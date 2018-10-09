package co.yodo.restapi.network.requests;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import co.yodo.restapi.network.contract.IExecuter;
import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.utils.JsonUtils;
import co.yodo.restapi.network.model.ServerResponse;
import co.yodo.restapi.network.requests.contract.Request;

/**
 * Created by hei on 26/08/16.
 * Request the currencies from the server
 */
public final class CurrenciesRequest extends Request {
    /** Name of the file to store the currencies */
    private static final String FILENAME = "currencies.json";

    /** Life time of the cache file */
    private static final int LIFETIME = 6 * 60 * 60 * 1000; // 6 hours (1000 = 1 sec)

    /** Request data */
    private final String merchantCurrency;
    private final String tenderCurrency;

    /** Dir to save cached files */
    private final File cacheFile;

    public CurrenciesRequest(String merchantCur, String tenderCur) {
        // Get the interested currencies
        this.merchantCurrency = merchantCur;
        this.tenderCurrency = tenderCur;

        // Get the currencies file
        cacheFile = new File(context.getCacheDir(), FILENAME);
    }

    @Override
    protected String buildBody() {
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void execute(RequestCallback callback) {
        // Find the dir to save cached files
        boolean exists = cacheFile.exists();
        if (!exists || cacheFile.lastModified() + LIFETIME < System.currentTimeMillis()) {
            client.sendJsonRequest(executer, callback);
        } else {
            try {
                FileInputStream fin = new FileInputStream(cacheFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine() ) != null) {
                    sb.append(line).append("\n");
                }

                reader.close();
                fin.close();

                // Transform the text to JSONArray
                JSONArray array = new JSONArray(sb.toString());
                if (array.length() <= 0) {
                    cacheFile.delete();
                }

                ServerResponse data = JsonUtils.parseCurrencies(array, merchantCurrency, tenderCurrency);
                callback.onResponse(data);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /** Executes a process for the json array request */
    private final IExecuter executer = new IExecuter() {
        @SuppressWarnings("ResultOfMethodCallIgnored")
        @Override
        public ServerResponse execute(JSONArray array) throws IOException {
            try {
                FileWriter writer = new FileWriter(cacheFile.getAbsolutePath());
                writer.write(array.toString());
                writer.flush();
                writer.close();

                return JsonUtils.parseCurrencies(array, merchantCurrency, tenderCurrency);
            } catch (IOException error) {
                if (cacheFile.exists()) {
                    cacheFile.delete();
                }
                throw error;
            }
        }
    };
}

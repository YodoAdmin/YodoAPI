package co.yodo.restapi.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.yodo.restapi.network.model.Params;
import co.yodo.restapi.network.model.ServerResponse;

/**
 * Created by luis on 19/01/16.
 * Handler for the JSON responses
 */
public final class JsonUtils {
    /** JSON Tags */
    private static final String YODO_TAG = "YodoCurrency";
    private static final String CURRENCY_TAG = "currency";
    private static final String RATE_TAG = "rate";

    /**
     * Parse the required currencies from the array of
     * currencies and their rates
     * @param array An array of currencies and rates
     * @return The server response with the parsed values
     */
    public static ServerResponse parseCurrencies(JSONArray array, String merchantCurrency, String tenderCurrency) {
        ServerResponse response = new ServerResponse();
        Params params = new Params();

        for (int i = 0; i < array.length(); ++i) {
            try {
                JSONObject temp = array.getJSONObject(i);
                JSONObject root = temp.getJSONObject(YODO_TAG);
                String currency = root.getString(CURRENCY_TAG);
                String rate = root.getString(RATE_TAG);

                // Gets the Merchant Currency
                if (currency.equals(merchantCurrency)) {
                    params.setMerchRate(rate);
                }

                // Gets the fare currency
                if (currency.equals(tenderCurrency)) {
                    params.setTenderRate(rate);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        response.setParams(params);
        return response;
    }
}


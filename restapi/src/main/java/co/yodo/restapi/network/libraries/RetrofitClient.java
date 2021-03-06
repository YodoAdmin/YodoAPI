package co.yodo.restapi.network.libraries;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

import co.yodo.restapi.ApiClient;
import co.yodo.restapi.network.contract.IExecuter;
import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.libraries.contract.IClient;
import co.yodo.restapi.network.model.ServerResponse;
import co.yodo.restapi.utils.JsonUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hei on 20/04/17.
 * Implements the requests for retrofit
 */
public final class RetrofitClient extends IClient {
    /** Object used to send the requests to the server */
    @Inject
    Retrofit networkManager;

    /** Interface with the rest paths */
    private final IApiEndpoint service;

    public RetrofitClient() {
        // Injection
        ApiClient.getComponent().inject(this);

        // Creates the interface for the requests
        service = networkManager.create(IApiEndpoint.class);
    }

    @Override
    public void sendXmlRequest(String params, final RequestCallback callback) {
        // Just before launching the request
        callback.onPrepare();

        // Execute the request
        Call<ServerResponse> request = service.getRequest(params);
        request.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    callback.onResponse(response.body());
                } catch (NullPointerException error) {
                    callback.onError(error);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable error) {
                callback.onError(error);
            }
        });
    }

    @Override
    public void sendJsonRequest(final IExecuter executer, final RequestCallback callback) {
        // Just before launching the request
        callback.onPrepare();

        // Execute the request
        Call<ResponseBody> request = service.currencies();
        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> privateCallback, Response<ResponseBody> response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    callback.onResponse(executer.execute(array));
                } catch (IOException | JSONException error) {
                    error.printStackTrace();
                    callback.onError(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> privateCallback, Throwable error) {
                callback.onError(error);
            }
        });
    }

    /** Interface for the requests requests */
    interface IApiEndpoint {
        @GET(SWITCH_ADDRESS + "{request}")
        Call<ServerResponse> getRequest(@Path("request") String request);

        @GET(YODO + "currency/index.json")
        Call<ResponseBody> currencies();
    }
}

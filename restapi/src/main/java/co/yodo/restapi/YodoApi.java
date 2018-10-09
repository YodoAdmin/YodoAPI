package co.yodo.restapi;

import android.content.Context;

import co.yodo.restapi.network.requests.contract.ICommand;
import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.utils.PrefUtils;

import static co.yodo.restapi.utils.ErrorUtils.checkNull;

/**
 * Created by hei on 19/04/17.
 * General class to communicate with the API
 */
public final class YodoApi {
    /** Switch server IP address */
    public static final String PROD_IP = "http://50.56.180.133";   // Production
    public static final String DEMO_IP = "http://162.244.228.84";  // Demo
    public static final String DEV_IP  = "http://162.244.228.78";  // Development

    /**
     * We avoid the creation of an instance
     */
    private YodoApi() {}

    /**
     * Instance of the API
     */
    private static ApiClient api;

    /**
     * We cannot modify the init values, if any of the services are running
     * @param context ApplicationContext
     */
    public static ApiClient.Builder init(Context context) {
        checkNull("Context", context);
        api = null;
        return new ApiClient.Builder(context);
    }

    /**
     * Build the client of the API
     * @param builder The builder pattern to create the Coproga client
     */
    static void build(ApiClient.Builder builder) {
        api = new ApiClient(builder) ;
    }

    /**
     * Gets the ip used for the connection
     * @return String ip
     */
    public static String getIp() {
        return api.getIp();
    }

    /**
     * Gets the alias used for the connection
     * @return String ip
     */
    public static String getAlias() {
        return api.getAlias();
    }

    /**
     * Gets the device identifier
     * @return String
     */
    public static String getIdentifier() {
        return PrefUtils.getIdentifier(ApiClient.getComponent().getContext());
    }

    /**
     * Executes a request to the server and sends the response to the callback
     * @param request The request (e.g. auth, query)
     * @param callback The callback for the response
     */
    public static void execute(ICommand request, RequestCallback callback) {
        api.invoke(request, callback);
    }

    public static void clear() {
        PrefUtils.clearPreferences();
    }
}

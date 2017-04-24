package co.yodo.yodoapi;

import android.app.Application;

import co.yodo.restapi.YodoApi;

/**
 * Created by hei on 19/04/17.
 * Application name
 */
public class YodoApplication extends Application {
    /** Switch server IP address */
    private static final String PROD_IP = "http://50.56.180.133";   // Production
    private static final String DEMO_IP = "http://162.244.228.84";  // Demo
    private static final String DEV_IP  = "http://162.244.228.78";  // Development

    @Override
    public void onCreate() {
        super.onCreate();

        YodoApi.init(this)
                .setLog(true)
                .server(DEV_IP, "D")
                .build();
    }
}

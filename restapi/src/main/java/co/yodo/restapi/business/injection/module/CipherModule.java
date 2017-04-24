package co.yodo.restapi.business.injection.module;

import android.content.Context;

import co.yodo.restapi.ApiClient;
import co.yodo.restapi.YodoApi;
import co.yodo.restapi.business.cipher.RSA;
import co.yodo.restapi.business.cipher.contract.ICipher;
import co.yodo.restapi.business.injection.scope.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class CipherModule {
    /** URL for the requests */
    private final String path;

    public CipherModule(String baseUrl) {
        switch (baseUrl) {
            // Production
            case YodoApi.PROD_IP:
                path = "YodoKey/Prod/2048.public.der";
                break;

            // Demo
            case YodoApi.DEMO_IP:
                path = "YodoKey/Demo/2048.public.der";
                break;

            // Development
            case YodoApi.DEV_IP:
                path = "YodoKey/Dev/2048.public.der";
                break;

            // Local
            default:
                path = "YodoKey/Local/2048.public.der";
                break;
        }
    }

    @Provides
    @ApplicationScope
    ICipher providesRSACrypt(Context context){
        return new RSA(context, path);
    }
}

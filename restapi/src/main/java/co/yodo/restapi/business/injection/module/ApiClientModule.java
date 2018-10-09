package co.yodo.restapi.business.injection.module;

import java.util.concurrent.TimeUnit;

import co.yodo.restapi.business.injection.scope.ApplicationScope;
import co.yodo.restapi.network.libraries.RetrofitClient;
import co.yodo.restapi.network.libraries.contract.IClient;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class ApiClientModule {
    /** URL for the requests */
    private final String baseUrl;
    private final boolean log;

    public ApiClientModule(String baseUrl, boolean log) {
        this.baseUrl = baseUrl;
        this.log = log;
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor providesInterceptor() {
        HttpLoggingInterceptor.Level DEBUG;

        if(log) {
            DEBUG = HttpLoggingInterceptor.Level.BODY;
        } else {
            DEBUG = HttpLoggingInterceptor.Level.NONE;
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(DEBUG);
        return logging;
    }

    @Provides
    @ApplicationScope
    OkHttpClient providesOkHttpClient(HttpLoggingInterceptor logging) {
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @ApplicationScope
    Retrofit providesRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory( SimpleXmlConverterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build();
    }

    @Provides
    @ApplicationScope
    IClient providesClient() {
        return new RetrofitClient();
    }
}

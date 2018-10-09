package co.yodo.restapi.business.injection.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Context mApp;

    public ApplicationModule(Context app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Context providesApplication(){
        return mApp;
    }
}

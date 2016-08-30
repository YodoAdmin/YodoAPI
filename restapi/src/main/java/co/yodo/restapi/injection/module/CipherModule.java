package co.yodo.restapi.injection.module;

import android.content.Context;

import co.yodo.restapi.component.cipher.RSACrypt;
import co.yodo.restapi.injection.scope.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class CipherModule {
    @Provides
    @ApplicationScope
    RSACrypt providesRSACrypt( Context context ){
        return new RSACrypt( context );
    }
}

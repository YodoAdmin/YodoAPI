package co.yodo.restapi.business.injection.component;

import android.content.Context;

import co.yodo.restapi.business.cipher.contract.ICipher;
import co.yodo.restapi.business.injection.module.ApiClientModule;
import co.yodo.restapi.business.injection.module.CipherModule;
import co.yodo.restapi.business.injection.scope.ApplicationScope;
import co.yodo.restapi.network.libraries.RetrofitClient;
import co.yodo.restapi.network.libraries.contract.IClient;
import dagger.Component;

@ApplicationScope
@Component(modules = {CipherModule.class, ApiClientModule.class}, dependencies = ApplicationComponent.class)
public interface GraphComponent {
    // Inject to components
    void inject(RetrofitClient client);

    // Get the application context
    Context getContext();

    // Get the cipher object (e.g. RSA)
    ICipher getCipher();

    // Get the client object (e.g Retrofit)
    IClient getClient();
}

package co.yodo.restapi.injection.component;

import co.yodo.restapi.injection.module.ApiClientModule;
import co.yodo.restapi.injection.module.CipherModule;
import co.yodo.restapi.injection.scope.ApplicationScope;
import co.yodo.restapi.network.ApiClient;
import co.yodo.restapi.network.request.CurrenciesRequest;
import dagger.Component;

@ApplicationScope
@Component( modules = {CipherModule.class, ApiClientModule.class}, dependencies = ApplicationComponent.class )
public interface GraphComponent {
    // Injects the cipher RSA
    void inject( ApiClient client );
    void inject( CurrenciesRequest request );
}

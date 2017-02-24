package co.yodo.restapi.network.request;

import co.yodo.restapi.component.cipher.RSACrypt;
import co.yodo.restapi.network.ApiClient;
import co.yodo.restapi.network.handler.JSONHandler;
import co.yodo.restapi.network.request.contract.IRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hei on 26/08/16.
 * Request the currencies from the server
 */
public class CurrenciesRequest extends IRequest {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = CurrenciesRequest.class.getSimpleName();

    /** Request data */
    private final String mMerchantCur;
    private final String mTenderCur;

    /** Interface for the Currencies request */
    public interface IApiEndpoint {
        @GET( YODO + "currency/index.json" )
        Call<ResponseBody> currencies();
    }

    /**
     * Request the currencies from the server
     */
    public CurrenciesRequest( int responseCode, String merchantCur, String tenderCur ) {
        super( responseCode );
        this.mMerchantCur = merchantCur;
        this.mTenderCur = tenderCur;
    }

    @Override
    public void execute( RSACrypt oEncrypter, ApiClient oManager ) {
        JSONHandler handler = new JSONHandler( mMerchantCur, mTenderCur );
        IApiEndpoint iCaller = oManager.create( IApiEndpoint.class );
        Call<ResponseBody> sResponse = iCaller.currencies();
        oManager.sendArrayRequest( sResponse, responseCode, handler );
    }
}

package co.yodo.restapi.network.request;

import javax.crypto.spec.SecretKeySpec;

import co.yodo.restapi.component.cipher.AESCrypt;
import co.yodo.restapi.component.cipher.RSACrypt;
import co.yodo.restapi.network.ApiClient;
import co.yodo.restapi.network.model.ServerResponse;
import co.yodo.restapi.network.request.contract.IRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hei on 25/08/16.
 * Request an exchange from the server
 */
public class ExchangeRequest extends IRequest {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = ExchangeRequest.class.getSimpleName();

    /** Exchange data */
    private final String mPrimaryClient;
    private final String mSecondaryClient;

    /** Exchange request type */
    private static final String EXCH_RT = "1";

    /** Exchange sub-type */
    private static final String EXCH_RETAIL_ST = "1";

    /** Interface for the EXCH requests */
    public interface IApiEndpoint {
        @GET( YODO_ADDRESS + "{request}" )
        Call<ServerResponse> exchange( @Path( "request" ) String request );
    }

    /**
     * Exchange request: primary,secondary,extra
     * @param hardwareToken The hardware token of the device
     */
    public ExchangeRequest( int responseCode, String hardwareToken, String client,
                            String total, String cashTender, String cashBack,
                            double latitude, double longitude, String currency ) {
        super( responseCode );
        mPrimaryClient = hardwareToken;
        mSecondaryClient = client;
        formattedData =
                latitude   + REQ_SEP +
                longitude  + REQ_SEP +
                total      + REQ_SEP +
                cashTender + REQ_SEP +
                cashBack   + REQ_SEP +
                currency;
    }

    @Override
    public void execute( RSACrypt oEncrypter, ApiClient oManager ) {
        String sEncryptedClientData, pRequest;

        SecretKeySpec key = AESCrypt.generateKey();

        encryptedKey = oEncrypter.encrypt( AESCrypt.encodeKey( key ) );
        encryptedData =
                AESCrypt.encrypt( mPrimaryClient, key )   + REQ_SEP +
                AESCrypt.encrypt( mSecondaryClient, key ) + REQ_SEP +
                AESCrypt.encrypt( formattedData, key );

        // Encrypting to create request
        /* encryptedData =
                oEncrypter.encrypt( mPrimaryClient ) + REQ_SEP +
                mSecondaryClient                     + REQ_SEP +
                oEncrypter.encrypt( formattedData ); */

        sEncryptedClientData =
                encryptedKey + REQ_SEP +
                        encryptedData;

        pRequest = buildRequest( EXCH_RT,
                EXCH_RETAIL_ST,
                sEncryptedClientData
        );

        IApiEndpoint iCaller = oManager.create( IApiEndpoint.class );
        Call<ServerResponse> sResponse = iCaller.exchange( pRequest );
        oManager.sendXMLRequest( sResponse, responseCode );
    }
}

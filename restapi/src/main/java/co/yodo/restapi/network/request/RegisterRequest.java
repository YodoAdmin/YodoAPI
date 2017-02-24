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
 * Request a registration from the server
 */
public class RegisterRequest extends IRequest {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = RegisterRequest.class.getSimpleName();

    /** Register request type */
    private static final String REG_RT = "9";

    /** Register sub-type */
    private static final String REG_MERCH_ST = "1";

    /** Interface for the REG requests */
    public interface IApiEndpoint {
        @GET( YODO_ADDRESS + "{request}" )
        Call<ServerResponse> register( @Path( "request" ) String request );
    }

    /**
     * Register request, to create a new POS
     * @param hardwareToken  The hardware token of the device
     * @param activationCode The activation code for the POS
     */
    public RegisterRequest( int responseCode, String hardwareToken, String activationCode ) {
        super( responseCode );
        formattedData =
                hardwareToken  + REQ_SEP +
                activationCode + REQ_SEP +
                System.currentTimeMillis() / 1000L;
    }

    @Override
    public void execute( RSACrypt oEncrypter, ApiClient oManager ) {
        String sEncryptedClientData, pRequest;

        SecretKeySpec key = AESCrypt.generateKey();

        encryptedKey = oEncrypter.encrypt( AESCrypt.encodeKey( key ) );
        encryptedData = AESCrypt.encrypt( formattedData, key );

        // Encrypting to create request
        //encryptedData = oEncrypter.encrypt( formattedData );
        sEncryptedClientData =
                encryptedKey + REQ_SEP +
                        encryptedData;

        pRequest = buildRequest( REG_RT,
                REG_MERCH_ST,
                sEncryptedClientData
        );

        IApiEndpoint iCaller = oManager.create( IApiEndpoint.class );
        Call<ServerResponse> sResponse = iCaller.register( pRequest );
        oManager.sendXMLRequest( sResponse, responseCode );
    }
}

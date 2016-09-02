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
 * Created by hei on 16/07/16.
 * Request an authentication from the server
 */
public class AuthenticateRequest extends IRequest {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = AuthenticateRequest.class.getSimpleName();

    /** Authenticate request type */
    private static final String AUTH_RT = "0";

    /** Authenticate sub-types */
    private enum AuthST {
        HW     ( "4" ),
        HW_PIP ( "5" );

        private final String value;

        AuthST( String value ) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /** Sub-type of the request */
    private final AuthST mRequestST;

    /** Interface for the AUTH requests */
    public interface IApiEndpoint {
        @GET( YODO_ADDRESS + "{request}" )
        Call<ServerResponse> authUser( @Path( "request" ) String request );
    }

    /**
     * Authentication with just the hardware token
     * @param hardwareToken The hardware token of the device
     */
    public AuthenticateRequest( int responseCode, String hardwareToken ) {
        super( responseCode );
        mFormattedData = hardwareToken;
        mRequestST = AuthST.HW;
    }

    /**
     * Authentication with just the hardware token
     * @param hardwareToken The hardware token of the device
     * @param merchPip The password of the user
     */
    public AuthenticateRequest( int responseCode, String hardwareToken, String merchPip ) {
        super( responseCode );
        mFormattedData =
                hardwareToken + PCLIENT_SEP +
                merchPip      + PCLIENT_SEP +
                System.currentTimeMillis() / 1000L;
        mRequestST = AuthST.HW_PIP;
    }

    @Override
    public void execute( RSACrypt oEncrypter, ApiClient oManager ) {
        String sEncryptedClientData, pRequest;

        SecretKeySpec key = AESCrypt.generateKey();

        mEncyptedKey = oEncrypter.encrypt( AESCrypt.encodeKey( key ) );
        mEncyptedData = AESCrypt.encrypt( mFormattedData, key );

        // Encrypting to create request
        // mEncyptedData = oEncrypter.encrypt( mFormattedData );
        sEncryptedClientData =
                mEncyptedKey + REQ_SEP +
                mEncyptedData;

        pRequest = buildRequest( AUTH_RT,
                mRequestST.getValue(),
                sEncryptedClientData
        );

        IApiEndpoint iCaller = oManager.create( IApiEndpoint.class );
        Call<ServerResponse> sResponse = iCaller.authUser( pRequest );
        oManager.sendRequest( sResponse, mResponseCode );
    }
}

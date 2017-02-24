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
 * Request a query from the server
 */
public class QueryRequest extends IRequest {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = QueryRequest.class.getSimpleName();

    /** Query request type */
    private static final String QUERY_RT = "4";

    /** Query sub-type */
    private static final String QUERY_ACC_ST = "3";

    /** Query Records - QUERY_ACC_ST */
    public enum Record {
        HISTORY_BALANCE   ( 10 ),
        TODAY_BALANCE     ( 12 ),
        MERCHANT_CURRENCY ( 13 ),
        MERCHANT_LOGO     ( 14 );

        private final int value;

        Record( int i ) {
            value = i;
        }

        public int getValue() {
            return value;
        }
    }

    /** Interface for the QUERY requests */
    public interface IApiEndpoint {
        @GET( YODO_ADDRESS + "{request}" )
        Call<ServerResponse> query( @Path( "request" ) String request );
    }

    /**
     * Request something from the server
     * @param hardwareToken The hardware token of the device
     */
    public QueryRequest( int responseCode, String hardwareToken, Record record ) {
        super( responseCode );
        formattedData =
                hardwareToken + REQ_SEP +
                record.getValue();
    }

    /**
     * Request something from the server
     * @param hardwareToken The hardware token of the device
     */
    public QueryRequest( int responseCode, String hardwareToken, String merchPip, Record record ) {
        super( responseCode );
        formattedData =
                hardwareToken + REQ_SEP +
                merchPip      + REQ_SEP +
                record.getValue();
    }

    @Override
    public void execute( RSACrypt oEncrypter, ApiClient oManager ) {
        String sEncryptedClientData, pRequest;

        SecretKeySpec key = AESCrypt.generateKey();

        encryptedKey = oEncrypter.encrypt( AESCrypt.encodeKey( key ) );
        encryptedData = AESCrypt.encrypt( formattedData, key );

        // Encrypting to create request
        // encryptedData = oEncrypter.encrypt( formattedData );
        sEncryptedClientData =
                encryptedKey + REQ_SEP +
                        encryptedData;

        pRequest = buildRequest( QUERY_RT,
                QUERY_ACC_ST,
                sEncryptedClientData
        );

        IApiEndpoint iCaller = oManager.create( IApiEndpoint.class );
        Call<ServerResponse> sResponse = iCaller.query( pRequest );
        oManager.sendXMLRequest( sResponse, responseCode );
    }
}

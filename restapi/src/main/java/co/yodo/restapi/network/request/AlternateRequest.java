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
 * Request an alternate from the server
 */
public class AlternateRequest extends IRequest {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = AlternateRequest.class.getSimpleName();

    /** Exchange data */
    private final String primaryClient;
    private final String secondaryClient;

    /** Alternate request type */
    private static final String ALTER_RT = "7";

    /**
     * CASH_ST        - 0
     * VISA_CRED_ST   - 1
     * HEART_ST       - 3
     * VISA_PREP_ST   - 4
     * PAYPAL_ST      - 5
     * PUB_TRANSIT_ST - 6
     */

    /** Sub-type of the request */
    private final String requestST;

    /** Interface for the ALTER requests */
    interface IApiEndpoint {
        @GET( YODO_ADDRESS + "{request}" )
        Call<ServerResponse> alternate( @Path( "request" ) String request );
    }

    /**
     * Alternate request: primary,secondary,extra
     * @param hardwareToken The hardware token of the device
     */
    public AlternateRequest( int responseCode, String subType, String hardwareToken, String client,
                            String total, String cashTender, String cashBack,
                            double latitude, double longitude, String currency ) {
        super( responseCode );
        requestST = subType;
        primaryClient = hardwareToken;
        secondaryClient = client;
        formattedData =
                latitude   + REQ_SEP +
                longitude  + REQ_SEP +
                total      + REQ_SEP +
                cashTender + REQ_SEP +
                cashBack   + REQ_SEP +
                currency;
    }

    @Override
    public void execute( RSACrypt encrypter, ApiClient manager ) {
        String sEncryptedClientData, pRequest;

        SecretKeySpec key = AESCrypt.generateKey();

        encryptedKey = encrypter.encrypt( AESCrypt.encodeKey( key ) );
        encryptedData =
                AESCrypt.encrypt( primaryClient, key )   + REQ_SEP +
                AESCrypt.encrypt( secondaryClient, key ) + REQ_SEP +
                AESCrypt.encrypt( formattedData, key );

        sEncryptedClientData =
                encryptedKey + REQ_SEP +
                        encryptedData;

        pRequest = buildRequest( ALTER_RT,
                requestST,
                sEncryptedClientData
        );

        IApiEndpoint iCaller = manager.create( IApiEndpoint.class );
        Call<ServerResponse> sResponse = iCaller.alternate( pRequest );
        manager.sendXMLRequest( sResponse, responseCode );
    }
}

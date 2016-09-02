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
    private final String mPrimaryClient;
    private final String mSecondaryClient;

    /** Alternate request type */
    private static final String ALTER_RT = "7";

    /** Sub-type of the request */
    private final String mRequestST;

    /** Interface for the ALTER requests */
    public interface IApiEndpoint {
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
        mRequestST = subType;
        mPrimaryClient = hardwareToken;
        mSecondaryClient = client;
        mFormattedData =
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

        mEncyptedKey = oEncrypter.encrypt( AESCrypt.encodeKey( key ) );
        mEncyptedData =
                AESCrypt.encrypt( mPrimaryClient, key )   + REQ_SEP +
                        AESCrypt.encrypt( mSecondaryClient, key ) + REQ_SEP +
                        AESCrypt.encrypt( mFormattedData, key );

        // Encrypting to create request
        /* mEncyptedData =
                oEncrypter.encrypt( mPrimaryClient ) + REQ_SEP +
                mSecondaryClient                     + REQ_SEP +
                oEncrypter.encrypt( mFormattedData );*/

        sEncryptedClientData =
                mEncyptedKey + REQ_SEP +
                mEncyptedData;

        pRequest = buildRequest( ALTER_RT,
                mRequestST,
                sEncryptedClientData
        );

        IApiEndpoint iCaller = oManager.create( IApiEndpoint.class );
        Call<ServerResponse> sResponse = iCaller.alternate( pRequest );
        oManager.sendRequest( sResponse, mResponseCode );
    }
}

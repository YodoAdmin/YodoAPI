package co.yodo.restapi.network.request.contract;

import co.yodo.restapi.component.cipher.RSACrypt;
import co.yodo.restapi.helper.SystemUtils;
import co.yodo.restapi.network.ApiClient;

/**
 * Created by hei on 16/07/16.
 * Class used to implement the Command Design Pattern for the requests
 */
public abstract class IRequest {
    /** DEBUG */
    private static final String TAG = IRequest.class.getSimpleName();

    /** Formatted data for the request */
    protected String mFormattedData;

    /** Encrypted data for the request */
    protected String mEncyptedData;
    protected String mEncyptedKey;

    /** The code for the response */
    protected final int mResponseCode;

    /** Protocol version used in the requests */
    private static final String PROTOCOL_VERSION = "1.1.6";

    /** Two paths used for the requests */
    protected static final String YODO         = "/yodo/";
    protected static final String YODO_ADDRESS = "/yodo/yodoswitchrequest/getRequest/";

    /** Data separator */
    protected static final String REQ_SEP = ",";
    protected static final String PCLIENT_SEP = "/";

    protected IRequest( int responseCode ) {
        this.mResponseCode = responseCode;
    }

    /**
     * Builds the request string
     * @param pRequestType The request type
     * @param pSubType The request sub-type
     * @param pUserData Encrypted user's data
     * @return The request string that is send to the server
     */
    protected static String buildRequest( String pRequestType, String pSubType, String pUserData ) {
        final String request =
                PROTOCOL_VERSION + REQ_SEP +
                pRequestType     + REQ_SEP +
                pSubType         + REQ_SEP +
                pUserData;
        SystemUtils.iLogger( TAG, request );
        return request;
    }

    public abstract void execute( RSACrypt oEncrypter, ApiClient oManager );
}

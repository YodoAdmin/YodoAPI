package co.yodo.restapi.network.builder;

import co.yodo.restapi.helper.AppUtils;

/**
 * Created by hei on 26/04/16.
 * builds the requests
 */
public class ServerRequest {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private final static String TAG = ServerRequest.class.getSimpleName();

    /** Protocol version used in the request */
    private static final String PROTOCOL_VERSION = "1.1.2";

    /** Parameters used for creating an authenticate request */
    private static final String AUTH_REQ                 = "0";
    public static final String AUTH_CLIENT_HW_SUBREQ     = "1";
    public static final String AUTH_CLIENT_HW_PIP_SUBREQ = "2";
    public static final String AUTH_MERCH_HW_SUBREQ      = "4";
    public static final String AUTH_MERCH_HW_PIP_SUBREQ  = "5";

    /** Variable that holds request string separator */
    private static final String	REQ_SEP = ",";

    /**
     * Creates an authentication switch request
     * @param pUsrData	Encrypted user's data
     * @param iAuthReqType Sub-type of the request
     * @return String Request for getting the authentication
     */
    public static String createAuthenticationRequest( String pUsrData, int iAuthReqType ) {
        StringBuilder sAuthenticationRequest = new StringBuilder();
        sAuthenticationRequest.append( PROTOCOL_VERSION ).append( REQ_SEP );
        sAuthenticationRequest.append( AUTH_REQ ).append( REQ_SEP );

        switch( iAuthReqType ) {
            // RT = 0, ST = 1
            case 1: sAuthenticationRequest.append( AUTH_CLIENT_HW_SUBREQ ).append( REQ_SEP );
                break;

            // RT = 0, ST = 2
            case 2: sAuthenticationRequest.append( AUTH_CLIENT_HW_PIP_SUBREQ ).append( REQ_SEP );
                break;

            // RT = 0, ST = 4
            case 4: sAuthenticationRequest.append( AUTH_MERCH_HW_SUBREQ ).append( REQ_SEP );
                break;

            // RT = 0, ST = 5
            case 5: sAuthenticationRequest.append( AUTH_MERCH_HW_PIP_SUBREQ ).append( REQ_SEP );
                break;
        }
        sAuthenticationRequest.append( pUsrData );

        AppUtils.Logger( 'd', TAG, "Authentication Request: " + sAuthenticationRequest.toString() );
        return sAuthenticationRequest.toString();
    }
}

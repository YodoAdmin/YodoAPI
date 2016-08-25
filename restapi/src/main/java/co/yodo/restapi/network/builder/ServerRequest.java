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
    private static final String PROTOCOL_VERSION = "1.1.5";

    /** Parameters used for creating an authenticate request */
    private static final String AUTH_RT              = "0";
    public static final String AUTH_MERCH_HW_ST      = "4";
    public static final String AUTH_MERCH_HW_PIP_ST  = "5";

    /** Parameters used for creating an exchange request */
    private static final String EXCH_RT      = "1";
    public static final String EXCH_MERCH_ST = "1";

    /** Parameters used for creating a balance request */
    private static final String QUERY_RT       = "4";
    public static final String QUERY_ACC_ST    = "3";

    /** Query Records - QUERY_ACC_ST */
    public enum QueryRecord {
        HISTORY_BALANCE   ( 10 ),
        TODAY_BALANCE     ( 12 ),
        MERCHANT_CURRENCY ( 13 ),
        MERCHANT_LOGO     ( 14 );

        private final int value;

        QueryRecord( int i ) {
            value = i;
        }

        public int getValue() {
            return value;
        }
    }

    /** Parameters used for creating an alternate request */
    private static final String ALTER_RT = "7";

    /** Parameters used for creating a registration request */
    private static final String REG_RT         = "9";
    public static final String REG_MERCH_ST = "1";

    /** Variable that holds request string separator */
    private static final String	REQ_SEP = ",";

    /**
     * Creates an authentication switch request
     * @param pUsrData	Encrypted user's data
     * @param iAuthST Sub-type of the request
     * @return String Request for getting the authentication
     */
    public static String createAuthenticationRequest( String pUsrData, String iAuthST ) {
        StringBuilder sAuthenticationRequest = new StringBuilder();
        sAuthenticationRequest.append( PROTOCOL_VERSION ).append( REQ_SEP );
        sAuthenticationRequest.append( AUTH_RT ).append( REQ_SEP );
        sAuthenticationRequest.append( iAuthST ).append( REQ_SEP );
        sAuthenticationRequest.append( pUsrData );

        AppUtils.Logger( 'd', TAG, "Authentication Request: " + sAuthenticationRequest.toString() );
        return sAuthenticationRequest.toString();
    }

    /**
     * Creates an exchange switch request
     * @param pUsrData	Encrypted user's data
     * @param iExchST Sub-type of the request
     * @return	String	Request for getting the Exchange
     */
    public static String createExchangeRequest( String pUsrData, String iExchST ) {
        StringBuilder sExchangeRequest = new StringBuilder();
        sExchangeRequest.append( PROTOCOL_VERSION ).append( REQ_SEP );
        sExchangeRequest.append( EXCH_RT ).append( REQ_SEP );
        sExchangeRequest.append( iExchST ).append( REQ_SEP );
        sExchangeRequest.append( pUsrData );

        AppUtils.Logger( 'd', TAG, "Exchange Request: " + sExchangeRequest.toString() );
        return sExchangeRequest.toString();
    }

    /**
     * Creates a query request
     * @param pUsrData	Encrypted user's data
     * @param iQueryST Sub-type of the request
     * @return String Request for getting the balance
     */
    public static String createQueryRequest( String pUsrData, String iQueryST ) {
        StringBuilder sQueryRequest = new StringBuilder();
        sQueryRequest.append( PROTOCOL_VERSION ).append( REQ_SEP );
        sQueryRequest.append( QUERY_RT ).append( REQ_SEP );
        sQueryRequest.append( iQueryST ).append( REQ_SEP );
        sQueryRequest.append( pUsrData );

        AppUtils.Logger(  'd', TAG, "Third Party Balance Request: " + sQueryRequest.toString() );
        return sQueryRequest.toString();
    }

    /**
     * Creates an registration switch request
     * @param pUsrData	Encrypted user's data
     * @param iRegST Sub-type of the request
     * @return String Request for getting the registration code
     */
    public static String createRegistrationRequest( String pUsrData, String iRegST ) {
        StringBuilder sRegistrationRequest = new StringBuilder();
        sRegistrationRequest.append( PROTOCOL_VERSION ).append( REQ_SEP );
        sRegistrationRequest.append( REG_RT ).append( REQ_SEP );
        sRegistrationRequest.append( iRegST ).append( REQ_SEP );
        sRegistrationRequest.append( pUsrData );

        AppUtils.Logger( 'd', TAG, "Registration Request: " + sRegistrationRequest.toString() );
        return sRegistrationRequest.toString();
    }

    /**
     * Creates an alternate switch request
     * @param pUsrData	Encrypted user's data
     * @param iAlterST Sub-type of the request
     *                 VISA_CRED_ST   - 1
     *                 HEART_ST       - 3
     *                 VISA_PREP_ST   - 4
     *                 PAYPAL_ST      - 5
     *                 PUB_TRANSIT_ST - 6
     * @return	String	Request for getting the Alternate Transaction
     */
    public static String createAlternateRequest( String pUsrData, String iAlterST ) {
        StringBuilder sAlternateRequest = new StringBuilder();
        sAlternateRequest.append( PROTOCOL_VERSION ).append( REQ_SEP );
        sAlternateRequest.append( ALTER_RT ).append( REQ_SEP );
        sAlternateRequest.append( iAlterST ).append( REQ_SEP );
        sAlternateRequest.append( pUsrData );

        AppUtils.Logger( 'd', TAG, "Alternate Request: " + sAlternateRequest.toString() );
        return sAlternateRequest.toString();
    }
}

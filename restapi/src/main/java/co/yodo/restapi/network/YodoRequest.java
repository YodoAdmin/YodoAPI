package co.yodo.restapi.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import co.yodo.restapi.R;
import co.yodo.restapi.component.Encrypter;
import co.yodo.restapi.helper.AppUtils;
import co.yodo.restapi.network.builder.ServerRequest;
import co.yodo.restapi.network.handler.XMLHandler;
import co.yodo.restapi.network.model.ServerResponse;

/**
 * Created by hei on 26/04/16.
 * Requests for the YodoService
 */
public class YodoRequest {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = YodoRequest.class.getSimpleName();

    /** Switch server IP address */
    private static final String PROD_IP = "http://50.56.180.133";   // Production
    private static final String DEV_IP  = "http://198.101.209.120"; // Development
    private static final String IP      = DEV_IP;

    /** Two paths used for the requests */
    private static final String YODO_ADDRESS = "/yodo/yodoswitchrequest/getRequest/";
    private static final String YODO         = "/yodo/";

    /** Timeout for the request */
    private final static int TIMEOUT = 1000 * 10; // 10 seconds

    private RetryPolicy retryPolicy = new DefaultRetryPolicy(
            TIMEOUT,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    );

    /** Object used to encrypt information */
    private final Encrypter oEncrypter;

    /** Global request queue for Volley */
    private RequestQueue mRequestQueue = null;

    /** Singleton instance */
    private static YodoRequest mInstance = null;

    /** The external listener to the service */
    private RESTListener listener;

    /** Context of the application */
    private static Context mCtx;

    /** User's data separator */
    private static final String	REQ_SEP = ",";
    private static final String	PCLIENT_SEP = "/";

    public interface RESTListener {
        /**
         * Listener for the server responses
         * @param responseCode Code for the response
         * @param response POJO for the response
         */
        void onResponse( int responseCode, ServerResponse response );
    }

    /**
     * Private constructor for the
     * singleton
     */
    private YodoRequest( Context context ) {
        mCtx = context.getApplicationContext();
        mRequestQueue = getRequestQueue();
        oEncrypter = new Encrypter();
    }

    /**
     * Gets the instance of the service
     * @return instance
     */
    public static synchronized YodoRequest getInstance( Context context ) {
        if( mInstance == null )
            mInstance = new YodoRequest( context );
        return mInstance;
    }

    /**
     * Returns the request queue for other queries
     * @return The static volley request queue
     */
    public RequestQueue getRequestQueue() {
        if( mRequestQueue == null ) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue( mCtx );
        }
        return mRequestQueue;
    }

    /**
     * Add a listener to the service
     * @param listener Listener for the requests to the server
     */
    public void setListener( RESTListener listener ) {
        this.listener = listener ;
    }

    /**
     * Returns an string that represents the server of the IP
     * @return P - production
     *         D - development
     */
    public static String getSwitch() {
        return ( IP.equals( PROD_IP ) ) ? "P" : "D";
    }

    private void sendXMLRequest( final String pRequest, final int responseCode ) {
        final StringRequest httpRequest = new StringRequest( Request.Method.GET, IP + YODO_ADDRESS + pRequest,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse( String xml ) {
                        try {
                            // Handling XML
                            SAXParserFactory spf = SAXParserFactory.newInstance();
                            SAXParser sp = spf.newSAXParser();
                            XMLReader xr = sp.getXMLReader();

                            // Create handler to handle XML Tags ( extends DefaultHandler )
                            xr.setContentHandler( new XMLHandler() );
                            xr.parse( new InputSource( new StringReader( xml ) ) );

                            // Sends the response to the listener
                            AppUtils.Logger( 'i', TAG, XMLHandler.response.toString() );
                            listener.onResponse( responseCode, XMLHandler.response );
                        } catch( ParserConfigurationException | SAXException | IOException e ) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error ) {
                        error.printStackTrace();
                        // depending on the error, return an alert to the activity
                        ServerResponse response = new ServerResponse();
                        if( error instanceof TimeoutError  ) {
                            response.setCode( ServerResponse.ERROR_TIMEOUT );
                            response.setMessage( mCtx.getString( R.string.message_error_timeout ) );
                        } else if( error instanceof NetworkError ) {
                            response.setCode( ServerResponse.ERROR_NETWORK );
                            response.setMessage( mCtx.getString( R.string.message_error_network ) );
                        } else if( error instanceof ServerError ) {
                            response.setCode( ServerResponse.ERROR_SERVER );
                            response.setMessage( mCtx.getString( R.string.message_error_server ) );
                        } else {
                            response.setCode( ServerResponse.ERROR_UNKOWN );
                            response.setMessage( mCtx.getString( R.string.message_error_unknown ) );
                        }
                        listener.onResponse( responseCode, response );
                    }
                }
        );
        httpRequest.setTag( "GET" );
        httpRequest.setRetryPolicy( retryPolicy );
        getRequestQueue().add( httpRequest );
    }

    /**
     * Queries directed to the server
     * {{ ======================================================================
     */
    public void requestMerchAuth( int responseCode, String hardwareToken ) {
        String sEncryptedMerchData, pRequest;

        // Encrypting to create request
        oEncrypter.setsUnEncryptedString( hardwareToken );
        oEncrypter.rsaEncrypt( mCtx );
        sEncryptedMerchData = oEncrypter.bytesToHex();

        pRequest = ServerRequest.createAuthenticationRequest(
                sEncryptedMerchData,
                Integer.parseInt( ServerRequest.AUTH_MERCH_HW_SUBREQ )
        );

        sendXMLRequest( pRequest, responseCode );
    }

    public void requestPIPAuthentication( int responseCode, String hardwareToken, String pip ) {
        String sEncryptedClientData, pRequest;

        // Encrypting to create request
        oEncrypter.setsUnEncryptedString(
                        hardwareToken + PCLIENT_SEP +
                        pip + PCLIENT_SEP +
                        System.currentTimeMillis() / 1000L
        );
        oEncrypter.rsaEncrypt( mCtx );
        sEncryptedClientData = oEncrypter.bytesToHex();

        pRequest = ServerRequest.createAuthenticationRequest(
                sEncryptedClientData,
                Integer.parseInt( ServerRequest.AUTH_MERCH_HW_PIP_SUBREQ )
        );

        sendXMLRequest( pRequest, responseCode );
    }
}

package co.yodo.restapi.network;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;

import co.yodo.restapi.component.cipher.RSACrypt;
import co.yodo.restapi.helper.SystemUtils;
import co.yodo.restapi.injection.component.ApplicationComponent;
import co.yodo.restapi.injection.component.DaggerApplicationComponent;
import co.yodo.restapi.injection.component.DaggerGraphComponent;
import co.yodo.restapi.injection.component.GraphComponent;
import co.yodo.restapi.injection.module.ApiClientModule;
import co.yodo.restapi.injection.module.ApplicationModule;
import co.yodo.restapi.network.handler.JSONHandler;
import co.yodo.restapi.network.model.ServerResponse;
import co.yodo.restapi.network.request.contract.IRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by hei on 15/07/16.
 * Implements the logic to communicate with the server
 */
public class ApiClient {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private final static String TAG = ApiClient.class.getSimpleName();

    /** Switch server IP address */
    public static final String PROD_IP  = "http://50.56.180.133";   // Production
    public static final String DEMO_IP  = "http://162.244.228.84";  // Demo
    public static final String DEV_IP   = "http://162.244.228.78";  // Development
    public static String IP = DEV_IP;

    /** Object used to send the requests to the server */
    @Inject
    Retrofit networkManager;

    /** Object used to encrypt information */
    @Inject
    RSACrypt cipher;

    /** Component that build the dependencies */
    private static GraphComponent injectionComponent;

    /** The external listener to the service */
    private RequestsListener listener;

    /** Singleton instance */
    private static ApiClient instance = null;

    /** Dir to save cached files */
    private final File currenciesFile;

    /**
     * private constructor for the singleton
     * @param context The application context
     */
    private ApiClient( Context context ) {
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( context ) )
                .build();

        injectionComponent = DaggerGraphComponent.builder()
                .applicationComponent( appComponent )
                .apiClientModule( new ApiClientModule( IP ) )
                .build();

        injectionComponent.inject( this );

        // Get the currencies file
        currenciesFile = new File( context.getCacheDir(), "currencies.json" );
    }

    /**
     * Gets the instance of the service
     * @return instance
     */
    public static synchronized ApiClient getInstance( Context context ) {
        if( instance == null )
            instance = new ApiClient( context );
        return instance;
    }

    /**
     * Returns the current IP address (dev or prod)
     * @return An string with the IP address
     */
    public static String getRoot() {
        return IP;
    }

    /**
     * Returns an string that represents the server of the IP
     * @return P  - production
     *         De - demo
     *         D  - development
     *         L  - local
     */
    public static String getSwitch() {
        return ( IP.equals( PROD_IP ) ) ? "P" :
               ( IP.equals( DEMO_IP ) ) ? "E" :
               ( IP.equals( DEV_IP ) ) ? "D" : "L";
    }

    /**
     * Gets the GraphComponent to inject the classes
     * @return GraphComponent
     */
    public static GraphComponent getComponent() {
        return injectionComponent;
    }

    /**
     * Creates the interface for the requests
     * @param service The interface
     * @param <T> The type
     * @return An object to call the request
     */
    public <T> T create( Class<T> service ) {
        return networkManager.create( service );
    }

    /**
     * Sets a listener for the service
     * @param listener Listener for the requests to the server
     */
    public void subscribe( RequestsListener listener ) {
        this.listener = listener ;
    }

    /**
     * Removes the listener for the service
     */
    public void unsubscribe() {
        this.listener = null;
    }

    /**
     * Executes a request (extends IRequest class)
     * @param request The request to be executed
     */
    public void invoke( IRequest request ) {
        if( listener == null )
            throw new NullPointerException( "Listener not defined" );

        request.execute( cipher, this );
    }

    public void sendXMLRequest( Call<ServerResponse> sResponse, final int responseCode ) {
        sResponse.enqueue( new Callback<ServerResponse>() {
            @Override
            public void onResponse( Call<ServerResponse> call, Response<ServerResponse> response ) {
                try {
                    SystemUtils.dLogger( TAG, response.body().toString() );
                    listener.onResponse( responseCode, response.body() );
                } catch( NullPointerException e ) {
                    handleFailure( e );
                }
            }

            @Override
            public void onFailure( Call<ServerResponse> call, Throwable error ) {
                handleFailure( error );
            }
        } );
    }

    public void sendArrayRequest( Call<ResponseBody> sResponse, final int responseCode, final JSONHandler handler ) {
        // Life time of the file
        final int MAX_AGE = 6 * 60 * 60 * 1000; // 6 hours (1000 = 1 sec)

        //Find the dir to save cached files
        boolean exists = currenciesFile.exists();
        if( !exists || currenciesFile.lastModified() + MAX_AGE < System.currentTimeMillis() ) {
            sResponse.enqueue( new Callback<ResponseBody>() {
                @Override
                public void onResponse( Call<ResponseBody> call, Response<ResponseBody> response ) {
                    try {
                        JSONArray json = new JSONArray( response.body().string() );

                        try {
                            FileWriter writer = new FileWriter( currenciesFile.getAbsolutePath() );
                            writer.write( json.toString() );
                            writer.flush();
                            writer.close();
                        } catch( IOException e ) {
                            e.printStackTrace();
                            if( currenciesFile.exists() )
                                currenciesFile.delete();
                        }

                        ServerResponse data = handler.parseCurrencies( json );
                        SystemUtils.dLogger( TAG, data.toString() );
                        listener.onResponse( responseCode, data );
                    } catch( IOException | JSONException e ) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure( Call<ResponseBody> call, Throwable error ) {
                    handleFailure( error );
                }
            } );
        } else {
            try {
                FileInputStream fin   = new FileInputStream( currenciesFile );
                BufferedReader reader = new BufferedReader( new InputStreamReader( fin ) );
                StringBuilder sb      = new StringBuilder();
                String line;

                while( ( line = reader.readLine() ) != null )
                    sb.append( line ).append( "\n" );

                reader.close();
                fin.close();

                // Transform the text to JSONArray
                JSONArray json = new JSONArray( sb.toString() );
                if( json.length() <= 0 )
                    currenciesFile.delete();

                ServerResponse data = handler.parseCurrencies( json );
                SystemUtils.dLogger( TAG, data.toString() );
                listener.onResponse( responseCode, data );
            } catch ( IOException | JSONException e ) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the error for the request
     * @param error The error object
     */
    private void handleFailure( Throwable error ) {
        error.printStackTrace();
        listener.onError( error );
        /*if( error instanceof IOException ) {
            // Network error
            listener.onError( error, NETWORK_ERROR );
        } else if( error instanceof NullPointerException  )  {
            // Server error
            listener.onError( error, SERVER_ERROR );
        } else {
            // Another error
            listener.onError( error, UNKNOWN_ERROR );
        }*/
    }

    public interface RequestsListener {
        /**
         * Listener for the server responses
         * @param responseCode Code of the request
         * @param response POJO for the response
         */
        void onResponse( int responseCode, ServerResponse response );

        /**
         * Wherever an error appears
         * @param error The error object
         */
        void onError( Throwable error );
    }
}

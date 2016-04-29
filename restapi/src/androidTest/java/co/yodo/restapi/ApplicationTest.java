package co.yodo.restapi;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import java.util.concurrent.Semaphore;

import co.yodo.restapi.network.YodoRequest;
import co.yodo.restapi.network.model.ServerResponse;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> implements YodoRequest.RESTListener {
    public ApplicationTest() {
        super( Application.class );
    }

    /**  */
    Context context;

    /** Authentication Number */
    private String hardwareToken = "adasd";

    /** Server Response */
    private ServerResponse response;

    /** Semaphore */
    private Semaphore semaphore;

    int AUTH_REQ = 0x01;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context      = getContext();
        semaphore     = new Semaphore( 0 );

        YodoRequest.getInstance( context ).setListener( this );
    }

    public void testAuthentication() throws Exception {
        YodoRequest.getInstance( context ).requestMerchAuth( AUTH_REQ, hardwareToken );
        semaphore.acquire();

        assertNotNull( response );
    }

    @Override
    public void onResponse( int responseCode, ServerResponse response ) {
        this.response = response;
        semaphore.release();
    }
}
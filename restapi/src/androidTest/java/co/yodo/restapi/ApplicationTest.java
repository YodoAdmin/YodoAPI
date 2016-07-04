package co.yodo.restapi;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import java.util.concurrent.Semaphore;

import co.yodo.restapi.network.YodoRequest;
import co.yodo.restapi.network.builder.ServerRequest;
import co.yodo.restapi.network.model.ServerResponse;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> implements YodoRequest.RESTListener {
    /** Context object */
    private Context ac;

    /** POS Merchant Values */
    private static final String fakeHardwareToken = "asdasdasdasdasd";
    private static final String mockHardwareToken = "354984054060899";
    private static final String registrationToken = "683b9fa4";
    private static final String masterPIPMerch    = "test";
    private static final String masterPIPPOS      = "demo123";
    private static final String wrongPIP          = "asdasda";

    /** Server Response */
    private ServerResponse response;

    /** Semaphore */
    private Semaphore semaphore;

    /** Response codes for the queries */
    private static final int REG_REQ   = 0x00;
    private static final int AUTH_REQ  = 0x01;
    private static final int EXCH_REQ  = 0x02;
    private static final int QUERY_REQ = 0x03;

    public ApplicationTest() {
        super( Application.class );
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Setup the basic elements
        ac = getContext();
        semaphore = new Semaphore( 0 );
        YodoRequest.getInstance( ac ).setListener( this );
        // Register one of the test hardware tokens
        YodoRequest.getInstance( ac ).requestMerchReg( REG_REQ, mockHardwareToken, registrationToken );
        semaphore.acquire();
        // Verify a not null response
        assertNotNull( response );
        response = null;
    }

    public void testAuthentication() throws Exception {
        // Auth
        YodoRequest.getInstance( ac ).requestMerchAuth( AUTH_REQ, fakeHardwareToken );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestMerchAuth( AUTH_REQ, mockHardwareToken );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.AUTHORIZED, response.getCode() );
        response = null;

        // Auth PIP
        YodoRequest.getInstance( ac ).requestMerchAuth( AUTH_REQ, fakeHardwareToken, masterPIPPOS );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestMerchAuth( AUTH_REQ, mockHardwareToken, masterPIPPOS );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.AUTHORIZED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestMerchAuth( AUTH_REQ, mockHardwareToken, wrongPIP );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;
    }

    public void testExchange() throws Exception {
        YodoRequest.getInstance( ac ).requestExchange(
                EXCH_REQ, fakeHardwareToken, "", "0.00", "0.00", "0.00" , 0.00, 0.00, "CAD"
        );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;
    }

    public void testTotalHistory() throws Exception {
        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                fakeHardwareToken,
                masterPIPMerch,
                ServerRequest.QueryRecord.HISTORY_BALANCE );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                mockHardwareToken,
                masterPIPMerch,
                ServerRequest.QueryRecord.HISTORY_BALANCE );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.AUTHORIZED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                mockHardwareToken,
                wrongPIP,
                ServerRequest.QueryRecord.HISTORY_BALANCE );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;
    }

    public void testDailyHistory() throws Exception {
        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                fakeHardwareToken,
                masterPIPMerch,
                ServerRequest.QueryRecord.TODAY_BALANCE );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                mockHardwareToken,
                masterPIPMerch,
                ServerRequest.QueryRecord.TODAY_BALANCE );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.AUTHORIZED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                mockHardwareToken,
                wrongPIP,
                ServerRequest.QueryRecord.TODAY_BALANCE );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;
    }

    public void testCurrency() throws Exception {
        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                mockHardwareToken,
                ServerRequest.QueryRecord.MERCHANT_CURRENCY );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.AUTHORIZED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                fakeHardwareToken,
                ServerRequest.QueryRecord.MERCHANT_CURRENCY );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;
    }

    public void testLogo() throws Exception {
        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                mockHardwareToken,
                ServerRequest.QueryRecord.MERCHANT_LOGO );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.AUTHORIZED, response.getCode() );
        response = null;

        YodoRequest.getInstance( ac ).requestQuery(
                QUERY_REQ,
                fakeHardwareToken,
                ServerRequest.QueryRecord.MERCHANT_LOGO );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;
    }

    public void testAlternate() throws Exception {
        YodoRequest.getInstance( ac ).requestAlternate(
                EXCH_REQ, "3", fakeHardwareToken, "", "0.00", "0.00", "0.00" , 0.00, 0.00, "CAD"
        );
        semaphore.acquire();

        assertNotNull( response );
        assertEquals( ServerResponse.ERROR_FAILED, response.getCode() );
        response = null;
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onResponse( int responseCode, ServerResponse response ) {
        this.response = response;
        semaphore.release();
    }
}
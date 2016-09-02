package co.yodo.yodoapi.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import co.yodo.restapi.network.ApiClient;
import co.yodo.restapi.network.model.ServerResponse;
import co.yodo.restapi.network.request.AlternateRequest;
import co.yodo.restapi.network.request.AuthenticateRequest;
import co.yodo.restapi.network.request.CurrenciesRequest;
import co.yodo.restapi.network.request.ExchangeRequest;
import co.yodo.restapi.network.request.QueryRequest;
import co.yodo.yodoapi.R;

public class MainActivity extends AppCompatActivity implements ApiClient.RequestsListener {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = MainActivity.class.getSimpleName();

    private ApiClient mRequestManager;

    /** Response codes for the server requests */
    private static final int AUTH_REQ = 0x00;
    private static final int EXCH_REQ = 0x01;
    private static final int QURY_REQ = 0x02;
    private static final int CURR_REQ = 0x03;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        FloatingActionButton fab = ( FloatingActionButton ) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );

        mRequestManager = ApiClient.getInstance( this );
        mRequestManager.setListener( this );
        mRequestManager.invoke(
                new AuthenticateRequest(
                        AUTH_REQ,
                        "4C21D00D7ABD"
                )
        );

        mRequestManager.invoke(
                new ExchangeRequest(
                        EXCH_REQ,
                        "4C21D00D7ABD",
                        "test",
                        "0.00",
                        "0.00",
                        "0.00",
                        0.00,
                        0.00,
                        "CAD"
                )
        );

        mRequestManager.invoke(
                new AlternateRequest(
                        EXCH_REQ,
                        "3",
                        "4C21D00D7ABD",
                        "test",
                        "0.00",
                        "0.00",
                        "0.00",
                        0.00,
                        0.00,
                        "CAD"
                )
        );

        mRequestManager.invoke(
                new QueryRequest(
                        QURY_REQ,
                        "4C21D00D7ABD",
                        QueryRequest.Record.MERCHANT_LOGO
                )
        );

        mRequestManager.invoke(
                new QueryRequest(
                        QURY_REQ,
                        "4C21D00D7ABD",
                        QueryRequest.Record.MERCHANT_CURRENCY
                )
        );

        mRequestManager.invoke(
                new QueryRequest(
                        QURY_REQ,
                        "4C21D00D7ABD",
                        "test",
                        QueryRequest.Record.HISTORY_BALANCE
                )
        );

        mRequestManager.invoke(
                new QueryRequest(
                        QURY_REQ,
                        "4C21D00D7ABD",
                        "test",
                        QueryRequest.Record.TODAY_BALANCE
                )
        );

        mRequestManager.invoke(
                new CurrenciesRequest(
                        CURR_REQ,
                        "CAD",
                        "BRL"
                )
        );
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onResponse( int responseCode, ServerResponse response ) {
        final String code = response.getCode();

        // Verify the type of the request
        switch( responseCode ) {
            case AUTH_REQ:
                Log.e( TAG, code );
                break;
        }
    }
}

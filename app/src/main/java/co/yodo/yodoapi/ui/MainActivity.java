package co.yodo.yodoapi.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import co.yodo.restapi.YodoApi;
import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.model.ServerResponse;
import co.yodo.restapi.network.requests.CurrenciesRequest;
import co.yodo.yodoapi.R;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
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

        YodoApi.execute(
                new CurrenciesRequest("CAD", "USD"),
                new RequestCallback() {
                    @Override
                    public void onPrepare() {

                    }

                    @Override
                    public void onResponse(ServerResponse response) {
                        Timber.i(response.toString());
                    }

                    @Override
                    public void onError( Throwable error ) {

                    }
                }
        );

    }

        /*mRequestManager = ApiClient.getInstance( this );
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

    @Override
    public void onError( Throwable error, String message ) {

    }*/
}

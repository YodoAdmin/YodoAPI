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
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        FloatingActionButton fab = findViewById( R.id.fab );
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
}

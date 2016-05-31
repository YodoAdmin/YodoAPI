package co.yodo.yodoapi.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import co.yodo.restapi.network.YodoRequest;
import co.yodo.restapi.network.builder.ServerRequest;
import co.yodo.restapi.network.model.ServerResponse;
import co.yodo.yodoapi.R;

public class MainActivity extends AppCompatActivity implements YodoRequest.RESTListener {

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

        /*YodoRequest.getInstance( getApplicationContext() ).createProgressDialog( YodoRequest.ProgressDialogType.NORMAL );

        new Handler().postDelayed( stop , 5000 );*/

        /*YodoRequest.getInstance( MainActivity.this ).setListener( this );
        YodoRequest.getInstance( MainActivity.this ).requestMerchAuth( 1, "asd" );
        YodoRequest.getInstance( MainActivity.this ).requestMerchAuth( 1, "354984054060899" );
        YodoRequest.getInstance( MainActivity.this ).requestQuery(
                1,
                "354984054060899",
                "test",
                ServerRequest.QueryRecord.HISTORY_BALANCE );*/
    }

    private Runnable stop = new Runnable() {
        @Override
        public void run() {
            YodoRequest.getInstance( getApplicationContext() ).destroyProgressDialog();
        }
    };

    @Override
    public void onResponse( int i, ServerResponse serverResponse ) {

    }
}

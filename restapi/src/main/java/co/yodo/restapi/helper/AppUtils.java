package co.yodo.restapi.helper;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by hei on 26/04/16.
 * Utilities for the App
 */
public class AppUtils {
    /**
     * Logger for the application, depending on the flag DEBUG
     * @param type The char type of the log
     * @param TAG The String used to know to which class the log belongs
     * @param text The String to output on the log
     */
    public static void Logger( char type, @NonNull String TAG, String text ) {
        if( text == null ) {
            Log.e( TAG, "NULL Message" );
            return;
        }

        if( AppConfig.DEBUG ) {
            switch( type ) {
                case 'i': // Information
                    Log.i( TAG, text );
                    break;

                case 'w': // Warning
                    Log.w( TAG, text );
                    break;

                case 'e': // Error
                    Log.e( TAG, text );
                    break;

                case 'd': // Debug
                    Log.d( TAG, text );
                    break;

                default:
                    Log.d( TAG, text );
                    break;
            }
        }
    }
}

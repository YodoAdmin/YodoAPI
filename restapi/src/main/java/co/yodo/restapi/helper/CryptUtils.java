package co.yodo.restapi.helper;

/**
 * Created by hei on 10/06/16.
 * Common utils for the encryption
 */
public class CryptUtils {
    /** DEBUG */
    @SuppressWarnings( "unused" )
    private static final String TAG = CryptUtils.class.getSimpleName();

    /**
     * Receives an encrypted byte array and returns a string of
     * hexadecimal numbers that represents it
     * @param data Encrypted byte array
     * @return           String of hexadecimal number
     */
    public static String bytesToHex( byte[] data ) {
        if( data == null )
            return null;

        String str = "";
        for( byte aData : data ) {
            if( ( aData & 0xFF ) < 16 )
                str = str + "0" + Integer.toHexString( aData & 0xFF );
            else
                str = str + Integer.toHexString( aData & 0xFF );
        }
        return str;
    }
}

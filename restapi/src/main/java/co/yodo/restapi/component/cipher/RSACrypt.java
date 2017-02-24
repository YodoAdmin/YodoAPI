package co.yodo.restapi.component.cipher;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.DataInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import co.yodo.restapi.helper.CryptUtils;
import co.yodo.restapi.network.ApiClient;

/**
 * Created by hei on 15/07/16.
 * RSA implementation
 */
public class RSACrypt {
	/** DEBUG */
	@SuppressWarnings( "unused" )
	private final static String TAG = RSACrypt.class.getSimpleName();

	/**
	 * Public key generated with: openssl rsa -in 11.private.pem -pubout -outform DER -out 11.public.der
	 * This key is created using the private key generated using openssl in unix environments
	 */
	private static String PUBLIC_KEY;

	static {
		if( ApiClient.getSwitch().equals( "P" ) ) {
			// Production
			PUBLIC_KEY = "YodoKey/Prod/2048.public.der";
		} else if( ApiClient.getSwitch().equals( "E" ) ) {
			// Demo
			PUBLIC_KEY = "YodoKey/Demo/2048.public.der";
		} else if( ApiClient.getSwitch().equals( "D" ) ) {
			// Development
			PUBLIC_KEY = "YodoKey/Dev/2048.public.der";
		} else {
			// Local
			PUBLIC_KEY = "YodoKey/Local/2048.public.der";
		}
	}

	private final PublicKey publicKey;

	/** Public key instance */
	private static final String KEY_INSTANCE = "RSA";

	/** Cipher instance used for encryption */
	private static final String CIPHER_INSTANCE = "RSA/None/PKCS1Padding";

	/**
	 * Private constructor for the singleton
	 * @param context The Android context for the application
	 */
	public RSACrypt( Context context ) {
		publicKey = readPublicKey( context );
	}

	public RSACrypt() {
		publicKey = null;
	}

	/**
	 * Function that opens the public key and returns the java object that contains it
	 * @param context    Context of the application
	 * @return PublicKey The public key specified in PUBLIC_KEY
	 */
	private static PublicKey readPublicKey( Context context ) {
		PublicKey pkPublic = null;

		try {
			AssetManager as = context.getAssets();
			InputStream inFile = as.open( PUBLIC_KEY );
			DataInputStream dis = new DataInputStream( inFile );

			byte[] encodedKey = new byte[inFile.available()];
			dis.readFully( encodedKey );
			dis.close();

			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec( encodedKey );
			KeyFactory kf = KeyFactory.getInstance( KEY_INSTANCE );
			pkPublic = kf.generatePublic( publicKeySpec );
		} catch( Exception e ) {
			e.printStackTrace();
		}

		return pkPublic;
	}

	/**
	 * Encrypts a string and transforms the byte array containing
	 * the encrypted string to Hex format
	 * @param plainText The unencrypted string
	 * @return String   The encrypted string in Hex
	 */
	public String encrypt( String plainText ) {
		String encryptedData = null;

		try {
			final Cipher cipher = Cipher.getInstance( CIPHER_INSTANCE );
			cipher.init( Cipher.ENCRYPT_MODE, publicKey );
			final byte[] cipherData = cipher.doFinal( plainText.getBytes( "UTF-8" ) );
			encryptedData = CryptUtils.bytesToHex( cipherData );
		} catch( Exception e ) {
			e.printStackTrace();
		}

		return encryptedData;
	}
}

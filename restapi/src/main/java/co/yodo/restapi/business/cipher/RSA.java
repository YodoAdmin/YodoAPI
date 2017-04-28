package co.yodo.restapi.business.cipher;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.DataInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import co.yodo.restapi.business.cipher.contract.ICipher;
import co.yodo.restapi.utils.CryptUtils;

/**
 * Created by hei on 15/07/16.
 * RSA implementation
 */
public final class RSA implements ICipher {
	/** Public key instance */
	private static final String KEY_INSTANCE = "RSA";

	/** Cipher instance used for encryption */
	private static final String CIPHER_INSTANCE = "RSA/None/PKCS1Padding";

	/**
	 * Public key generated with: openssl rsa -in 11.private.pem -pubout -outform DER -out 11.public.der
	 * This key is created using the private key generated using openssl in unix environments
	 */
	private final PublicKey publicKey;

	/**
	 * Private constructor for the singleton
	 * @param context The Android context for the application
	 */
	public RSA( Context context, String path) {
		publicKey = readPublicKey(context, path);
	}

	/**
	 * Function that opens the public key and returns the java object that contains it
	 * @param context Context of the application
	 * @return PublicKey The public key specified in PUBLIC_KEY
	 */
	private PublicKey readPublicKey(Context context, String path) {
		PublicKey pkPublic = null;

		try {
			AssetManager as = context.getAssets();
			InputStream inFile = as.open(path);
			DataInputStream dis = new DataInputStream(inFile);

			byte[] encodedKey = new byte[inFile.available()];
			dis.readFully(encodedKey);
			dis.close();

			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
			KeyFactory kf = KeyFactory.getInstance(KEY_INSTANCE);
			pkPublic = kf.generatePublic(publicKeySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pkPublic;
	}

	@Override
	public String encrypt(String plainText) {
		String encryptedData = null;

		try {
			final Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			final byte[] cipherData = cipher.doFinal( plainText.getBytes("UTF-8"));
			encryptedData = CryptUtils.bytesToHex(cipherData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encryptedData;
	}
}

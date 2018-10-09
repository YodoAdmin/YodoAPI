package co.yodo.restapi.business.cipher.contract;

/**
 * Created by hei on 20/04/17.
 * Interface for the ciphers
 */
public interface ICipher {
    /**
     * Encrypts a string and transforms the byte array containing
     * the encrypted string to Hex format
     * @param plainText The unencrypted string
     * @return String The encrypted string in Hex
     */
    String encrypt(String plainText);
}

package co.yodo.restapi.network.requests.contract;

import javax.crypto.spec.SecretKeySpec;

import co.yodo.restapi.business.cipher.AES;

/**
 * Created by hei on 21/04/17.
 * Implements the basic logic for the alternate
 */
public abstract class AltRequest extends Request {
    /** Alternate request type */
    private static final String ALT_RT = "7";

    /** Authenticate sub request */
    private final String altSt;

    /** Alternate client */
    private final String secondaryClient;

    public AltRequest(String altSt, String secondaryClient) {
        this.altSt = altSt;
        this.secondaryClient = secondaryClient;
    }

    @Override
    protected String buildBody() {
        SecretKeySpec key = AES.generateKey();
        encryptedKey = cipher.encrypt(AES.encodeKey(key));
        encryptedData = AES.encrypt(identifier, key) + REQ_SEP +
                AES.encrypt(secondaryClient, key) + REQ_SEP +
                AES.encrypt(formattedData, key);

        // Formatting the request
        final String data = encryptedKey + REQ_SEP + encryptedData;
        return PROTOCOL_VERSION + REQ_SEP +
                ALT_RT + REQ_SEP +
                altSt + REQ_SEP +
                data;
    }
}

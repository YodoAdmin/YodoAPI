package co.yodo.restapi.network.requests.contract;

import javax.crypto.spec.SecretKeySpec;

import co.yodo.restapi.business.cipher.AES;

/**
 * Created by hei on 19/04/17.
 * Implements the basic logic for the authentication
 */
public abstract class AuthRequest extends Request {
    /** Authenticate request type */
    private static final String AUTH_RT = "0";

    /** Authenticate sub request */
    private final String authSt;

    public AuthRequest(String authSt) {
        this.authSt = authSt;
    }

    @Override
    protected String buildBody() {
        SecretKeySpec key = AES.generateKey();
        encryptedKey = cipher.encrypt(AES.encodeKey(key));
        encryptedData = AES.encrypt(formattedData, key);

        // Formatting the request
        final String data = encryptedKey + REQ_SEP + encryptedData;
        return PROTOCOL_VERSION + REQ_SEP +
                AUTH_RT + REQ_SEP +
                authSt + REQ_SEP +
                data;
    }
}

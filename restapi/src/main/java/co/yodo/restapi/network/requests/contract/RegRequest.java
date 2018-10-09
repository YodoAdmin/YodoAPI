package co.yodo.restapi.network.requests.contract;

import javax.crypto.spec.SecretKeySpec;

import co.yodo.restapi.business.cipher.AES;

/**
 * Created by hei on 21/04/17.
 * Implements the basic logic for the registration
 */
public abstract class RegRequest extends Request {
    /** Registration request type */
    private static final String REG_RT = "9";

    /** Registration sub request */
    private final String regSt;

    public RegRequest(String regSt) {
        this.regSt = regSt;
    }

    @Override
    protected String buildBody() {
        SecretKeySpec key = AES.generateKey();
        encryptedKey = cipher.encrypt(AES.encodeKey(key));
        encryptedData = AES.encrypt(formattedData, key);

        // Formatting the request
        final String data = encryptedKey + REQ_SEP + encryptedData;
        return PROTOCOL_VERSION + REQ_SEP +
                REG_RT + REQ_SEP +
                regSt + REQ_SEP +
                data;
    }
}

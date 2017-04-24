package co.yodo.restapi.network.requests.contract;

import javax.crypto.spec.SecretKeySpec;

import co.yodo.restapi.business.cipher.AES;

/**
 * Created by hei on 21/04/17.
 * Implements the basic logic for the exchange
 */
public abstract class ExchRequest extends Request {
    /** Exchange request type */
    private static final String EXCH_RT = "1";

    /** Exchange sub request */
    private final String exchSt;

    /** Exchange client */
    private final String secondaryClient;

    public ExchRequest(String exchSt, String secondaryClient) {
        this.exchSt = exchSt;
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
                EXCH_RT + REQ_SEP +
                exchSt + REQ_SEP +
                data;
    }
}

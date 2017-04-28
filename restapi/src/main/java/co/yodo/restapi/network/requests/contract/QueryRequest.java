package co.yodo.restapi.network.requests.contract;

import javax.crypto.spec.SecretKeySpec;

import co.yodo.restapi.business.cipher.AES;

/**
 * Created by hei on 25/08/16.
 * Request a query from the server
 */
public abstract class QueryRequest extends Request {
    /** Query request type */
    private static final String QUERY_RT = "4";

    /** Query sub request */
    private static final String QUERY_ACC_ST = "3";

    @Override
    protected String buildBody() {
        SecretKeySpec key = AES.generateKey();
        encryptedKey = cipher.encrypt(AES.encodeKey(key));
        encryptedData = AES.encrypt(formattedData, key);

        // Formatting the request
        final String data = encryptedKey + REQ_SEP + encryptedData;
        return PROTOCOL_VERSION + REQ_SEP +
                QUERY_RT + REQ_SEP +
                QUERY_ACC_ST + REQ_SEP +
                data;
    }
}

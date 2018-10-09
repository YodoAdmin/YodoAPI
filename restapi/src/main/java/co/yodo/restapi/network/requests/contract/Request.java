package co.yodo.restapi.network.requests.contract;

import android.content.Context;

import co.yodo.restapi.ApiClient;
import co.yodo.restapi.business.cipher.contract.ICipher;
import co.yodo.restapi.network.libraries.contract.IClient;
import co.yodo.restapi.utils.PrefUtils;

/**
 * Created by hei on 16/07/16.
 * Class used to implement the Command Design Pattern for the requests
 */
public abstract class Request implements ICommand {
    /** Protocol version used in the requests */
    static final String PROTOCOL_VERSION = "1.1.6";

    /** Application context */
    protected final Context context;

    /** Object to encrypt data */
    protected final ICipher cipher;

    /** Object to execute the requests */
    protected final IClient client;

    /** Device identifier */
    protected final String identifier;

    /** Formatted data for the request */
    protected String formattedData;

    /** Encrypted data for the request */
    String encryptedData;
    String encryptedKey;

    /** Data separator */
    protected static final String REQ_SEP = ",";
    protected static final String PCLIENT_SEP = "/";

    public Request() {
        // Get components
        cipher = ApiClient.getComponent().getCipher();
        client = ApiClient.getComponent().getClient();

        // Get device identifier
        context = ApiClient.getComponent().getContext();
        identifier = PrefUtils.getIdentifier(context);
    }

    /**
     * Builds the request
     * @return The formatted request
     */
    protected abstract String buildBody();
}

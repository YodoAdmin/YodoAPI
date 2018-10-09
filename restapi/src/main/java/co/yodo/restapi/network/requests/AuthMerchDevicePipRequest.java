package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.AuthRequest;

/**
 * Created by hei on 21/04/17.
 * Authenticate the device with the pip
 */
public final class AuthMerchDevicePipRequest extends AuthRequest {
    /** Authenticate request sub-type */
    private static final String AUTH_MERCH_HW_PIP_ST = "5";

    public AuthMerchDevicePipRequest(String pip) {
        super(AUTH_MERCH_HW_PIP_ST);
        formattedData = identifier + PCLIENT_SEP +
                pip + PCLIENT_SEP +
                System.currentTimeMillis() / 1000L;
    }

    @Override
    public void execute(RequestCallback callback) {
        client.sendXmlRequest(buildBody(), callback);
    }
}

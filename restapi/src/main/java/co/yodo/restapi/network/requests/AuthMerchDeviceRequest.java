package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.AuthRequest;

/**
 * Created by hei on 21/04/17.
 * authenticates the device
 */
public final class AuthMerchDeviceRequest extends AuthRequest {
    /** Authenticate request sub-type */
    private static final String AUTH_MERCH_HW_ST = "4";

    public AuthMerchDeviceRequest() {
        super(AUTH_MERCH_HW_ST);
        formattedData = identifier;
    }

    @Override
    public void execute(RequestCallback callback) {
        client.sendXmlRequest(buildBody(), callback);
    }
}

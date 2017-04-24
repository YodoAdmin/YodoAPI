package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.RegRequest;

/**
 * Created by hei on 21/04/17.
 * Registers a device as POS
 */
public final class RegMerchDeviceRequest extends RegRequest {
    /** Registration request sub-type */
    private static final String REG_MERCH_ST = "1";

    public RegMerchDeviceRequest(String activationCode) {
        super(REG_MERCH_ST);
        formattedData = identifier + REQ_SEP +
                activationCode + REQ_SEP +
                System.currentTimeMillis() / 1000L;
    }

    @Override
    public void execute(RequestCallback callback) {
        client.sendXmlRequest(buildBody(), callback);
    }
}

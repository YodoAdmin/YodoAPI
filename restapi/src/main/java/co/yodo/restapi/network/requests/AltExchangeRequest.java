package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.AltRequest;

/**
 * Created by hei on 21/04/17.
 * Handles different exchange requests - alternate primary clients
 *
 * Sub-types:
 * CASH_ST        - 0
 * VISA_CRED_ST   - 1
 * HEART_ST       - 3
 * VISA_PREP_ST   - 4
 * PAYPAL_ST      - 5
 * PUB_TRANSIT_ST - 6
 */
public final class AltExchangeRequest extends AltRequest {
    public AltExchangeRequest(String altSt, String secondaryClient, String total, String cashTender, String cashBack,
                             double latitude, double longitude, String currency) {
        super(altSt, secondaryClient);
        formattedData = latitude + REQ_SEP +
                longitude + REQ_SEP +
                total + REQ_SEP +
                cashTender + REQ_SEP +
                cashBack + REQ_SEP +
                currency;
    }

    @Override
    public void execute(RequestCallback callback) {
        client.sendXmlRequest(buildBody(), callback);
    }
}

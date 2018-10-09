package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.ExchRequest;
import timber.log.Timber;

/**
 * Created by hei on 21/04/17.
 * Makes a normal exchange between a primary and secondary client
 */
public final class ExchRetailRequest extends ExchRequest {
    /** Exchange request sub-type */
    private static final String EXCH_RETAIL_ST = "1";

    public ExchRetailRequest(String secondaryClient, String total, String cashTender, String cashBack,
                             double latitude, double longitude, String currency) {
        super(EXCH_RETAIL_ST, secondaryClient);
        formattedData = latitude + REQ_SEP +
                longitude + REQ_SEP +
                total + REQ_SEP +
                cashTender + REQ_SEP +
                cashBack + REQ_SEP +
                currency;
        Timber.d(secondaryClient);
    }

    @Override
    public void execute(RequestCallback callback) {
        Timber.d(formattedData);
        client.sendXmlRequest(buildBody(), callback);
    }
}

package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.QueryRequest;

/**
 * Created by hei on 21/04/17.
 * Request the currency from the server
 */
public final class QueryCurrencyRequest extends QueryRequest {
    /** Query request record */
    private static final String RECORD_CURRENCY = "13";

    public QueryCurrencyRequest() {
        formattedData = identifier + REQ_SEP + RECORD_CURRENCY;
    }

    @Override
    public void execute(RequestCallback callback) {
        client.sendXmlRequest(buildBody(), callback);
    }
}

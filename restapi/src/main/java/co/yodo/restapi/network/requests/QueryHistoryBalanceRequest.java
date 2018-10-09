package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.QueryRequest;

/**
 * Created by hei on 21/04/17.
 * Request the merchant history balance from the server
 */
public final class QueryHistoryBalanceRequest extends QueryRequest {
    /** Query request record */
    private static final String RECORD_HISTORY = "10";

    public QueryHistoryBalanceRequest(String pip) {
        formattedData = identifier + REQ_SEP + pip + REQ_SEP + RECORD_HISTORY;
    }

    @Override
    public void execute(RequestCallback callback) {
        client.sendXmlRequest(buildBody(), callback);
    }
}

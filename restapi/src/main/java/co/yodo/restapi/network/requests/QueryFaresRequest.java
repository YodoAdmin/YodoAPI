package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.QueryRequest;

/**
 * Created by hei on 21/04/17.
 * Request the merchant fares from the server
 */
public class QueryFaresRequest extends QueryRequest {
    /** Query request record */
    private static final String RECORD_FARES = "15";

    public QueryFaresRequest() {
        formattedData = identifier + REQ_SEP + RECORD_FARES;
    }

    @Override
    public void execute(RequestCallback callback) {
        client.sendXmlRequest(buildBody(), callback);
    }
}

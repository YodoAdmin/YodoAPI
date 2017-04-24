package co.yodo.restapi.network.requests;

import co.yodo.restapi.network.contract.RequestCallback;
import co.yodo.restapi.network.requests.contract.QueryRequest;

/**
 * Created by hei on 21/04/17.
 * Request the merchant logo from the server
 */
public final class QueryLogoRequest extends QueryRequest {
    /** Query request record */
    private static final String RECORD_LOGO = "14";

    public QueryLogoRequest() {
        formattedData = identifier + REQ_SEP + RECORD_LOGO;
    }

    @Override
    public void execute(RequestCallback callback) {
        client.sendXmlRequest(buildBody(), callback);
    }
}

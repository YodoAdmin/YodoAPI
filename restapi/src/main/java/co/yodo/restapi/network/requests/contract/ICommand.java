package co.yodo.restapi.network.requests.contract;

import co.yodo.restapi.network.contract.RequestCallback;

/**
 * Created by hei on 19/04/17.
 * Interface for the command interface
 */
public interface ICommand {
    /**
     * Executes the command (request)
     * @param callback The receiver of the response
     */
    void execute(RequestCallback callback);
}

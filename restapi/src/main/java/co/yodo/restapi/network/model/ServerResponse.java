package co.yodo.restapi.network.model;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by hei on 26/04/16.
 * Describes the data for the server responses
 */
public class ServerResponse {
    /** ID for authorized responses */
    public static final String AUTHORIZED		       = "AU00";
    public static final String AUTHORIZED_REGISTRATION = "AU01";
    public static final String AUTHORIZED_BALANCE      = "AU55";
    public static final String AUTHORIZED_ALTERNATE    = "AU69";
    public static final String AUTHORIZED_TRANSFER     = "AU88";

    /** ID for error responses */
    public static final String ERROR_UNKOWN        = "UNKN";
    public static final String ERROR_NETWORK       = "NCON";
    public static final String ERROR_TIMEOUT       = "TOUT";
    public static final String ERROR_SERVER        = "ESRV";
    public static final String ERROR_FAILED        = "ER00";
    public static final String ERROR_MAX_LIM       = "ER13";
    public static final String ERROR_DUP_AUTH      = "ER20";
    public static final String ERROR_NO_BALANCE    = "ER21";
    public static final String ERROR_INCORRECT_PIP = "ER22";
    public static final String ERROR_INSUFF_FUNDS  = "ER25";

    private String code;
    private String authNumber;
    private String message;
    private long rtime;
    private HashMap<String, String> params = new HashMap<>();

    public void setCode( String code ) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setAuthNumber( String authNumber ) {
        this.authNumber = authNumber;
    }

    public String getAuthNumber() {
        return this.authNumber;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setRTime( long rtime ) {
        this.rtime = rtime;
    }

    public long getRTime() {
        return this.rtime;
    }

    public void addParam( String key, String value ) {
        params.put( key, value );
    }

    public String getParam( String key ) {
        return params.get( key );
    }

    public HashMap<String, String> getParams() { return params; }

    @Override
    public String toString() {
        return "\nCode : "       + this.code       + "\n" +
                " AuthNumber : " + this.authNumber + "\n" +
                " Message : "    + this.message    + "\n" +
                " Time : "       + this.rtime      + "\n" +
                " Params : "     + Collections.singletonList( this.params );
    }
}

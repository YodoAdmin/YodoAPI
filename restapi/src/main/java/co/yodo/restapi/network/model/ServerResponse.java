package co.yodo.restapi.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by hei on 26/04/16.
 * Describes the data for the server responses
 */
@Root( name = "Yodoresponse", strict = false )
public class ServerResponse {
    /** ID for authorized responses */
    public static final String AUTHORIZED		       = "AU00";
    public static final String AUTHORIZED_REGISTRATION = "AU01";
    public static final String AUTHORIZED_BALANCE      = "AU55";
    public static final String AUTHORIZED_ALTERNATE    = "AU69";
    public static final String AUTHORIZED_TRANSFER     = "AU88";

    /** ID for error responses */
    public static final String ERROR_FAILED        = "ER00";
    public static final String ERROR_MAX_LIM       = "ER13";
    public static final String ERROR_DUP_AUTH      = "ER20";
    public static final String ERROR_NO_BALANCE    = "ER21";
    public static final String ERROR_INCORRECT_PIP = "ER22";
    public static final String ERROR_INSUFF_FUNDS  = "ER25";

    @Element( name = "code" )
    private String code;

    @Element( name = "authNumber" )
    private String authNumber;

    @Element( name = "message", required = false )
    private String message;

    @Element
    private Params params;

    @Element( name = "rtime" )
    private long rtime;

    public void setCode( String code ) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public String getAuthNumber() {
        return this.authNumber;
    }

    public String getMessage() {
        return this.message;
    }

    public Params getParams() {
        return this.params;
    }

    public long getRTime() {
        return this.rtime;
    }

    public void setParams( Params params ) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "\nCode : "       + this.code       + "\n" +
                " AuthNumber : " + this.authNumber + "\n" +
                " Message : "    + this.message    + "\n" +
                " Time : "       + this.rtime      + "\n" +
                " Params : "     + this.params.toString();
    }
}


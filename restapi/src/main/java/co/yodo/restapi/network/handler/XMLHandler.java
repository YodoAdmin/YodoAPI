package co.yodo.restapi.network.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import co.yodo.restapi.network.model.ServerResponse;

// Import all the tags for the xml
import static co.yodo.restapi.network.model.ServerResponse.*;

/**
 * Created by hei on 26/04/16.
 * Handler for the XML responses
 */
public class XMLHandler extends DefaultHandler {
    /** XML root element */
    private static final String ROOT = "Yodoresponse";

    /** XML sub root element */
    private static final String CODE    = "code";
    private static final String AUTHNUM = "authNumber";
    private static final String MESSAGE = "message";
    public static final String PARAMS   = "params";
    private static final String TIME    = "rtime";

    /** Server Response POJO */
    public static ServerResponse response = null;

    /** Parser Elements */
    private Boolean currentElement = false;
    private String currentValue = null;

    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes ) throws SAXException {
        currentElement = true;

        if( localName.equalsIgnoreCase( ROOT ) ) {
            /** Start */
            response = new ServerResponse();
        }
    }

    /** Called when tag closing ( ex:- <name>AndroidPeople</name>
     * -- </name> )*/
    @Override
    public void endElement( String uri, String localName, String qName ) throws SAXException {
        currentElement = false;

        /** set value */
        if( localName.equalsIgnoreCase( CODE ) ) {
            response.setCode( currentValue );
        }
        else if( localName.equalsIgnoreCase( AUTHNUM ) ) {
            response.setAuthNumber( currentValue );
        }
        else if( localName.equalsIgnoreCase( MESSAGE ) ) {
            response.setMessage( currentValue );
        }
        else if( localName.equalsIgnoreCase( TIME ) ) {
            response.setRTime( Long.valueOf( currentValue ) );
        }

        /** Params */
        else if( localName.equalsIgnoreCase( PARAMS ) ) {
            response.addParam( PARAMS, currentValue );
        }
        else if( localName.equalsIgnoreCase( LOGO ) ) {
            response.addParam( LOGO, currentValue );
        }
        else if( localName.equalsIgnoreCase( DEBIT ) ) {
            response.addParam( DEBIT, currentValue );
        }
        else if( localName.equalsIgnoreCase( CREDIT ) ) {
            response.addParam( CREDIT, currentValue );
        }
        else if( localName.equalsIgnoreCase( SETTLEMENT ) ) {
            response.addParam( SETTLEMENT, currentValue );
        }
        else if( localName.equalsIgnoreCase( CURRENCY ) ) {
            response.addParam( CURRENCY, currentValue );
        }
        else if( localName.equalsIgnoreCase( EQUIPMENT ) ) {
            response.addParam( EQUIPMENT, currentValue );
        }
        else if( localName.equalsIgnoreCase( LEASE ) ) {
            response.addParam( LEASE, currentValue );
        }
        else if( localName.equalsIgnoreCase( TOTAL_LEASE ) ) {
            response.addParam( TOTAL_LEASE, currentValue );
        }
        else if( localName.equalsIgnoreCase( ACCOUNT ) ) {
            response.addParam( ACCOUNT, currentValue );
        }
        else if( localName.equalsIgnoreCase( PURCHASE ) ) {
            response.addParam( PURCHASE, currentValue );
        }
        else if( localName.equalsIgnoreCase( AMOUNT_DELTA ) ) {
            response.addParam( AMOUNT_DELTA, currentValue );
        }
    }

    /** Called to get tag characters ( ex:- <name>AndroidPeople</name>
     * -- to get AndroidPeople Character ) */
    @Override
    public void characters( char[] ch, int start, int length ) throws SAXException {
        if( currentElement ) {
            currentValue = new String( ch, start, length );
            currentElement = false;
        }
    }
}

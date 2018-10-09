package co.yodo.restapi.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by hei on 14/03/17.
 * Fares
 */
@Root( strict = false )
public class Fare {
    @Element( name = "fare_id", required = false )
    private String id;

    @Element( name = "fare", required = false )
    private String name;

    @Element( name = "price", required = false )
    private String price;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}

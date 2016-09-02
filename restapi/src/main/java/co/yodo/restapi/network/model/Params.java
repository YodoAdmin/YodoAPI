package co.yodo.restapi.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root( strict = false )
public class Params {
    @Element( name = "account", required = false )
    private String account;

    @Element( name = "purchase_price", required = false )
    private String purchase;

    @Element( name = "amount_delta", required = false )
    private String amountDelta;

    @Element( name = "merch_rate", required = false )
    private String merchRate;

    @Element( name = "tender_rate", required = false )
    private String tenderRate;

    @Element( name = "MerchantDebitWTCost", required = false )
    private String debit;

    @Element( name = "MerchantCreditWTCost", required = false )
    private String credit;

    @Element( name = "Settlement", required = false )
    private String settlement;

    @Element( name = "Equipments", required = false )
    private String equipment;

    @Element( name = "Lease", required = false )
    private String lease;

    @Element( name = "TotalLease", required = false )
    private String totalLease;

    @Element( name = "DefaultCurrency", required = false )
    private String currency;

    @Element( name = "logo_url", required = false )
    private String logo;

    /** Getters */
    public String getAccount() {
        return account;
    }

    public String getPurchase() {
        return purchase;
    }

    public String getAmountDelta() {
        return amountDelta;
    }

    public String getMerchRate() {
        return merchRate;
    }

    public String getTenderRate() {
        return tenderRate;
    }

    public String getDebit() {
        return debit;
    }

    public String getCredit() {
        return credit;
    }

    public String getSettlement() {
        return settlement;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getLease() {
        return lease;
    }

    public String getTotalLease() {
        return totalLease;
    }

    public String getCurrency() {
        return currency;
    }

    public String getLogo() {
        return logo;
    }

    public void setTenderRate( String rate ) {
        this.tenderRate = rate;
    }

    public void setMerchRate( String rate ) {
        this.merchRate = rate;
    }

    /************/

    @Override
    public String toString() {
        return "[" +
                "Account = "      + account     + ", " +
                "Purchase = "     + purchase    + ", " +
                "Amount Delta = " + amountDelta + ", " +
                "Merch Rate = "   + merchRate   + ", " +
                "Tender Rate = "  + tenderRate  + ", " +
                "Debit = "        + debit       + ", " +
                "Credit = "       + credit      + ", " +
                "Settlement = "   + settlement  + ", " +
                "Equipment = "    + equipment   + ", " +
                "Lease = "        + lease       + ", " +
                "Total Lease = "  + totalLease  + ", " +
                "Currency = "     + currency    + ", " +
                "Logo = "         + logo +
                "]";
    }
}
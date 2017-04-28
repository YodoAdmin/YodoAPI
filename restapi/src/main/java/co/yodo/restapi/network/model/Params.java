package co.yodo.restapi.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root( strict = false )
public class Params {
    @Element( name = "merchant", required = false )
    private String merchant;

    @Element( name = "tender_currency", required = false )
    private String tenderCurrency;

    @Element( name = "account", required = false )
    private String account;

    @Element( name = "created", required = false )
    private String created;

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

    @Element( name = "account_bal", required = false )
    private String accountBalance;

    @Element( name = "Fare0", required = false )
    private Fare elderlyZone1;

    @Element( name = "Fare1", required = false )
    private Fare elderlyZone2;

    @Element( name = "Fare2", required = false )
    private Fare elderlyZone3;

    @Element( name = "Fare3", required = false )
    private Fare adultZone1;

    @Element( name = "Fare4", required = false )
    private Fare adultZone2;

    @Element( name = "Fare5", required = false )
    private Fare adultZone3;

    @Element( name = "Fare6", required = false )
    private Fare studentZone1;

    @Element( name = "Fare7", required = false )
    private Fare studentZone2;

    @Element( name = "Fare8", required = false )
    private Fare studentZone3;

    @Element( name = "Fare9", required = false )
    private Fare childZone1;

    @Element( name = "Fare10", required = false )
    private Fare childZone2;

    @Element( name = "Fare11", required = false )
    private Fare childZone3;

    /** Getters */
    public String getMerchant() {
        return merchant;
    }

    public String getTenderCurrency() {
        return tenderCurrency;
    }

    public String getAccount() {
        return account;
    }

    public String getCreated() {
        return created;
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

    public String getAccountBalance() {
        return accountBalance;
    }

    public Fare getElderlyZone1() {
        return elderlyZone1;
    }

    public Fare getElderlyZone2() {
        return elderlyZone2;
    }

    public Fare getElderlyZone3() {
        return elderlyZone3;
    }

    public Fare getAdultZone1() {
        return adultZone1;
    }

    public Fare getAdultZone2() {
        return adultZone2;
    }

    public Fare getAdultZone3() {
        return adultZone3;
    }

    public Fare getStudentZone1() {
        return studentZone1;
    }

    public Fare getStudentZone2() {
        return studentZone2;
    }

    public Fare getStudentZone3() {
        return studentZone3;
    }

    public Fare getChildZone1() {
        return childZone1;
    }

    public Fare getChildZone2() {
        return childZone2;
    }

    public Fare getChildZone3() {
        return childZone3;
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
                "Merchant = "        + merchant       + ", " +
                "Tender Currency = " + tenderCurrency + ", " +
                "Created = "         + created        + ", " +
                "Account = "         + account        + ", " +
                "Purchase = "        + purchase       + ", " +
                "Amount Delta = "    + amountDelta    + ", " +
                "Merch Rate = "      + merchRate      + ", " +
                "Tender Rate = "     + tenderRate     + ", " +
                "Debit = "           + debit          + ", " +
                "Credit = "          + credit         + ", " +
                "Settlement = "      + settlement     + ", " +
                "Equipment = "       + equipment      + ", " +
                "Lease = "           + lease          + ", " +
                "Total Lease = "     + totalLease     + ", " +
                "Currency = "        + currency       + ", " +
                "Logo = "            + logo           + ", " +
                "Account Balance = " + accountBalance +
                "]";
    }
}
package com.example.junyeop_imaciislab.firsttechscm.util;

/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class Constant {
    private static final String serverURL = "http://166.104.142.190:50000/ftscm/";
    private static final String queryTagsTrade = serverURL + "tags_trade";
    private static final String queryLogin = serverURL + "user/login";
    private static final String queryLogout = serverURL + "user/logout";

    private static final String serverItemName = "ITEM_NAME";
    private static final String serverItemStatus = "TYPE";
    private static final String serverCategory = "NAME";
    private static final String serverExpiryDate = "VALID_TIME";
    private static final String serverStandard = "ITEM_COUNT";
    private static final String serverUnit = "UNIT";
    private static final String serverAmount = "AMOUNT";
    private static final String serverPrice = "COST";
    private static final String serverLocation = "LOCATION";
    private static final String serverCustomer = "PARTNER_NAME";
    private static final String serverTagModifiedTime =  "tag_modified_time";

    private static final String statusUnregistered = "u";
    private static final String statusStocked = "s";
    private static final String statusReleased = "r";
    private static final String statusReturned = "";
    private static final String statusDiscard = "";

    private static final String opStock = "";
    private static final String opRelease="";
    private static final String opReturn = "";
    private static final String opDiscard ="";
    private static final String opStockedCancel = "sr";
    private static final String opsReleasedCancel = "rr";
    private static final String opDiscardCancel = "";

    public static String getServerURL() {
        return serverURL;
    }

    public static String getQueryTagsTrade() {
        return queryTagsTrade;
    }

    public static String getQueryLogin() {
        return queryLogin;
    }

    public static String getQueryLogout() {
        return queryLogout;
    }

    public static String getServerItemName() {
        return serverItemName;
    }

    public static String getServerItemStatus() {
        return serverItemStatus;
    }

    public static String getServerCategory() {
        return serverCategory;
    }

    public static String getServerExpiryDate() {
        return serverExpiryDate;
    }

    public static String getServerStandard() {
        return serverStandard;
    }

    public static String getServerUnit() {
        return serverUnit;
    }

    public static String getServerAmount() {
        return serverAmount;
    }

    public static String getServerPrice() {
        return serverPrice;
    }

    public static String getServerLocation() {
        return serverLocation;
    }

    public static String getServerCustomer() {
        return serverCustomer;
    }

    public static String getServerTagModifiedTime() {
        return serverTagModifiedTime;
    }

    public static String getStatusUnregistered() {
        return statusUnregistered;
    }

    public static String getStatusStocked() {
        return statusStocked;
    }

    public static String getStatusReleased() {
        return statusReleased;
    }

    public static String getStatusReturned() {
        return statusReturned;
    }

    public static String getStatusDiscard() {
        return statusDiscard;
    }

    public static String getOpStock() {
        return opStock;
    }

    public static String getOpRelease() {
        return opRelease;
    }

    public static String getOpReturn() {
        return opReturn;
    }

    public static String getOpDiscard() {
        return opDiscard;
    }

    public static String getOpStockedCancel() {
        return opStockedCancel;
    }

    public static String getOpsReleasedCancel() {
        return opsReleasedCancel;
    }

    public static String getOpDiscardCancel() {
        return opDiscardCancel;
    }
}

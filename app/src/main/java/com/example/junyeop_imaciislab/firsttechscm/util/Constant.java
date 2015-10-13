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
    private static final String serverExpiryDate = "";
    private static final String serverStandard = "ITEM_COUNT";
    private static final String serverUnit = "UNIT";
    private static final String serverAmount = "AMOUNT";
    private static final String serverPrice = "COST";
    private static final String serverLocation = "";
    private static final String serverCustomer = "";
    private static final String serverTagModifiedTime =  "tag_modified_time";

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
}

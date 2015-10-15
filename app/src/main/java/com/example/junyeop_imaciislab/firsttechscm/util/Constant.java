package com.example.junyeop_imaciislab.firsttechscm.util;

import java.util.HashMap;

/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class Constant {
    private static final String serverURL = "http://166.104.142.190:50000/ftscm/";
    private static final String queryTagsTrade = serverURL + "tags_trade";
    private static final String queryLogin = serverURL + "user/login";
    private static final String queryLogout = serverURL + "user/logout";
    private static final String queryGetItemHistory = serverURL + "item/{item_code}/history";
    private static final String queryGetItemHistoryParameter = "{item_code}";

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

    private static final String serverItemCode = "CODE";
    private static final String serverCreatedDate = "TRADE_CREATED";
    private static final String serverStatusHitory = "TYPE";
    private static final String serverCompanyName = "NAME";

    private static final String statusUnregistered = "u";
    private static final String statusStocked = "s";
    private static final String statusReleased = "r";
    private static final String statusReturned = "b";
    private static final String statusDiscard = "d";

    private static final String opStock = "s";
    private static final String opRelease="r";
    private static final String opReturn = "b";
    private static final String opDiscard ="d";
    private static final String opStockedCancel = "sr";
    private static final String opsReleasedCancel = "rr";
    private static final String opDiscardCancel = "dr";

    private static final HashMap<String,String> enumToStatus = new HashMap<String,String>() {
        {
            put("s", "입고");
            put("r", "출고");
            put("b", "반품");
            put("d", "폐기");
            put("rr", "출고취소");
            put("br", "반품취소");
            put("dr", "폐기취소");
        }
    };

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

    public static String getQueryGetItemHistory() {
        return queryGetItemHistory;
    }

    public static String getQueryGetItemHistoryParameter() {
        return queryGetItemHistoryParameter;
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

    public static String getServerCompanyName() {
        return serverCompanyName;
    }

    public static String getServerStatusHitory() {
        return serverStatusHitory;
    }

    public static String getServerCreatedDate() {
        return serverCreatedDate;
    }

    public static String getServerItemCode() {
        return serverItemCode;
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

    public static HashMap getEnumToStatus() {
        return enumToStatus;
    }
}

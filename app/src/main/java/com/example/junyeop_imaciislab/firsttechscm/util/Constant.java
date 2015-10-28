package com.example.junyeop_imaciislab.firsttechscm.util;

import java.util.HashMap;

/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class Constant {
    /**
     *
     * User private information, Non final, set on MainActivity
     *
     * */
    private static String userName="";
    private static String sqluserTableName="";

    /**
     *
     * Application constant information, NO SETTER, ONLY GETTER
     *
     * */
    private static final String serverURL = "http://166.104.142.190:50000/ftscm/";
    private static final String queryTagsTrade = serverURL + "tags_trade";
    private static final String queryLogin = serverURL + "user/login";
    private static final String queryLogout = serverURL + "user/logout";
    private static final String queryGetItemHistory = serverURL + "item/{item_code}/history";
    private static final String queryGetItemHistoryParameter = "{item_code}";
    private static final String queryTradeStatusUpdate = serverURL + "trade/{trade_code}/status";
    private static final String queryTradeStatusUpdateParameter = "{trade_code}";
    private static final String querySearchItem = serverURL+"/item";

    private static final String serverItemName = "ITEM_NAME";
    private static final String serverItemStatus = "STATUS";
    private static final String serverCategory = "NAME";
    private static final String serverExpiryDate = "VALID_TIME";
    private static final String serverStandard = "ITEM_COUNT";
    private static final String serverUnit = "UNIT";
    private static final String serverAmount = "AMOUNT";
    private static final String serverPrice = "COST";
    private static final String serverLocation = "LOCATION";
    private static final String serverCustomer = "PARTNER_NAME";
    private static final String serverTradeCode = "TRADE_CODE";
    private static final String serverItemCodeInTag = "ITEM_CODE";
    private static final String serverTagModifiedTime =  "tag_modified_time";

    private static final String serverItemCode = "CODE";
    private static final String serverCreatedDate = "TRADE_CREATED";
    private static final String serverStatusHitory = "TYPE";
    private static final String serverCompanyName = "NAME";

    private static final String statusUnregistered = "n";
    private static final String statusStocked = "s";
    private static final String statusReleased = "r";
    private static final String statusReturned = "b";
    private static final String statusDiscard = "d";

    private static final String opStock = "s";
    private static final String opRelease="r";
    private static final String opReturn = "b";
    private static final String opDiscard ="d";
    private static final String opStockedCancel = "sr";
    private static final String opReleasedCancel = "rr";
    private static final String opReturnedCancel = "br";
    private static final String opDiscardCancel = "dr";

    private static final String sqlTagHistoryDBName = "TagHistory";
    private static final String sqlDefaultTableName = "TAG_HISTORY_TB";
    private static final String sqlCreateTable = "CREATE TABLE if not exists "+sqlDefaultTableName+"(" +
                                                "key INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "NFCtagID TEXT, " +
                                                "tagTime TEXT," +
                                                "summary TEXT);";  // MUST be used after replace sqlDefaultTableName to sqluserTableName
    private static final String sqlSelectAll = "SELECT * FROM " + sqlDefaultTableName; // MUST be used after replace sqlDefaultTableName to sqluserTableName

    private static final String sqlInventoryDBName = "Inventory";
    private static final String sqlInventoryTableName = "INVENTORY_TB";
    private static final String sqlCreateInventoryTable = "CREATE TABLE if not exists "+sqlInventoryTableName+"(" +
            "key INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ItemCode TEXT, " +
            "ItemName TEXT," +
            "summary TEXT);";

    private static final String searchedItemCode = "CODE";
    private static final String searchedItemName = "NAME";
    private static final String searchedItemCategory = "CATEGORY_NAME";
    private static final String searchedItemSubCategory = "SUB_CATEGORY_NAME";
    private static final String searchedItemUnit ="UNIT";
    private static final String searchedItemAmount ="AMOUNT";


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

    public static String getQueryTradeStatusUpdate() {
        return queryTradeStatusUpdate;
    }

    public static String getQueryTradeStatusUpdateParameter() {
        return queryTradeStatusUpdateParameter;
    }

    public static String getQuerySearchItem() {
        return querySearchItem;
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

    public static String getServerTradeCode() {
        return serverTradeCode;
    }

    public static String getServerItemCodeInTag() {
        return serverItemCodeInTag;
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

    public static String getOpReleasedCancel() {
        return opReleasedCancel;
    }

    public static String getOpReturnedCancel() {
        return opReturnedCancel;
    }

    public static String getOpDiscardCancel() {
        return opDiscardCancel;
    }

    public static HashMap getEnumToStatus() {
        return enumToStatus;
    }

    public static String getSqlTagHistoryDBName() {
        return sqlTagHistoryDBName;
    }

    public static String getSqlDefaultTableName() {
        return sqlDefaultTableName;
    }

    public static String getSqlCreateTable() {
        return sqlCreateTable;
    }

    public static String getSqlSelectAll() {
        return sqlSelectAll;
    }

    public static String getSearchedItemCode() {
        return searchedItemCode;
    }

    public static String getSearchedItemCategory() {
        return searchedItemCategory;
    }

    public static String getSearchedItemSubCategory() {
        return searchedItemSubCategory;
    }

    public static String getSearchedItemUnit() {
        return searchedItemUnit;
    }

    public static String getSearchedItemAmount() {
        return searchedItemAmount;
    }

    public static String getSearchedItemName() {
        return searchedItemName;
    }

    public static String getSqlCreateInventoryTable() {
        return sqlCreateInventoryTable;
    }

    public static String getSqlInventoryDBName() {
        return sqlInventoryDBName;
    }

    public static String getSqlInventoryTableName() {
        return sqlInventoryTableName;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Constant.userName = userName;
    }

    public static String getSqluserTableName() {
        return sqluserTableName;
    }

    public static void setSqluserTableName(String sqluserTableName) {
        Constant.sqluserTableName = sqluserTableName;
    }
}

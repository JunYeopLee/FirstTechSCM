package com.example.junyeop_imaciislab.firsttechscm.util;

/**
 * Created by junyeop_imaciislab on 2015. 10. 15..
 */
public class itemHistoryDAO {
    private String itemCode;
    private String createdDate;
    private String itemStatus;
    private String companyName;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

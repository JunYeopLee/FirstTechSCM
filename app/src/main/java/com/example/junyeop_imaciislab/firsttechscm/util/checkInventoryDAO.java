package com.example.junyeop_imaciislab.firsttechscm.util;

import java.io.Serializable;

/**
 * Created by LeeJunYeop on 2015-10-12.
 */
public class checkInventoryDAO implements Serializable {
    private String itemCode;
    private String itemName;
    private String category;
    private String standard;
    private String unit;
    private String amount;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

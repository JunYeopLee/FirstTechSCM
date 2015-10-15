package com.example.junyeop_imaciislab.firsttechscm.util;

/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class tagHistoryDAO {
    private int key;
    private String tagID;
    private String createdTime;
    private String summary;
    private Boolean isSelected;

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String modifiedTime) {
        this.createdTime = modifiedTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}

package com.example.junyeop_imaciislab.firsttechscm.util;

/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class tagHistoryDAO {
    private String tagID;
    private String modifiedTime;
    private String summary;

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

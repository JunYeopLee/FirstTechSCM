package com.example.junyeop_imaciislab.firsttechscm.util;

import java.util.ArrayList;

import com.example.junyeop_imaciislab.firsttechscm.R;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;


/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class itemDAOListWrapper {
    private static ArrayList<itemDAO> itemDAOArrayList;
    private String tagID;
    private String tagModifiedTime;

    public itemDAOListWrapper() {
        if(itemDAOArrayList==null) {
            itemDAOArrayList = new ArrayList<>();
        }
    }
    public itemDAOListWrapper(String tagID) {
        if(itemDAOArrayList==null) {
            itemDAOArrayList = new ArrayList<>();
        }
        this.tagID = tagID;
    }
    public static ArrayList<itemDAO> getItemDAOArrayList() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("tags_code", "T1510111");
        requestParams.setContentEncoding("UTF-8");
        client.get(Constant.getQueryTagsTrade(),requestParams,new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                itemDAO itemDAOObject = new itemDAO();
                itemDAOArrayList.clear();
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                itemDAO itemDAOObject = new itemDAO();
                itemDAOArrayList.clear();
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                itemDAO itemDAOObject = new itemDAO();
                itemDAOArrayList.clear();
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
                itemDAOArrayList.add(itemDAOObject);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
        return itemDAOArrayList;
    }
    public static void setItemDAOArrayList(ArrayList<itemDAO> itemDAOArrayList) {
        itemDAOListWrapper.itemDAOArrayList = itemDAOArrayList;
    }

    public String getTagModifiedTime() {
        return tagModifiedTime;
    }

    public void setTagModifiedTime(String tagModifiedTime) {
        this.tagModifiedTime = tagModifiedTime;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

}

package com.example.junyeop_imaciislab.firsttechscm.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;


/**
 * Created by LeeJunYeop on 2015-10-11.
 *
 * SAVE ITEM LIST FOR INPUT TAGID
 */
public class itemDAOListWrapper {
    private static ArrayList<itemDAO> itemDAOArrayList;
    private static String tagID;
    private static String tagModifiedTime;
    private static Context context;

    public itemDAOListWrapper(Context context,String tagID) {
        if(itemDAOArrayList==null) {
            itemDAOArrayList = new ArrayList<>();
            tagModifiedTime = "새로고침 해주세요";
        }
        this.tagID = tagID;
        this.context = context;
    }

    public static ArrayList<itemDAO> getItemDAOArrayList() {
        return itemDAOArrayList;
    }

    public static String getTagModifiedTime() {
        return tagModifiedTime;
    }

    public ArrayList<itemDAO> getItemDAOArrayListFromServer(Context context) {
        // Get connection and set params
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("tags_code", tagID);

        // Set Cookie from store(JSESSIONID)
        CookieStore cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
        for (Cookie c : cookieStore.getCookies()) {
            String cookieName = c.getName();
            String cookieValue = c.getValue();
            if(cookieName.compareTo("JSESSIONID")==0) {
                client.addHeader("Cookie",cookieName+"="+cookieValue);
                break;
            }
        }
        // Execute query for tag information and getItemDAOArrayList from server
        RequestHandle requestHandle = client.get(Constant.getQueryTagsTrade(), requestParams, new receiveTagInformaitnoHandler());
        return itemDAOArrayList;
    }

    /**
     *
     * For receive tag information from Server
     *
     */
    private class receiveTagInformaitnoHandler extends JsonHttpResponseHandler {
        ProgressDialog dialog;
        @Override
        public void onStart() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("잠시만 기다려 주세요.");
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // called when response HTTP status is "200 OK"
            try {
                if(response.getBoolean("success")) {
                    itemDAOArrayList.clear();
                    JSONArray jsonArray = response.getJSONObject("result").getJSONArray("data");
                    for( int i  = 0 ; i < jsonArray.length() ; i++ ) { // get trade information
                        itemDAO itemDAOObject = new itemDAO();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        itemDAOObject.setItemName(jsonObject.getString(Constant.getServerItemName()));
                        itemDAOObject.setItemStatus(jsonObject.getString(Constant.getServerItemStatus()));
                        itemDAOObject.setCategory(jsonObject.getString(Constant.getServerCategory()));

                        itemDAOObject.setStandard(jsonObject.getString(Constant.getServerStandard()));
                        itemDAOObject.setUnit(jsonObject.getString(Constant.getServerUnit()));
                        itemDAOObject.setAmount(jsonObject.getString(Constant.getServerAmount()));
                        itemDAOObject.setPrice(jsonObject.getString(Constant.getServerPrice()));

                        itemDAOArrayList.add(itemDAOObject);
                    }

                    tagModifiedTime = response.getJSONObject("result").getString(Constant.getServerTagModifiedTime()); // get tag modified time
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject errorResponse) {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setMessage("[태그정보 수신 실패]네트워크 연결이 불안정 합니다").show();
        }
        @Override
        public void onRetry(int retryNo) {
            // called when request is retried
        }
    }
}

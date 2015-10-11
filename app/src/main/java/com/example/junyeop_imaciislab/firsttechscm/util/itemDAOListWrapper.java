package com.example.junyeop_imaciislab.firsttechscm.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

import com.example.junyeop_imaciislab.firsttechscm.R;
import com.loopj.android.http.*;
import com.loopj.android.http.Base64;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.extras.*;


/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class itemDAOListWrapper {
    private static ArrayList<itemDAO> itemDAOArrayList;
    private String tagID;
    private String tagModifiedTime;
    private static Context context;

    public itemDAOListWrapper(Context context) {
        if(itemDAOArrayList==null) {
            itemDAOArrayList = new ArrayList<>();
        }
        this.context = context;
    }
    public itemDAOListWrapper(Context context,String tagID) {
        if(itemDAOArrayList==null) {
            itemDAOArrayList = new ArrayList<>();
        }
        this.tagID = tagID;
        this.context = context;
    }
    public static ArrayList<itemDAO> getItemDAOArrayListFromServer(Context context) {
        // Get connection and set params
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("tags_code", "T1510111");

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
        client.get(Constant.getQueryTagsTrade(),requestParams,new receiveTagInformaitnoHandler());
        return itemDAOArrayList;
    }

    /**
     *
     * For receive tag information from Server
     *
     */
    private static class receiveTagInformaitnoHandler extends JsonHttpResponseHandler {
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
            itemDAO itemDAOObject = new itemDAO();
            itemDAOArrayList.clear();
            itemDAOArrayList.add(itemDAOObject);
            itemDAOArrayList.add(itemDAOObject);
            itemDAOArrayList.add(itemDAOObject);
            itemDAOArrayList.add(itemDAOObject);
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
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

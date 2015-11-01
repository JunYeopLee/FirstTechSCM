package com.example.junyeop_imaciislab.firsttechscm.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.CheckInventoryActivity;
import com.example.junyeop_imaciislab.firsttechscm.InventoryDetailActivity;
import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.adapter.ItemDAOListViewAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by LeeJunYeop on 2015-11-02.
 */
public class receiveAllTradeHandler extends JsonHttpResponseHandler {
    private Context context;
    private ArrayList<itemDAO> itemDAOArrayList;

    public receiveAllTradeHandler(Context context) {
        this.context = context;
    }

    public ArrayList<itemDAO> getItemDAOArrayList() {
        return itemDAOArrayList;
    }

    @Override
    public void onStart() {
    }
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        // called when response HTTP status is "200 OK"
        try {
            if(response.getBoolean("success")) {
                itemDAOArrayList = new ArrayList<>();
                JSONArray jsonArray = response.getJSONArray("result");
                for( int i  = 0 ; i < jsonArray.length() ; i++ ) { // get trade information
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    itemDAO itemDAOObject = convertJsonObjectToitemDAO(jsonObject);
                    itemDAOArrayList.add(itemDAOObject);
                }
                Collections.sort(itemDAOArrayList, new Comparator<itemDAO>() {
                    @Override
                    public int compare(itemDAO p1, itemDAO p2) {
                        return p1.getItemName().compareTo(p2.getItemName());
                    }
                });
                ((CheckInventoryActivity)context).setAllTradeList(itemDAOArrayList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject errorResponse) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setMessage("[태그정보 수신 실패]네트워크 연결이 불안정 합니다").show();
    }

    private itemDAO convertJsonObjectToitemDAO(JSONObject jsonObject) throws JSONException {
        itemDAO itemDAOObject = new itemDAO();
        itemDAOObject.setItemName(jsonObject.getString(Constant.getServerItemName()));
        itemDAOObject.setItemStatus(jsonObject.getString(Constant.getServerItemStatus()));
        itemDAOObject.setExpirydate(jsonObject.getString(Constant.getServerExpiryDate()));
        itemDAOObject.setAmount(jsonObject.getString(Constant.getServerAmount()));
        itemDAOObject.setPrice(jsonObject.getString(Constant.getServerPrice()));
        itemDAOObject.setTradeCode(jsonObject.getString("CODE"));
        itemDAOObject.setItemCode(jsonObject.getString(Constant.getServerItemCodeInTag()));
        itemDAOObject.setIsSelected(false);
        return itemDAOObject;
    }
}

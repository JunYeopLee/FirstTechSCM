package com.example.junyeop_imaciislab.firsttechscm.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;

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
 * Created by junyeop_imaciislab on 2015. 10. 25..
 */
public class receiveInventoryDetailHandler extends JsonHttpResponseHandler {
    private ProgressDialog dialog;
    private Context context;
    private ArrayList<itemDAO> inventoryDetailList = new ArrayList<>();
    private ListView inventoryDetailListView;

    public receiveInventoryDetailHandler(Context context) {
        this.context = context;
        inventoryDetailListView = (ListView)((Activity) context).findViewById(R.id.listview_detail_item);
    }
    @Override
    public void onStart() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("잠시만 기다려 주세요.");
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
            if(response.getBoolean("success")) {
                inventoryDetailList = new ArrayList<>();
                JSONArray jsonArray = response.getJSONObject("result").getJSONArray("data");
                for( int i  = 0 ; i < jsonArray.length() ; i++ ) { // get trade information
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    itemDAO itemDAOObject = convertJsonObjectToitemDAO(jsonObject);
                    inventoryDetailList.add(itemDAOObject);
                }
                Collections.sort(inventoryDetailList, new Comparator<itemDAO>() {
                    @Override
                    public int compare(itemDAO p1, itemDAO p2) {
                        return p1.getItemName().compareTo(p2.getItemName());
                    }
                });
                if (((Activity)context).getLocalClassName().compareTo("InventoryDetailActivity") == 0 ) {
                    ItemDAOListViewAdapter itemDAOListViewAdapter = new ItemDAOListViewAdapter(((Activity)context),inventoryDetailList);
                    inventoryDetailListView = (ListView)((Activity) context).findViewById(R.id.listview_tagitem);
                    inventoryDetailListView.setAdapter(itemDAOListViewAdapter);
                    inventoryDetailListView.invalidate();
                }
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

    public ArrayList<itemDAO> getInventoryDetailList() {
        return inventoryDetailList;
    }

    private itemDAO convertJsonObjectToitemDAO(JSONObject jsonObject) throws JSONException {
        itemDAO itemDAOObject = new itemDAO();
        itemDAOObject.setItemName(jsonObject.getString(Constant.getServerItemName()));
        itemDAOObject.setItemStatus(jsonObject.getString(Constant.getServerItemStatus()));
        itemDAOObject.setCategory(jsonObject.getString(Constant.getServerCategory()));
        itemDAOObject.setExpirydate(jsonObject.getString(Constant.getServerExpiryDate()));
        itemDAOObject.setStandard(jsonObject.getString(Constant.getServerStandard()));
        itemDAOObject.setUnit(jsonObject.getString(Constant.getServerUnit()));
        itemDAOObject.setAmount(jsonObject.getString(Constant.getServerAmount()));
        itemDAOObject.setPrice(jsonObject.getString(Constant.getServerPrice()));
        itemDAOObject.setLocation(jsonObject.getString(Constant.getServerLocation()));
        itemDAOObject.setCustomer(jsonObject.getString(Constant.getServerCustomer()));
        itemDAOObject.setTradeCode(jsonObject.getString(Constant.getServerTradeCode()));
        itemDAOObject.setItemCode(jsonObject.getString(Constant.getServerItemCodeInTag()));
        itemDAOObject.setTagID(jsonObject.getString(Constant.getServerTagID()));
        itemDAOObject.setIsSelected(false);
        return itemDAOObject;
    }
}

package com.example.junyeop_imaciislab.firsttechscm.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;

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
 * Created by junyeop_imaciislab on 2015. 10. 25..
 */
public class receiveInventoryDetailHandler extends JsonHttpResponseHandler {
    private ProgressDialog dialog;
    private Context context;
    private ArrayList<itemDAO> inventoryDetailList = new ArrayList<>();
    private ArrayList<itemDAO> allTradeList = new ArrayList<>();
    private ArrayList<String> tradeCodeList = new ArrayList<>();
    private checkInventoryDAO checkInventoryDAO;
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
                tradeCodeList = new ArrayList<>();
                allTradeList = ((InventoryDetailActivity)context).getAllTradeList();
                checkInventoryDAO = ((InventoryDetailActivity)context).getCheckInventoryDAOObject();

                JSONArray jsonArray = response.getJSONArray("result");
                for( int i  = 0 ; i < jsonArray.length() ; i++ ) { // get trade information
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(jsonObject.getString(Constant.getItemHistoryStatus()).compareTo("s")==0) {
                        tradeCodeList.add(jsonObject.getString(Constant.getItemHistoryTradeCode()));
                    }
                }
                String tradeCode;
                itemDAO itemDAOObject;
                for( int i = 0 ; i < tradeCodeList.size() ; i++ ) {
                    tradeCode = tradeCodeList.get(i);
                    for( int j = 0 ; j < allTradeList.size() ; j++ ) {
                        if(tradeCode.compareTo(allTradeList.get(j).getTradeCode())==0) {
                            itemDAOObject = new itemDAO(allTradeList.get(j));
                            itemDAOObject.setStandard(checkInventoryDAO.getStandard());
                            itemDAOObject.setUnit(checkInventoryDAO.getUnit());
                            itemDAOObject.setCategory(checkInventoryDAO.getCategory());
                            inventoryDetailList.add(itemDAOObject);
                            break;
                        }
                    }
                }

                Collections.sort(inventoryDetailList, new Comparator<itemDAO>() {
                    @Override
                    public int compare(itemDAO p1, itemDAO p2) {
                        return p1.getItemName().compareTo(p2.getItemName());
                    }
                });
                if (((Activity)context).getLocalClassName().compareTo("InventoryDetailActivity") == 0 ) {
                    ItemDAOListViewAdapter itemDAOListViewAdapter = new ItemDAOListViewAdapter(((Activity)context),inventoryDetailList);
                    inventoryDetailListView = (ListView)((Activity) context).findViewById(R.id.listview_detail_item);
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

}

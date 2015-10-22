package com.example.junyeop_imaciislab.firsttechscm.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;
import android.widget.Toast;

import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.adapter.CheckInventoryListViewAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by LeeJunYeop on 2015-10-23.
 */
public class receiveSearchedItemHandler extends JsonHttpResponseHandler {
    private ArrayList<checkInventoryDAO> checkInventoryDAOArrayList;
    private ProgressDialog dialog;
    private Context context;
    private ListView searchedItemListView;

    public receiveSearchedItemHandler(Context context) {
        this.context = context;
        checkInventoryDAOArrayList = new ArrayList<>();
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
        // called when response HTTP status is "200 OK"

        try {
            if(response.getBoolean("success")) {
                JSONArray jsonArray = response.getJSONArray("result");
                for( int i  = 0 ; i < jsonArray.length() ; i++ ) { // get trade information
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    checkInventoryDAO checkInventoryDAOObject = convertJsonObjectToCheckInventoryDAO(jsonObject);
                    checkInventoryDAOArrayList.add(checkInventoryDAOObject);
                }
                CheckInventoryListViewAdapter checkInventoryListViewAdapter = new CheckInventoryListViewAdapter(((Activity)context),checkInventoryDAOArrayList, ((Activity) context).getIntent());
                searchedItemListView = (ListView)((Activity) context).findViewById(R.id.listview_inventory_item);
                searchedItemListView.setAdapter(checkInventoryListViewAdapter);
                searchedItemListView.invalidate();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        Toast.makeText(context, "검색 성공", Toast.LENGTH_SHORT).show();
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

    private checkInventoryDAO convertJsonObjectToCheckInventoryDAO(JSONObject jsonObject) throws JSONException {
        checkInventoryDAO checkInventoryDAO = new checkInventoryDAO();
        checkInventoryDAO.setItemCode(jsonObject.getString(Constant.getSearchedItemCode()));
        checkInventoryDAO.setItemName(jsonObject.getString(Constant.getSearchedItemName()));
        checkInventoryDAO.setCategory(jsonObject.getString(Constant.getSearchedItemCategory()));
        checkInventoryDAO.setAmount(jsonObject.getString(Constant.getSearchedItemAmount()));
        checkInventoryDAO.setUnit(jsonObject.getString(Constant.getSearchedItemUnit()));
        return checkInventoryDAO;
    }
}
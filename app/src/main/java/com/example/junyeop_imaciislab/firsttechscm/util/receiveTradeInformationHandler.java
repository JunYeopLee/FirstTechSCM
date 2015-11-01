package com.example.junyeop_imaciislab.firsttechscm.util;

/**
 * Created by LeeJunYeop on 2015-10-16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

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
 *
 * For receive tag information from Server
 *
 */
public class receiveTradeInformationHandler extends JsonHttpResponseHandler {
    private ProgressDialog dialog;
    private Context context;
    private ListView itemListView;
    private TextView modifiedTimeTextView;
    private String tagModifiedTime;
    private ArrayList<itemDAO> itemDAOArrayList;
    private CheckBox allCheckBox;

    public receiveTradeInformationHandler(Context context) {
        this.context = context;
        allCheckBox = (CheckBox)((Activity) context).findViewById(R.id.ckbox_allcheck);
        if (((Activity)context).getLocalClassName().compareTo("TagReadActivity") == 0 ||
                ((Activity)context).getLocalClassName().compareTo("TagWriteActivity") == 0 ) {
            allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for( int i = 0 ; i < itemDAOArrayList.size() ; i++ ) {
                        itemDAOArrayList.get(i).setIsSelected(isChecked);
                    }
                    ((ItemDAOListViewAdapter)itemListView.getAdapter()).notifyDataSetChanged();
                    itemListView.invalidate();
                }
            });
        }
    }

    public ArrayList<itemDAO> getItemDAOArrayList() {
        return itemDAOArrayList;
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
                itemDAOArrayList = new ArrayList<>();
                JSONArray jsonArray = response.getJSONObject("result").getJSONArray("data");
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

                if (((Activity)context).getLocalClassName().compareTo("TagReadActivity") == 0 ||
                        ((Activity)context).getLocalClassName().compareTo("TagWriteActivity") == 0 ) {
                    tagModifiedTime = response.getJSONObject("result").getString(Constant.getServerTagModifiedTime()); // get tag modified time
                    modifiedTimeTextView = (TextView)((Activity)context).findViewById(R.id.txt_modified_time);
                    modifiedTimeTextView.setText(tagModifiedTime);

                    ItemDAOListViewAdapter itemDAOListViewAdapter = new ItemDAOListViewAdapter(((Activity)context),itemDAOArrayList);
                    itemListView = (ListView)((Activity) context).findViewById(R.id.listview_tagitem);
                    itemListView.setAdapter(itemDAOListViewAdapter);
                    itemListView.invalidate();
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

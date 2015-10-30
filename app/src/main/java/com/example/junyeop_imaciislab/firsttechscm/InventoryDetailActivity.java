package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.checkInventoryDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.receiveInventoryDetailHandler;
import com.example.junyeop_imaciislab.firsttechscm.util.receiveTradeInformationHandler;
import com.example.junyeop_imaciislab.firsttechscm.util.sendCreateTagsTradeHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;

public class InventoryDetailActivity extends AppCompatActivity {
    public static final String TAG = "InventoryDetailActivity";
    private ListView detailItemListView;
    private String activityFrom;
    private String NFCtagID;
    private Context context;
    private checkInventoryDAO checkInventoryDAOObject;
    private receiveInventoryDetailHandler searchedItemDAOArrayListHandler;
    private receiveTradeInformationHandler prevItemDAOArrayListHandler;

    private ArrayList<itemDAO> searchedItemDAOArrayList = new ArrayList<>();
    private ArrayList<itemDAO> prevItemDAOArrayList = new ArrayList<>();

    @InjectView(R.id.txt_itemname)
    public TextView itemNameTextView;
    @InjectView(R.id.txt_category)
    public TextView categoryTextView;
    @InjectView(R.id.txt_standard)
    public TextView standardTextView;
    @InjectView(R.id.txt_unit)
    public TextView unitTextView;
    @InjectView(R.id.txt_amount)
    public TextView amountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        ButterKnife.inject(this);
        context = this;
        Intent intent = getIntent();
        if(!intent.hasExtra("activityFrom")) {
            // ERROR SHOOTING
        } else {
            activityFrom = intent.getExtras().getString("activityFrom");
        }
        NFCtagID = "";
        if(intent.hasExtra("NFCtagID"))
            NFCtagID = intent.getExtras().getString("NFCtagID");
        checkInventoryDAOObject = (checkInventoryDAO)intent.getSerializableExtra("checkInventoryDAO");
        itemNameTextView.setText(checkInventoryDAOObject.getItemName());
        categoryTextView.setText(checkInventoryDAOObject.getCategory());
        standardTextView.setText(checkInventoryDAOObject.getStandard());
        unitTextView.setText(checkInventoryDAOObject.getUnit());
        amountTextView.setText(checkInventoryDAOObject.getAmount());
    }
    @Override
    protected void onResume() {
        super.onResume();
        invisibleSomeComponent();
        if(NFCtagID!=null && NFCtagID.compareTo("")!=0)
            getprevItemDAOArrayList();
        drawListView();

    }
    private void invisibleSomeComponent() {  // set invisible some component when activity came from main activity directly.
        if(activityFrom.compareTo("")==0) {
            Log.d(TAG,"invisibleSomeComponent ERROR");
            return;
        } else if(activityFrom.compareTo("TagWriteActivity")==0) {
            return;
        } else { // FROM MainActivity for check inventory view
            findViewById(R.id.layout_forconfirm).setVisibility(View.GONE);
            findViewById(R.id.layout_forlistview_detail).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8.0f));
        }
    }

    public void drawListView() { // GET searched item dao list that is matched to item code, and draw list
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("item_code", checkInventoryDAOObject.getItemCode());
        getCookieFromStore(client);
        searchedItemDAOArrayListHandler = new receiveInventoryDetailHandler(this);
        client.get(Constant.getQueryTagsTrade(), requestParams, searchedItemDAOArrayListHandler);
    }

    public void getprevItemDAOArrayList() { // GET prev item DAO list that is already bind to tag
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("hf_tags", NFCtagID);
        getCookieFromStore(client);
        prevItemDAOArrayListHandler = new receiveTradeInformationHandler(this);
        client.get(Constant.getQueryTagsTrade(), requestParams, prevItemDAOArrayListHandler);
    }

    @OnClick(R.id.btn_confirm)
    public void onClickConfirmButton() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final AlertDialog dialog = null;
        ab.setMessage("선택 하신 상품들을 박스 추가 하시겠습니까?");
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();

                searchedItemDAOArrayList = searchedItemDAOArrayListHandler.getInventoryDetailList();
                prevItemDAOArrayList = prevItemDAOArrayListHandler.getItemDAOArrayList();

                String tradeCode = "",tagID;
                itemDAO itemDAOObject;
                for (int i = 0; i < prevItemDAOArrayList.size(); i++) {
                    itemDAOObject = prevItemDAOArrayList.get(i);
                    tradeCode += itemDAOObject.getTradeCode();
                    tradeCode += ", ";
                    tagID = itemDAOObject.getTagID();
                }
                if(prevItemDAOArrayList.size()!=0) tradeCode = tradeCode.substring(0, tradeCode.lastIndexOf(","));

                boolean added = false;
                for (int i = 0; i < searchedItemDAOArrayList.size(); i++) {
                    itemDAOObject = searchedItemDAOArrayList.get(i);
                    if(itemDAOObject.getIsSelected()) {
                        tradeCode += itemDAOObject.getTradeCode();
                        tradeCode += ", ";
                        added = true;
                    }
                }
                if(added) tradeCode = tradeCode.substring(0, tradeCode.lastIndexOf(","));
                else return;

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams requestParams = new RequestParams();
                requestParams.add("tags_code", NFCtagID);
                requestParams.add("trade_code",tradeCode);
                getCookieFromStore(client);
                client.post(Constant.getQueryTagsTrade(), requestParams, new sendCreateTagsTradeHandler(context));
            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        });
        ab.show();
    }

    private void getCookieFromStore(AsyncHttpClient client) {
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
    }
}
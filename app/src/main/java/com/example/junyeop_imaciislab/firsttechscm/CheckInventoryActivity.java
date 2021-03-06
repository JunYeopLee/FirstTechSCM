package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.checkInventoryDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.receiveAllTradeHandler;
import com.example.junyeop_imaciislab.firsttechscm.util.receiveSearchedItemHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;

public class CheckInventoryActivity extends AppCompatActivity {
    private ArrayList<checkInventoryDAO> checkInventoryDAOArrayList;
    private String NFCtagID;
    private Context context;
    private ArrayList<itemDAO> allTradeList = new ArrayList<>();

    @InjectView(R.id.edit_search_inventory)
    public EditText searchEditText;
    @InjectView(R.id.listview_inventory_item)
    public ListView checkInventoryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_inventory);
        ButterKnife.inject(this);
        context = this;
        getAllTradeFromServer();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    // FAKE DISPLAY
    @OnClick(R.id.btn_search_inventory)
    public void onClickSearch() {
        String searchText = searchEditText.getText().toString();
        if(searchText.compareTo("")==0) {
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            final AlertDialog dialog = null;
            ab.setMessage("검색어를 입력하세요");
            ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                }
            }).show();
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("item_name", searchEditText.getText().toString());
        receiveSearchedItemHandler receiveSearchedItemHandler = new receiveSearchedItemHandler(this);
        getCookieFromStore(client);
        client.get(Constant.getQuerySearchItem(), requestParams, receiveSearchedItemHandler);
        findViewById(R.id.listview_inventory_item).setVisibility(View.VISIBLE);
    }

    private void getAllTradeFromServer() {
        AsyncHttpClient client = new AsyncHttpClient();
        getCookieFromStore(client);
        String query = Constant.getServerURL()+"trade";
        client.get(query, new receiveAllTradeHandler(context));
    }

    public ArrayList<itemDAO> getAllTradeList() {
        return allTradeList;
    }

    public void setAllTradeList(ArrayList<itemDAO> allTradeList) {
        this.allTradeList = allTradeList;
    }

    private void getCookieFromStore(AsyncHttpClient client) {
        // Get Cookie from store(JSESSIONID)
        CookieStore cookieStore = new PersistentCookieStore(getApplicationContext());
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
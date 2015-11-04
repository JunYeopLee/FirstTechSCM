package com.example.junyeop_imaciislab.firsttechscm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.ItemHistoryListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.itemHistoryDAO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;

public class ItemHistoryActivity extends Activity {
    private String tradeCode;
    private ArrayList<itemHistoryDAO> itemHistoryDAOArrayList;
    private ListView itemHistoryListView;
    private ItemHistoryListViewAdapter itemHistoryListViewAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item_history);
        ButterKnife.inject(this);
        itemHistoryListView = (ListView)findViewById(R.id.listview_itemhistory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tradeCode = getTradeCode();
        //tradeCode = "I1510141"; // For Test
        context = this;
        getTradeHistoryDAOListFromServer(tradeCode);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @OnClick(R.id.layout_itemhistory_bg)
    public void onclickBackground() {
        this.onBackPressed();
    }

    private String getTradeCode() {
        if(!getIntent().hasExtra("tradeCode")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ItemHistoryActivity.this);
            alert.setMessage("잘못된 접근 입니다").show();
            finish();
            return null;
        }
        return getIntent().getExtras().getString("tradeCode");
    }


    private void getTradeHistoryDAOListFromServer(String tradeCode) {
        // Get connection and set params
        AsyncHttpClient client = new AsyncHttpClient();

        // Set Cookie from store(JSESSIONID)
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
        // Execute query for tag information and getItemDAOArrayList from server
        String query = Constant.getQueryGetTradeHistory().replace(Constant.getQueryGetTradeHistoryParameter(), tradeCode);
        client.get(query,new receiveItemHistoryHandler());
    }


    /**
     *
     * For receive item history information from Server
     *
     */
    private class receiveItemHistoryHandler extends JsonHttpResponseHandler {
        @Override
        public void onStart() {
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // called when response HTTP status is "200 OK"
            try {
                if(response.getBoolean("success")) {
                    JSONArray jsonArray = response.getJSONObject("result").getJSONArray("history");
                    itemHistoryDAOArrayList = new ArrayList<>();
                    for( int i  = 0 ; i < jsonArray.length() ; i++ ) { // get trade information
                        itemHistoryDAO itemHistoryDAOObject = new itemHistoryDAO();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //itemHistoryDAOObject.setItemCode(jsonObject.getString(Constant.getServerItemCode()));
                        itemHistoryDAOObject.setCompanyName(jsonObject.getString(Constant.getServerCompanyName()));
                        itemHistoryDAOObject.setCreatedDate(jsonObject.getString(Constant.getServerCreatedDate()));
                        itemHistoryDAOObject.setItemStatus(jsonObject.getString(Constant.getServerStatusHistory()));
                        itemHistoryDAOArrayList.add(itemHistoryDAOObject);
                    }
                    itemHistoryListViewAdapter = new ItemHistoryListViewAdapter((Activity)context,itemHistoryDAOArrayList);
                    itemHistoryListView.setAdapter(itemHistoryListViewAdapter);
                    itemHistoryListView.invalidate();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject errorResponse) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
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
package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.receiveTradeInformationHandler;
import com.example.junyeop_imaciislab.firsttechscm.util.sendTradeStatusUpdateHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;

public class TagReadActivity extends AppCompatActivity {
    private String NFCtagID;
    private Handler autoRefresher;
    private Context context;
    private receiveTradeInformationHandler receiveTradeInformationHandlerObject;
    private ArrayList<itemDAO> itemDAOArrayList;

    @InjectView(R.id.ckbox_allcheck)
    public CheckBox allCheckBox;
    @InjectView(R.id.listview_tagitem)
    public  ListView itemListView;
    @InjectView(R.id.txt_tagid)
    public TextView NFCtagTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_read);
        ButterKnife.inject(this);
        context = this;
        NFCtagID = getNFCtagID();
        //NFCtagID = "04b8d1496b0280"; // For Test
    }

    @Override
    protected void onResume() {
        super.onResume();
        NFCtagTextView.setText(NFCtagID);
        autoRefresher = new Handler();
        autoRefresher.postDelayed(runnableAutoRefresh, 300000);
        drawListView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoRefresher.removeCallbacks(runnableAutoRefresh);
    }

    private String getNFCtagID() {
        if(!getIntent().hasExtra("NFCtagID")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(TagReadActivity.this);
            alert.setMessage("잘못된 접근 입니다").show();
            finish();
            return null;
        }
        return getIntent().getExtras().getString("NFCtagID");
    }

    public void drawListView() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("hf_tag", NFCtagID);
        getCookieFromStore(client);
        receiveTradeInformationHandlerObject = new receiveTradeInformationHandler(this);
        // Execute query for tag information and getItemDAOArrayList from server
        client.get(Constant.getQueryTagsTrade(), requestParams, receiveTradeInformationHandlerObject);
    }

    @OnClick(R.id.btn_refresh)
    public void refreshView() {
        drawListView();
    }

    @OnClick(R.id.btn_get_stock)
    public void onClickGetStock() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final AlertDialog dialog = null;
        ab.setMessage("선택 하신 상품들을 입고 하시겠습니까?");
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                itemDAOArrayList = receiveTradeInformationHandlerObject.getItemDAOArrayList();
                for (int i = 0; i < itemDAOArrayList.size(); i++) {
                    if (itemDAOArrayList.get(i).getIsSelected() &&
                            itemDAOArrayList.get(i).getItemStatus().compareTo(Constant.getStatusUnregistered()) != 0) {
                        new AlertDialog.Builder(context).setMessage("미등록 상품만 입고가 가능합니다").setCancelable(true).show();
                        return;
                    }
                }
                for (int i = 0; i < itemDAOArrayList.size(); i++) {
                    if (itemDAOArrayList.get(i).getIsSelected()) {
                        sendTradeStatusUpdateQuery(itemDAOArrayList.get(i).getTradeCode(),
                                Constant.getOpStock());
                    }
                }
                drawListView();
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

    @OnClick(R.id.btn_get_release)
    public void onClickGetRelease() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final AlertDialog dialog = null;
        ab.setMessage("선택 하신 상품들을 출고 하시겠습니까?");
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                itemDAOArrayList = receiveTradeInformationHandlerObject.getItemDAOArrayList();
                for( int i = 0 ; i < itemDAOArrayList.size() ; i++ ) {
                    if(itemDAOArrayList.get(i).getIsSelected() &&
                            itemDAOArrayList.get(i).getItemStatus().compareTo(Constant.getStatusStocked())!=0){
                        new AlertDialog.Builder(context).setMessage("입고된 상품만 출고가 가능합니다").setCancelable(true).show();
                        return;
                    }
                }
                for( int i = 0 ; i < itemDAOArrayList.size() ; i++ ) {
                    if(itemDAOArrayList.get(i).getIsSelected() ){
                        sendTradeStatusUpdateQuery(itemDAOArrayList.get(i).getTradeCode(),
                                Constant.getOpRelease());
                    }
                }
                drawListView();
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

    private void sendTradeStatusUpdateQuery(String tradeCode,String status) {
        AsyncHttpClient client = new AsyncHttpClient();
        String Query = Constant.getQueryTradeStatusUpdate().replace(Constant.getQueryTradeStatusUpdateParameter(), tradeCode);
        RequestParams params = new RequestParams();
        params.add("type", status);
        getCookieFromStore(client);
        Log.d("TagReadActivity", "Query : " + Query + " tradeCode : " + tradeCode + " status : " + status);
        client.post(Query, params, new sendTradeStatusUpdateHandler(context));
    }

    private void getCookieFromStore(AsyncHttpClient client) {
        // Get Cookie from store(JSESSIONID)
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

    private final Runnable runnableAutoRefresh = new Runnable()
    {
        public void run()
        {
            drawListView();
            TagReadActivity.this.autoRefresher.postDelayed(runnableAutoRefresh, 300000);
        }
    };
}

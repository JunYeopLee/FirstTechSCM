package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.receiveTradeInformationHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;

public class TagWriteActivity extends AppCompatActivity {
    private String NFCtagID;
    private Context context;
    private Handler autoRefresher;

    @InjectView(R.id.ckbox_allcheck)
    public CheckBox allCheckBox;
    @InjectView(R.id.listview_tagitem)
    public  ListView itemListView;
    @InjectView(R.id.txt_tagid)
    public TextView NFCtagTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_write);
        ButterKnife.inject(this);
        context = this;
        NFCtagID = getNFCtagID();
        //NFCtagID = "T1510141"; // For Test
    }
    @Override
    protected void onResume() {
        super.onResume();
        NFCtagTextView.setText(NFCtagID);
        autoRefresher = new Handler();
        autoRefresher.postDelayed(runnableAutoRefresh, 30000);
        drawListView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoRefresher.removeCallbacks(runnableAutoRefresh);
    }

    private String getNFCtagID() {
        if(!getIntent().hasExtra("NFCtagID")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(TagWriteActivity.this);
            alert.setMessage("잘못된 접근 입니다").show();
            finish();
            return null;
        }
        return getIntent().getExtras().getString("NFCtagID");
    }

    private void drawListView() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("hf_tag", NFCtagID);
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
        client.get(Constant.getQueryTagsTrade(), requestParams, new receiveTradeInformationHandler(this));
    }

    @OnClick(R.id.btn_refresh)
    public void refreshView() {
        drawListView();
    }

    @OnClick(R.id.btn_add_item)
    public void callCheckInventory() {
        Intent intent = new Intent(TagWriteActivity.this, CheckInventoryActivity.class);
        intent.putExtra("activityFrom", "TagWriteActivity");
        startActivity(intent);
    }

    @OnClick(R.id.btn_del_selected)
    public void onClickDelSelected() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final AlertDialog dialog = null;
        ab.setMessage("선택 하신 상품들을 삭제 하시겠습니까?");
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
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

    private final Runnable runnableAutoRefresh = new Runnable()
    {
        public void run()
        {
            drawListView();
            TagWriteActivity.this.autoRefresher.postDelayed(runnableAutoRefresh, 10000);
        }
    };
}

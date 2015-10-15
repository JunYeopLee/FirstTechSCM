package com.example.junyeop_imaciislab.firsttechscm;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.sendTradeStatusUpdateHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;

/**
 * Created by junyeop_imaciislab on 2015. 10. 14..
 */
public class StatusSelectDialog extends Dialog implements OnClickListener {
    private String status;
    private Context context;
    private String tradeCode;

    public StatusSelectDialog(Context context, String status, String tradeCode) {
        super(context);
        this.context = context;
        this.tradeCode = tradeCode;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_status_select);
        this.status = status;
        if(Constant.getStatusUnregistered().compareTo(status)==0) {
            findViewById(R.id.btn_dialog_stock).setVisibility(View.VISIBLE);
        } else if(Constant.getStatusStocked().compareTo(status)==0) {
            findViewById(R.id.btn_dialog_release).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_dialog_return).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_dialog_discard).setVisibility(View.VISIBLE);
        } else if(Constant.getStatusReleased().compareTo(status)==0) {
            findViewById(R.id.btn_dialog_release_cancel).setVisibility(View.VISIBLE);
        } else if(Constant.getStatusReturned().compareTo(status)==0) {
            findViewById(R.id.btn_dialog_return_cancel).setVisibility(View.VISIBLE);
        } else if(Constant.getStatusDiscard().compareTo(status)==0) {
            findViewById(R.id.btn_dialog_discard_cancel).setVisibility(View.VISIBLE);
        }
    }
    public void onClick(View view) {
        dismiss();
    }

    private void sendTradeStatusUpdateQuery(String tradeCode,String status) {
        AsyncHttpClient client = new AsyncHttpClient();
        String Query = Constant.getQueryTradeStatusUpdate().replace(Constant.getQueryTradeStatusUpdateParameter(), tradeCode);
        RequestParams params = new RequestParams();
        params.add("type", status);
        getCookieFromStore(client);
        client.post(Query,params,new sendTradeStatusUpdateHandler(context));
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
}

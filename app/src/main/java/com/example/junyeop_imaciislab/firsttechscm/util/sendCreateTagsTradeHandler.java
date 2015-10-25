package com.example.junyeop_imaciislab.firsttechscm.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.junyeop_imaciislab.firsttechscm.TagWriteActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by junyeop_imaciislab on 2015. 10. 25..
 */
public class sendCreateTagsTradeHandler extends JsonHttpResponseHandler {
    private ProgressDialog dialog;
    private Context context;

    public sendCreateTagsTradeHandler(Context context) {
        this.context = context;
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
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        Toast.makeText(context, "작업 성공", Toast.LENGTH_SHORT).show();
        if (((Activity)context).getLocalClassName().compareTo("InventoryDetailActivity") == 0) {
            ((TagWriteActivity) context).drawListView();
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
}

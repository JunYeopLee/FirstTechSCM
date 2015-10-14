package com.example.junyeop_imaciislab.firsttechscm;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.example.junyeop_imaciislab.firsttechscm.util.Constant;

/**
 * Created by junyeop_imaciislab on 2015. 10. 14..
 */
public class StatusSelectDialog extends Dialog implements OnClickListener {
    private String status;
    public StatusSelectDialog(Context context, String status) {
        super(context);
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
}

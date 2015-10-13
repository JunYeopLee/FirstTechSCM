package com.example.junyeop_imaciislab.firsttechscm;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ItemHistoryActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item_history);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}
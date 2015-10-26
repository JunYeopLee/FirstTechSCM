package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private SharedPreferences LoginSharedPreferences;
    private SharedPreferences.Editor LoginSharedPreferencesEditor;

    private ImageButton TagReadButton;
    private ImageButton TagWriteButton;
    private ImageButton TagHistoryButton;
    private ImageButton CheckInventoryButton;
    private ImageButton LogOutButton;
    private SQLiteDatabase tagHistoryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginSharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        Constant.setUserName(LoginSharedPreferences.getString("username", ""));
        Constant.setSqluserTableName(Constant.getUserName()+Constant.getSqlDefaultTableName());

        TagReadButton = (ImageButton)findViewById(R.id.btn_tag_read);
        TagWriteButton = (ImageButton)findViewById(R.id.btn_tag_write);
        TagHistoryButton = (ImageButton)findViewById(R.id.btn_tag_history);
        CheckInventoryButton = (ImageButton)findViewById(R.id.btn_check_inventory);
        LogOutButton = (ImageButton)findViewById(R.id.btn_log_out);

        TagReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, NFCTaggingActivity.class);
                Intent intent = new Intent(MainActivity.this, TagReadActivity.class); // For Test
                intent.putExtra("activityToGo","read");
                startActivity(intent);
            }
        });

        TagWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, NFCTaggingActivity.class);
                Intent intent = new Intent(MainActivity.this, TagWriteActivity.class); // For Test
                intent.putExtra("activityToGo","write");
                startActivity(intent);
            }
        });

        TagHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TagHistoryActivity.class);
                startActivity(intent);
            }
        });

        CheckInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckInventoryActivity.class);
                intent.putExtra("activityFrom","MainActivity");
                startActivity(intent);
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(Constant.getQueryLogout(), new LogoutAsyncHttpResponseHandler());
            }
        });
        tagHistoryDB = openOrCreateDatabase(Constant.getSqlTagHistoryDBName(), Context.MODE_PRIVATE, null);
        tagHistoryDB.execSQL(Constant.getSqlCreateTable().replace(Constant.getSqlDefaultTableName(),Constant.getSqluserTableName()));
    }

    /**
     *
     * For Login AsyncTask
     *
     * */
    private class LogoutAsyncHttpResponseHandler extends JsonHttpResponseHandler {
        ProgressDialog dialog;
        @Override
        public void onStart() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("잠시만 기다려 주세요.");
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                if(response.getBoolean("success")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    finish();
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                    LoginSharedPreferencesEditor = LoginSharedPreferences.edit();
                    LoginSharedPreferencesEditor.remove("username");
                    LoginSharedPreferencesEditor.remove("password");
                    LoginSharedPreferencesEditor.apply();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage("[로그아웃 실패]알 수 없는 에러").show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject errorResponse) {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setMessage("[로그아웃 실패]네트워크 연결이 불안정 합니다").show();
        }
    }
}

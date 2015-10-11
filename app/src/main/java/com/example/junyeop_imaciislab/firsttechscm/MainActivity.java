package com.example.junyeop_imaciislab.firsttechscm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    private SharedPreferences LoginSharedPreferences;
    private SharedPreferences.Editor LoginSharedPreferencesEditor;

    private TextView UserIdtxt;
    private Button TagReadButton;
    private Button TagWriteButton;
    private Button TagHistoryButton;
    private Button CheckInventoryButton;
    private Button LogOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginSharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        UserIdtxt = (TextView)findViewById(R.id.txt_userid);
        UserIdtxt.setText("USER ID : " + LoginSharedPreferences.getString("username",""));

        TagReadButton = (Button)findViewById(R.id.btn_tag_read);
        TagWriteButton = (Button)findViewById(R.id.btn_tag_write);
        TagHistoryButton = (Button)findViewById(R.id.btn_tag_history);
        CheckInventoryButton = (Button)findViewById(R.id.btn_check_inventory);
        LogOutButton = (Button)findViewById(R.id.btn_log_out);

        TagReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.junyeop_imaciislab.firsttechscm.NFCTaggingActivity.class);
                intent.putExtra("activityToGo","read");
                startActivity(intent);
            }
        });

        TagWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.junyeop_imaciislab.firsttechscm.NFCTaggingActivity.class);
                intent.putExtra("activityToGo","write");
                startActivity(intent);
            }
        });

        TagHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.junyeop_imaciislab.firsttechscm.TagHistoryActivity.class);
                startActivity(intent);
            }
        });

        CheckInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckInventoryActivity.class);
                startActivity(intent);
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogoutTask().execute(getString(R.string.query_logout));
            }
        });
    }

    /**
     *
     * For Login AsyncTask
     *
     * */
    private class LogoutTask extends AsyncTask<String, Void, HttpResponse> {
        private Handler mHandler;
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mHandler = new Handler();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("잠시만 기다려 주세요.");
            dialog.setCancelable(false);
            dialog.show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            },5000);
        }

        @Override
        protected HttpResponse doInBackground(String... urls) {
            HttpResponse response = null;
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
            HttpPost httpPost = new HttpPost(urls[0]);

            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            try {
                response = client.execute(httpPost);
            } catch(IOException e) {
                e.printStackTrace();
                alert.setMessage("[로그아웃 실패]네트워크 연결이 불안정 합니다");
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert.show();
                    }
                });
            }

            try{
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    String responseString = out.toString();
                    out.close();
                    JSONTokener tokener = new JSONTokener(responseString);
                    JSONObject finalResult = (JSONObject)tokener.nextValue();
                    if(finalResult.getBoolean("success")==true) {
                        LoginSharedPreferencesEditor = LoginSharedPreferences.edit();
                        LoginSharedPreferencesEditor.remove("username");
                        LoginSharedPreferencesEditor.remove("password");
                        LoginSharedPreferencesEditor.commit();
                        Intent intent = new Intent(MainActivity.this, com.example.junyeop_imaciislab.firsttechscm.LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        finish();
                    } else {
                        alert.setMessage("[로그아웃 실패]네트워크 연결이 불안정 합니다");
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                            }
                        });
                    }
                } else{
                    alert.setMessage("[로그아웃 실패]네트워크 연결이 불안정 합니다");
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.show();
                        }
                    });
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return response;
        }
        @Override
        protected void onPostExecute(HttpResponse response) {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}

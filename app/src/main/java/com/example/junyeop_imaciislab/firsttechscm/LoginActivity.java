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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LoginActivity extends Activity {
    public static final String TAG = "LoginActivity";
    private SharedPreferences LoginSharedPreferences;
    private SharedPreferences.Editor LoginSharedPreferencesEditor;
    private Button LoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        LoginSharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final String username = LoginSharedPreferences.getString("username", null);
        final String password = LoginSharedPreferences.getString("password", null);
        if (!"".equalsIgnoreCase(username) && username != null && password != null ) { // Auto login
            String query;
            query = getString(R.string.query_login);
            new LoginTask().execute(query,username,password);
        } else {
            LoginButton = (Button)findViewById(R.id.btn_log_in);
            LoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String query;
                    String id = ((EditText)findViewById(R.id.edit_id)).getText().toString();
                    String pw = ((EditText)findViewById(R.id.edit_pw)).getText().toString();
                    query = getString(R.string.query_login);
                    new LoginTask().execute(query,id,pw);
                }
            });
        }
    }

    /**
     *
     * For Login AsyncTask
     *
     * */
    private class LoginTask extends AsyncTask<String, Void, HttpResponse> {
        private Handler mHandler;
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mHandler = new Handler();
            dialog = new ProgressDialog(LoginActivity.this);
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

            final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            try {
                ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", urls[1]));
                nameValuePairs.add(new BasicNameValuePair("password", urls[2]));
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                httpPost.setEntity(urlEncodedFormEntity);
                response = client.execute(httpPost);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch(ClientProtocolException e){
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
                alert.setMessage("[로그인 실패]네트워크 연결이 불안정 합니다");
                LoginActivity.this.runOnUiThread(new Runnable() {
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

                        LoginSharedPreferencesEditor.putString("username", urls[1]);
                        LoginSharedPreferencesEditor.putString("password", urls[2]);
                        LoginSharedPreferencesEditor.commit();

                        Intent intent = new Intent(LoginActivity.this, com.example.junyeop_imaciislab.firsttechscm.MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        finish();
                    } else {
                        alert.setMessage("아이디 혹은 비밀번호가 틀렸습니다");
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                            }
                        });
                        Log.d(TAG, finalResult.getString("errorMessage"));
                    }

                } else{
                    //Closes the connection.
                    alert.setMessage("[로그인 실패]네트워크 연결이 불안정 합니다");
                    LoginActivity.this.runOnUiThread(new Runnable() {
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



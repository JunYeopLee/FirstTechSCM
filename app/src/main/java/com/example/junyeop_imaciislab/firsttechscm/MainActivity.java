package com.example.junyeop_imaciislab.firsttechscm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";
    private Button TagReadButton;
    private Button TagWriteButton;
    private Button TagHistoryButton;
    private Button CheckInventoryButton;
    private Button LogOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TagReadButton = (Button)findViewById(R.id.btn_tag_read);
        TagWriteButton = (Button)findViewById(R.id.btn_tag_write);
        TagHistoryButton = (Button)findViewById(R.id.btn_tag_history);
        CheckInventoryButton = (Button)findViewById(R.id.btn_check_inventory);
        LogOutButton = (Button)findViewById(R.id.btn_log_out);

        TagReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NFCTaggingActivity.class);
                startActivity(intent);
            }
        });

        TagWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NFCTaggingActivity.class);
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
                startActivity(intent);
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

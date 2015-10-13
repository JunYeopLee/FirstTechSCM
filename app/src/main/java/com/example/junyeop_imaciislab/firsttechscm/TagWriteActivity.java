package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.ItemDAOListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAOListWrapper;

import java.util.ArrayList;

public class TagWriteActivity extends AppCompatActivity {
    private String NFCtagID;
    private TextView NFCtagTextView;
    private ListView itemListView;
    private Button addItemButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_write);
        //NFCtagID = getNFCtagID();
        NFCtagID = "T1510111"; // For Test

        addItemButton = (Button)findViewById(R.id.btn_add_item);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TagWriteActivity.this, CheckInventoryActivity.class);
                intent.putExtra("activityFrom","TagWriteActivity");
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_del_selected).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListView.invalidate();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        NFCtagTextView = (TextView)findViewById(R.id.txt_tagid);
        NFCtagTextView.setText(NFCtagID);
        itemListView = (ListView)findViewById(R.id.listview_tagwrite);
        itemDAOListWrapper wrapper = new itemDAOListWrapper(this,NFCtagID);
        ArrayList<itemDAO> itemDAOArrayList = wrapper.getItemDAOArrayListFromServer(getApplicationContext());
        waitForServer();
        ItemDAOListViewAdapter itemDAOListViewAdapter = new ItemDAOListViewAdapter(this,itemDAOArrayList);
        itemListView.setAdapter(itemDAOListViewAdapter);
        itemDAOListViewAdapter.notifyDataSetChanged();
        itemListView.invalidateViews();
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

    private void waitForServer() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

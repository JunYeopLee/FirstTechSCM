package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.TagTradeListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAOListWrapper;

import java.util.ArrayList;

public class TagWriteActivity extends AppCompatActivity {
    private String NFCtagID;
    private TextView NFCtagTextView;
    private ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_write);
        NFCtagID = getNFCtagID();
    }
    @Override
    protected void onResume() {
        super.onResume();
        NFCtagTextView = (TextView)findViewById(R.id.txt_tagid);
        NFCtagTextView.setText(NFCtagID);

        itemListView = (ListView)findViewById(R.id.listview_tagwrite);
        itemDAOListWrapper wrapper = new itemDAOListWrapper(NFCtagID);
        ArrayList<itemDAO> itemDAOArrayList = wrapper.getItemDAOArrayList();
        TagTradeListViewAdapter tagTradeListViewAdapter = new TagTradeListViewAdapter(this,itemDAOArrayList);
        itemListView.setAdapter(tagTradeListViewAdapter);
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
}

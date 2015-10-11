package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import com.example.junyeop_imaciislab.firsttechscm.adapter.TagTradeListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;

import java.util.ArrayList;

public class TagReadActivity extends AppCompatActivity {
    private String NFCtagID;
    private TextView NFCtagTest;
    private ListView itemListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_read);
        NFCtagID = getNFCtagID();
        itemListView = (ListView)findViewById(R.id.listview_tagread);

        ArrayList<itemDAO> itemDAOArrayList = new ArrayList<>();
        itemDAO itemDAOObject = new itemDAO();
        itemDAOArrayList.add(itemDAOObject);
        itemDAOArrayList.add(itemDAOObject);
        itemDAOArrayList.add(itemDAOObject);
        itemDAOArrayList.add(itemDAOObject);
        TagTradeListViewAdapter tagTradeListViewAdapter = new TagTradeListViewAdapter(this,itemDAOArrayList);
        itemListView.setAdapter(tagTradeListViewAdapter);
    }
    private String getNFCtagID() {
        if(!getIntent().hasExtra("NFCtagID")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(TagReadActivity.this);
            alert.setMessage("잘못된 접근 입니다").show();
            finish();
            return null;
        }
        return getIntent().getExtras().getString("NFCtagID");
    }
}

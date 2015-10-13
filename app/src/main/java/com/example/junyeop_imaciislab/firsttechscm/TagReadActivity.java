package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.ItemDAOListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAOListWrapper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagReadActivity extends AppCompatActivity {
    private String NFCtagID;
    private TextView NFCtagTextView;
    private ListView itemListView;
    private TextView modifiedTimeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_read);
        ButterKnife.inject(this);
        //NFCtagID = getNFCtagID();
        NFCtagID = "T1510111"; // For Test
    }
    @Override
    protected void onResume() {
        super.onResume();
        NFCtagTextView = (TextView)findViewById(R.id.txt_tagid);
        NFCtagTextView.setText(NFCtagID);
        drawListView();
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

    private void drawListView() {
        itemListView = (ListView)findViewById(R.id.listview_tagread);
        itemDAOListWrapper wrapper = new itemDAOListWrapper(this,NFCtagID);
        ArrayList<itemDAO> itemDAOArrayList = wrapper.getItemDAOArrayListFromServer(getApplicationContext());
        waitForServer();
        ItemDAOListViewAdapter itemDAOListViewAdapter = new ItemDAOListViewAdapter(this,itemDAOArrayList);
        itemListView.setAdapter(itemDAOListViewAdapter);
        itemDAOListViewAdapter.notifyDataSetChanged();
        itemListView.invalidateViews();

        modifiedTimeTextView = (TextView)findViewById(R.id.txt_modified_time);
        modifiedTimeTextView.setText(wrapper.getTagModifiedTime());
    }

    private void waitForServer() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_refresh)
    public void refreshView() {
        drawListView();
    }
}

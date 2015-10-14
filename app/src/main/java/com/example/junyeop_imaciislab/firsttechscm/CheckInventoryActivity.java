package com.example.junyeop_imaciislab.firsttechscm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.CheckInventoryListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.checkInventoryDAO;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckInventoryActivity extends AppCompatActivity {
    ListView checkInventoryListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_inventory);
        ButterKnife.inject(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkInventoryListView = (ListView)findViewById(R.id.listview_inventory_item);
        ArrayList<checkInventoryDAO> checkInventoryDAOArrayList = new ArrayList<>();
        checkInventoryDAO checkInventoryDAOObject = new checkInventoryDAO();
        checkInventoryDAOArrayList.add(checkInventoryDAOObject);
        checkInventoryDAOArrayList.add(checkInventoryDAOObject);
        checkInventoryDAOArrayList.add(checkInventoryDAOObject);
        checkInventoryDAOArrayList.add(checkInventoryDAOObject);
        checkInventoryDAOArrayList.add(checkInventoryDAOObject);
        checkInventoryDAOArrayList.add(checkInventoryDAOObject);
        checkInventoryDAOArrayList.add(checkInventoryDAOObject);
        CheckInventoryListViewAdapter checkInventoryListViewAdapter = new CheckInventoryListViewAdapter(this,checkInventoryDAOArrayList,getIntent());
        checkInventoryListView.setAdapter(checkInventoryListViewAdapter);

    }

    // FAKE DISPLAY
    @OnClick(R.id.btn_search_inventory)
    public void onClickSearch() {
        findViewById(R.id.listview_inventory_item).setVisibility(View.VISIBLE);
    }
}

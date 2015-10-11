package com.example.junyeop_imaciislab.firsttechscm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.CheckInventoryListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.checkInventoryDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.tagHistoryDAO;

import java.util.ArrayList;
import java.util.List;

public class CheckInventoryActivity extends AppCompatActivity {
    ListView checkInventoryListView;
    Button searchInventoryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_inventory);
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

        // FAKE DISPLAY
        searchInventoryButton = (Button)findViewById(R.id.btn_search_inventory);
        searchInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.listview_inventory_item).setVisibility(View.VISIBLE);
            }
        });
    }
}

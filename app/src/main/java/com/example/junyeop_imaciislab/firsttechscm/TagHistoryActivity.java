package com.example.junyeop_imaciislab.firsttechscm;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.adapter.TagHistoryListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.adapter.TagTradeListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.tagHistoryDAO;

import java.util.ArrayList;

public class TagHistoryActivity extends AppCompatActivity {
    private ListView tagHistoryListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_history);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tagHistoryListView = (ListView)findViewById(R.id.listview_taghistory);
        ArrayList<tagHistoryDAO> tagHistoryDAOArrayList = new ArrayList<>();
        tagHistoryDAO tagHistoryDAOObject = new tagHistoryDAO();
        tagHistoryDAOArrayList.add(tagHistoryDAOObject);
        tagHistoryDAOArrayList.add(tagHistoryDAOObject);
        tagHistoryDAOArrayList.add(tagHistoryDAOObject);
        tagHistoryDAOArrayList.add(tagHistoryDAOObject);
        TagHistoryListViewAdapter tagTradeListViewAdapter = new TagHistoryListViewAdapter(this,tagHistoryDAOArrayList);
        tagHistoryListView.setAdapter(tagTradeListViewAdapter);
    }
}

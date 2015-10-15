package com.example.junyeop_imaciislab.firsttechscm;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.TagHistoryListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.tagHistoryDAO;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagHistoryActivity extends AppCompatActivity {
    private ListView tagHistoryListView;
    private SQLiteDatabase tagHistoryDB;
    private ArrayList<tagHistoryDAO> tagHistoryDAOArrayList;
    private TagHistoryListViewAdapter tagTradeListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_history);
        ButterKnife.inject(this);
        tagHistoryDB = openOrCreateDatabase(Constant.getSqlTagHistoryDBName(), Context.MODE_PRIVATE,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tagHistoryListView = (ListView)findViewById(R.id.listview_taghistory);
        tagHistoryDAOArrayList = new ArrayList<>();
        selectAlltagHistory();
        tagTradeListViewAdapter = new TagHistoryListViewAdapter(this,tagHistoryDAOArrayList);
        tagHistoryListView.setAdapter(tagTradeListViewAdapter);
    }

    private void selectAlltagHistory(){
        String sql = Constant.getSqlSelectAll();
        Cursor results = tagHistoryDB.rawQuery(sql, null);
        results.moveToFirst();
        tagHistoryDAOArrayList.clear();
        while(!results.isAfterLast()){
            tagHistoryDAO tagHistoryDAOObject = new tagHistoryDAO();
            tagHistoryDAOObject.setKey(results.getInt(0));
            tagHistoryDAOObject.setTagID(results.getString(1));
            tagHistoryDAOObject.setCreatedTime(results.getString(2));
            tagHistoryDAOObject.setSummary(results.getString(3));
            tagHistoryDAOObject.setIsSelected(false);
            results.moveToNext();
            tagHistoryDAOArrayList.add(tagHistoryDAOObject);
        }
        results.close();
    }

    @OnClick(R.id.btn_history_delete)
    public void deleteSelected() {
        for( int i = 0 ; i < tagHistoryDAOArrayList.size() ; i++ ) {
            if(tagHistoryDAOArrayList.get(i).getIsSelected()) {
                int key = tagHistoryDAOArrayList.get(i).getKey();
                tagHistoryDB.execSQL("DELETE FROM " + Constant.getSqlTableName() + " WHERE key=" + String.valueOf(key) +";");
            }
        }
        selectAlltagHistory();
        tagTradeListViewAdapter.notifyDataSetChanged();
        tagHistoryListView.invalidate();
    }
}

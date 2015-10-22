package com.example.junyeop_imaciislab.firsttechscm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.ItemDAOListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.checkInventoryDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class InventoryDetailActivity extends AppCompatActivity {
    public static final String TAG = "InventoryDetailActivity";
    private ListView detailItemListView;
    private String activityFrom;
    private Button confirmButton;
    private checkInventoryDAO checkInventoryDAOObject;

    @InjectView(R.id.txt_itemname)
    public TextView itemNameTextView;
    @InjectView(R.id.txt_category)
    public TextView categoryTextView;
    @InjectView(R.id.txt_standard)
    public TextView standardTextView;
    @InjectView(R.id.txt_unit)
    public TextView unitTextView;
    @InjectView(R.id.txt_amount)
    public TextView amountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        if(!intent.hasExtra("activityFrom")) {
            // ERROR SHOOTING
        } else {
            activityFrom = intent.getExtras().getString("activityFrom");
        }
        checkInventoryDAOObject = (checkInventoryDAO)intent.getSerializableExtra("checkInventoryDAO");
        itemNameTextView.setText(checkInventoryDAOObject.getItemName());
        categoryTextView.setText(checkInventoryDAOObject.getCategory());
        standardTextView.setText(checkInventoryDAOObject.getStandard());
        unitTextView.setText(checkInventoryDAOObject.getUnit());
        amountTextView.setText(checkInventoryDAOObject.getAmount());
    }
    @Override
    protected void onResume() {
        super.onResume();
        invisibleSomeComponent();
        detailItemListView = (ListView)findViewById(R.id.listview_detail_item);
        ArrayList<itemDAO> itemDAOArrayList = new ArrayList<>();
        itemDAO itemDAOObject = new itemDAO();
        itemDAOObject.setItemStatus("n"); itemDAOObject.setExpirydate("11111111"); itemDAOObject.setIsSelected(false);
        itemDAOArrayList.add(itemDAOObject);
        itemDAOArrayList.add(itemDAOObject);
        itemDAOArrayList.add(itemDAOObject);
        itemDAOArrayList.add(itemDAOObject);
        itemDAOArrayList.add(itemDAOObject);
        ItemDAOListViewAdapter itemDAOListViewAdapter = new ItemDAOListViewAdapter(this,itemDAOArrayList,activityFrom);
        detailItemListView.setAdapter(itemDAOListViewAdapter);
    }
    private void invisibleSomeComponent() {  // set invisible some component when activity came from main activity directly.
        if(activityFrom.compareTo("")==0) {
            Log.d(TAG,"invisibleSomeComponent ERROR");
            return;
        } else if(activityFrom.compareTo("TagWriteActivity")==0) {
            return;
        } else { // FROM MainActivity for check inventory view
            findViewById(R.id.layout_forconfirm).setVisibility(View.GONE);
            findViewById(R.id.layout_forlistview_detail).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 8.0f));
        }
    }
}
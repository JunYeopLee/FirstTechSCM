package com.example.junyeop_imaciislab.firsttechscm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.junyeop_imaciislab.firsttechscm.InventoryDetailActivity;
import com.example.junyeop_imaciislab.firsttechscm.ItemHistoryActivity;
import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.util.checkInventoryDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.tagHistoryDAO;

import java.util.ArrayList;

/**
 * Created by LeeJunYeop on 2015-10-12.
 */
public class CheckInventoryListViewAdapter   extends ArrayAdapter<checkInventoryDAO> {
    private final Activity context;
    private ArrayList<checkInventoryDAO> checkInventoryDAOArrayList;
    private String activityFrom;
    public CheckInventoryListViewAdapter(Activity context, ArrayList<checkInventoryDAO> checkInventoryDAOArrayList, Intent intent) {
        super(context, R.layout.item_check_inventory_list, checkInventoryDAOArrayList);
        this.context = context;
        this.checkInventoryDAOArrayList = checkInventoryDAOArrayList;
        activityFrom = intent.getExtras().getString("activityFrom");
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_check_inventory_list, null, true);

        Button checkDetailButton = (Button)rowView.findViewById(R.id.btn_check_detail);
        checkDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InventoryDetailActivity.class);
                intent.putExtra("activityFrom",activityFrom);
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}

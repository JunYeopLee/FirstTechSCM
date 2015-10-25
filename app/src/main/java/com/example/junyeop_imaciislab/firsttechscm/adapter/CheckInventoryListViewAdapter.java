package com.example.junyeop_imaciislab.firsttechscm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.InventoryDetailActivity;
import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.util.checkInventoryDAO;

import java.util.ArrayList;

/**
 * Created by LeeJunYeop on 2015-10-12.
 */
public class CheckInventoryListViewAdapter   extends ArrayAdapter<checkInventoryDAO> {
    private final Activity context;
    private ArrayList<checkInventoryDAO> checkInventoryDAOArrayList;
    private String activityFrom;
    private String NFCtagID;
    public CheckInventoryListViewAdapter(Activity context, ArrayList<checkInventoryDAO> checkInventoryDAOArrayList, Intent intent) {
        super(context, R.layout.item_check_inventory_list, checkInventoryDAOArrayList);
        this.context = context;
        this.checkInventoryDAOArrayList = checkInventoryDAOArrayList;
        activityFrom = intent.getExtras().getString("activityFrom");
        NFCtagID = intent.getExtras().getString("NFCtagID");
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_check_inventory_list, null, true);

        final checkInventoryDAO checkInventoryDAOObject = checkInventoryDAOArrayList.get(position);
        TextView itemNameTextView = (TextView)rowView.findViewById(R.id.txt_itemname);
        TextView categoryTextView = (TextView)rowView.findViewById(R.id.txt_category);
        TextView standardTextView = (TextView)rowView.findViewById(R.id.txt_standard);
        TextView unitTextView = (TextView)rowView.findViewById(R.id.txt_unit);
        TextView amountTextView = (TextView)rowView.findViewById(R.id.txt_amount);

        itemNameTextView.setText(checkInventoryDAOObject.getItemName());
        categoryTextView.setText(checkInventoryDAOObject.getCategory());
        standardTextView.setText(checkInventoryDAOObject.getStandard());
        unitTextView.setText(checkInventoryDAOObject.getUnit());
        amountTextView.setText(checkInventoryDAOObject.getAmount());

        ImageButton checkDetailButton = (ImageButton)rowView.findViewById(R.id.btn_check_detail);
        checkDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InventoryDetailActivity.class);
                intent.putExtra("activityFrom",activityFrom);
                intent.putExtra("NFCtagID",NFCtagID);
                intent.putExtra("checkInventoryDAO",checkInventoryDAOObject);
                context.startActivity(intent);
            }
        });
        return rowView;
    }
}

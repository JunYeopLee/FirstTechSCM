package com.example.junyeop_imaciislab.firsttechscm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.ItemHistoryActivity;
import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;

import java.util.ArrayList;

/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class ItemDAOListViewAdapter extends ArrayAdapter<itemDAO> {
    private Activity context;
    private ArrayList<itemDAO> itemDAOArrayList;
    private String activityFrom ="";


    public ArrayList<itemDAO> getItemDAOArrayList() {
        return itemDAOArrayList;
    }

    public ItemDAOListViewAdapter(Activity context, ArrayList<itemDAO> itemDAOArrayList) {
        super(context, R.layout.item_itemdao_list, itemDAOArrayList);
        this.context = context;
        this.itemDAOArrayList = itemDAOArrayList;
    }

    public ItemDAOListViewAdapter(Activity context, ArrayList<itemDAO> itemDAOArrayList,String activityFrom) {
        super(context, R.layout.item_itemdao_list, itemDAOArrayList);
        this.context = context;
        this.itemDAOArrayList = itemDAOArrayList;
        this.activityFrom = activityFrom;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.item_itemdao_list, null, true);

        setInvisibleSomeComponent(rowView);

        TextView itemNameTextView = (TextView)rowView.findViewById(R.id.txt_itemname);
        Button itemStatusButton = (Button)rowView.findViewById(R.id.btn_item_status);
        TextView priceTextView = (TextView)rowView.findViewById(R.id.txt_price);
        TextView amountTextView = (TextView)rowView.findViewById(R.id.txt_amount);

        itemDAO itemDAOObject = itemDAOArrayList.get(position);
        itemNameTextView.setText(itemDAOObject.getItemName());
        //itemStatusButton.setText(itemDAOObject.getItemStatus());
        priceTextView.setText(itemDAOObject.getPrice());
        amountTextView.setText(itemDAOObject.getAmount());

        Button itemHistoryButton = (Button)rowView.findViewById(R.id.btn_item_history);
        itemHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemHistoryActivity.class);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

    private void setInvisibleSomeComponent(View view) { // set invisible some component when activity came from main activity directly.
        if(activityFrom.compareTo("")==0 || activityFrom.compareTo("TagWriteActivity")==0) {
            return;
        } else { // FROM MainActivity for check inventory view
            view.findViewById(R.id.ckbox_select_item).setVisibility(View.INVISIBLE);
        }
    }
}


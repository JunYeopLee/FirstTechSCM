package com.example.junyeop_imaciislab.firsttechscm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.itemHistoryDAO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by junyeop_imaciislab on 2015. 10. 15..
 */
public class ItemHistoryListViewAdapter extends ArrayAdapter<itemHistoryDAO> {
    private Activity context;
    private ArrayList<itemHistoryDAO> itemDAOHistoryArrayList;
    public ArrayList<itemHistoryDAO> getItemDAOArrayList() {
        return itemDAOHistoryArrayList;
    }

    public ItemHistoryListViewAdapter(Activity context, ArrayList<itemHistoryDAO> itemDAOHistoryArrayList) {
        super(context, R.layout.item_itemdao_list, itemDAOHistoryArrayList);
        this.context = context;
        this.itemDAOHistoryArrayList = itemDAOHistoryArrayList;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_item_history_list, null, true);
        TextView dateTextView = (TextView)rowView.findViewById(R.id.txt_date_history);
        TextView statusTextView = (TextView)rowView.findViewById(R.id.txt_status_hitory);
        TextView companyNameTextView = (TextView)rowView.findViewById(R.id.txt_companyname_history);

        HashMap enumToStatus = Constant.getEnumToStatus();
        itemHistoryDAO itemHistoryDAOObject = itemDAOHistoryArrayList.get(position);
        dateTextView.setText(itemHistoryDAOObject.getCreatedDate());
        statusTextView.setText((String)enumToStatus.get(itemHistoryDAOObject.getItemStatus()));
        companyNameTextView.setText(itemHistoryDAOObject.getCompanyName());

        return rowView;
    }
}

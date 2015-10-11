package com.example.junyeop_imaciislab.firsttechscm.adapter;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.junyeop_imaciislab.firsttechscm.ItemHistoryActivity;
import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;

import java.util.ArrayList;

/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class TagTradeListViewAdapter extends ArrayAdapter<itemDAO> {
    private final Activity context;
    private ArrayList<itemDAO> itemDAOArrayList;

    public ArrayList<itemDAO> getItemDAOArrayList() {
        return itemDAOArrayList;
    }

    public TagTradeListViewAdapter(Activity context, ArrayList<itemDAO> itemDAOArrayList) {
        super(context, R.layout.item_tag_trade_list, itemDAOArrayList);
        this.context = context;
        this.itemDAOArrayList = itemDAOArrayList;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_tag_trade_list, null, true);


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
}

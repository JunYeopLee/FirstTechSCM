package com.example.junyeop_imaciislab.firsttechscm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.util.tagHistoryDAO;

import java.util.ArrayList;

/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class TagHistoryListViewAdapter  extends ArrayAdapter<tagHistoryDAO> {
    private final Activity context;
    private ArrayList<tagHistoryDAO> tagHistoryDAOArrayList;

    public ArrayList<tagHistoryDAO> getTagHistoryDAOArrayList() {
        return tagHistoryDAOArrayList;
    }

    public TagHistoryListViewAdapter(Activity context, ArrayList<tagHistoryDAO> tagHistoryDAOArrayList) {
        super(context, R.layout.item_tag_history_list, tagHistoryDAOArrayList);
        this.context = context;
        this.tagHistoryDAOArrayList = tagHistoryDAOArrayList;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_tag_history_list, null, true);
        return rowView;
    }
}

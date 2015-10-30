package com.example.junyeop_imaciislab.firsttechscm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.TagReadActivity;
import com.example.junyeop_imaciislab.firsttechscm.util.tagHistoryDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        TextView tagIDTextView = (TextView)rowView.findViewById(R.id.txt_tagid_taghistory);
        TextView createdTimeTextView = (TextView)rowView.findViewById(R.id.txt_created_time_taghistory);
        TextView summaryTextView = (TextView)rowView.findViewById(R.id.txt_summary_taghistory);
        final CheckBox tagHistoryCkbox = (CheckBox)rowView.findViewById(R.id.ckbox_taghistory);

        final tagHistoryDAO tagHistoryDAOObject = tagHistoryDAOArrayList.get(position);

        tagIDTextView.setText(tagHistoryDAOObject.getTagID());
        createdTimeTextView.setText(tagHistoryDAOObject.getCreatedTime());
        summaryTextView.setText(tagHistoryDAOObject.getSummary());

        ImageButton historyDetailButton = (ImageButton)rowView.findViewById(R.id.btn_history_detail);
        historyDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NFCtagID = tagHistoryDAOObject.getTagID();
                String taggingTime = tagHistoryDAOObject.getCreatedTime();
                Long taggingTimeLong= (long)0;
                try {
                    SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd  HH:mm:ss");
                    Date d = f.parse(taggingTime);
                    taggingTimeLong = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent readIntent = new Intent(context, TagReadActivity.class);
                readIntent.putExtra("NFCtagID", NFCtagID);
                readIntent.putExtra("TaggingTime",taggingTimeLong);
                context.startActivity(readIntent);
            }
        });

        tagHistoryCkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tagHistoryDAOObject.setIsSelected(isChecked);
            }
        });
        return rowView;
    }
}
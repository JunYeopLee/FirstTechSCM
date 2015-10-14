package com.example.junyeop_imaciislab.firsttechscm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.ItemHistoryActivity;
import com.example.junyeop_imaciislab.firsttechscm.R;
import com.example.junyeop_imaciislab.firsttechscm.StatusSelectDialog;
import com.example.junyeop_imaciislab.firsttechscm.util.Constant;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by LeeJunYeop on 2015-10-11.
 */
public class ItemDAOListViewAdapter extends ArrayAdapter<itemDAO> {
    private Activity context;
    private ArrayList<itemDAO> itemDAOArrayList;
    private String activityFrom ="";
    private float scale;

    public ArrayList<itemDAO> getItemDAOArrayList() {
        return itemDAOArrayList;
    }

    public ItemDAOListViewAdapter(Activity context, ArrayList<itemDAO> itemDAOArrayList) {
        super(context, R.layout.item_itemdao_list, itemDAOArrayList);
        this.context = context;
        this.itemDAOArrayList = itemDAOArrayList;
        this.scale = context.getResources().getDisplayMetrics().density;
    }

    public ItemDAOListViewAdapter(Activity context, ArrayList<itemDAO> itemDAOArrayList,String activityFrom) {
        super(context, R.layout.item_itemdao_list, itemDAOArrayList);
        this.context = context;
        this.itemDAOArrayList = itemDAOArrayList;
        this.activityFrom = activityFrom;
        this.scale = context.getResources().getDisplayMetrics().density;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.item_itemdao_list, null, true);

        setInvisibleSomeComponent(rowView);

        TextView itemNameTextView = (TextView)rowView.findViewById(R.id.txt_itemname);
        TextView categoryTextView = (TextView)rowView.findViewById(R.id.txt_category);
        ImageButton itemStatusButton = (ImageButton)rowView.findViewById(R.id.btn_item_status);
        TextView expiryDateTextView = (TextView)rowView.findViewById(R.id.txt_expirydate);
        TextView standardTextView = (TextView)rowView.findViewById(R.id.txt_standard);
        TextView unitTextView = (TextView)rowView.findViewById(R.id.txt_unit);
        TextView priceTextView = (TextView)rowView.findViewById(R.id.txt_price);
        TextView amountTextView = (TextView)rowView.findViewById(R.id.txt_amount);
        TextView locationTextView = (TextView)rowView.findViewById(R.id.txt_location);
        TextView customerTextView = (TextView)rowView.findViewById(R.id.txt_customer);
        final CheckBox itemCheckBox = (CheckBox)rowView.findViewById(R.id.ckbox_select_item);

        final itemDAO itemDAOObject = itemDAOArrayList.get(position);

        itemNameTextView.setText(itemDAOObject.getItemName());
        setItemStatus(itemStatusButton,itemDAOObject.getItemStatus());
        categoryTextView.setText(itemDAOObject.getCategory());
        expiryDateTextView.setText(convertToDateform(itemDAOObject.getExpirydate()));
        standardTextView.setText(itemDAOObject.getStandard());
        unitTextView.setText(itemDAOObject.getUnit());
        priceTextView.setText(itemDAOObject.getPrice());
        amountTextView.setText(itemDAOObject.getAmount());
        locationTextView.setText(itemDAOObject.getLocation());
        customerTextView.setText(itemDAOObject.getCustomer());
        Boolean isSelected = itemDAOObject.getIsSelected();

        int dp5 = (int) (5 * scale + 0.5f);
        int dp15 = (int) (15 * scale + 0.5f);
        if(isSelected) {
            rowView.findViewById(R.id.layout_itemdao_information).setBackground(context.getResources().getDrawable(R.drawable.border_itemdao_tab));
            rowView.findViewById(R.id.layout_itemdao_information).setPadding(dp15, dp5, dp15, dp5);
        } else {
            rowView.findViewById(R.id.layout_itemdao_information).setBackground(context.getResources().getDrawable(R.drawable.border_itemdao));
            rowView.findViewById(R.id.layout_itemdao_information).setPadding(dp15, dp5, dp15, dp5);
        }

        itemStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusSelectDialog statusSelectDialog = new StatusSelectDialog(context, itemDAOObject.getItemStatus());
                statusSelectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                statusSelectDialog.show();
            }
        });

        ImageButton itemHistoryButton = (ImageButton)rowView.findViewById(R.id.btn_item_history);
        itemHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemHistoryActivity.class);
                context.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
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

    private String convertToDateform(String s) {
        Date date = new Date(Long.valueOf(s));
        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
        return df.format(date);
    }

    private void setItemStatus(ImageButton b,String status) {
        if(Constant.getStatusUnregistered().compareTo(status)==0) {
            b.setImageResource(R.drawable.img_status_unregistered);
        } else if(Constant.getStatusStocked().compareTo(status)==0) {
            b.setImageResource(R.drawable.img_status_stocked);
        } else if(Constant.getStatusReleased().compareTo(status)==0) {
            b.setImageResource(R.drawable.img_status_released);
        } else if(Constant.getStatusReturned().compareTo(status)==0) {
            b.setImageResource(R.drawable.img_status_return);
        } else if(Constant.getStatusDiscard().compareTo(status)==0) {
            b.setImageResource(R.drawable.img_status_discard);
        } else {
            //Error handle
        }
    }
}


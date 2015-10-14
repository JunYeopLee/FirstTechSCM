package com.example.junyeop_imaciislab.firsttechscm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junyeop_imaciislab.firsttechscm.adapter.ItemDAOListViewAdapter;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAO;
import com.example.junyeop_imaciislab.firsttechscm.util.itemDAOListWrapper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class TagWriteActivity extends AppCompatActivity {
    private String NFCtagID;
    private TextView NFCtagTextView;
    private ListView itemListView;
    private TextView modifiedTimeTextView;
    private Handler autoRefresher;

    @InjectView(R.id.ckbox_allcheck)
    public CheckBox allCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_write);
        ButterKnife.inject(this);
        new itemDAOListWrapper(this,"").clearAllStaticVariable();
        //NFCtagID = getNFCtagID();
        NFCtagID = "T1510141"; // For Test
    }
    @Override
    protected void onResume() {
        super.onResume();
        NFCtagTextView = (TextView)findViewById(R.id.txt_tagid);
        NFCtagTextView.setText(NFCtagID);
        autoRefresher = new Handler();
        autoRefresher.postDelayed(runnableAutoRefresh, 1500);
        drawListView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoRefresher.removeCallbacks(runnableAutoRefresh);
    }

    private String getNFCtagID() {
        if(!getIntent().hasExtra("NFCtagID")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(TagWriteActivity.this);
            alert.setMessage("잘못된 접근 입니다").show();
            finish();
            return null;
        }
        return getIntent().getExtras().getString("NFCtagID");
    }

    private void drawListView() {
        itemListView = (ListView)findViewById(R.id.listview_tagwrite);
        itemDAOListWrapper wrapper = new itemDAOListWrapper(this,NFCtagID);
        ArrayList<itemDAO> itemDAOArrayList = wrapper.getItemDAOArrayListFromServer(getApplicationContext());
        waitForServer();
        ItemDAOListViewAdapter itemDAOListViewAdapter = new ItemDAOListViewAdapter(this,itemDAOArrayList);
        itemListView.setAdapter(itemDAOListViewAdapter);
        itemDAOListViewAdapter.notifyDataSetChanged();
        itemListView.invalidateViews();

        modifiedTimeTextView = (TextView)findViewById(R.id.txt_modified_time);
        modifiedTimeTextView.setText(wrapper.getTagModifiedTime());

        allCheckBox.setChecked(false);
    }

    private void waitForServer() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_refresh)
    public void refreshView() {
        drawListView();
    }

    @OnClick(R.id.btn_add_item)
    public void callCheckInventory() {
        Intent intent = new Intent(TagWriteActivity.this, CheckInventoryActivity.class);
        intent.putExtra("activityFrom", "TagWriteActivity");
        startActivity(intent);
    }

    @OnClick(R.id.btn_del_selected)
    public void onClickDelSelected() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final AlertDialog dialog = null;
        ab.setMessage("선택 하신 상품들을 삭제 하시겠습니까?");
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if(dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
        });
        ab.show();
    }

    @OnCheckedChanged(R.id.ckbox_allcheck)
    public void OnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ArrayList<itemDAO> itemDAOArrayList = new itemDAOListWrapper(this,NFCtagID).getItemDAOArrayList();
        for( int i = 0 ; i < itemDAOArrayList.size() ; i++ ) {
            itemDAOArrayList.get(i).setIsSelected(isChecked);
        }
        ((ItemDAOListViewAdapter)itemListView.getAdapter()).notifyDataSetChanged();
        itemListView.invalidate();
    }

    private final Runnable runnableAutoRefresh = new Runnable()
    {
        public void run()
        {
            drawListView();
            TagWriteActivity.this.autoRefresher.postDelayed(runnableAutoRefresh, 10000);
        }
    };

}

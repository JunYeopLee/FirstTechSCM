package com.example.junyeop_imaciislab.firsttechscm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TagReadActivity extends AppCompatActivity {
    private String NFCtagID;
    private TextView NFCtagTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_read);
        NFCtagID = getIntent().getExtras().getString("NFCtagID");
        NFCtagTest = (TextView)findViewById(R.id.txt_tagtest);
        NFCtagTest.setText(NFCtagID);
    }
}

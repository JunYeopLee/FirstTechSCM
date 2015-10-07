package com.example.junyeop_imaciislab.firsttechscm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.widget.Toast;

public class NFCTaggingActivity extends Activity {
    public static final String TAG = "NFCTaggingActivity";

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private String activityToGo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfctagging);
        resolveIntent(getIntent());
        activityToGo = getIntent().getExtras().getString("activityToGo");

        Toast.makeText(this, activityToGo, Toast.LENGTH_LONG).show();



        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter==null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(NFCTaggingActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setMessage("NFC TAG를 사용할 수 없는 기기입니다");
            alert.show();
            finish();
        } else if(!mNfcAdapter.isEnabled()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(NFCTaggingActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mNfcAdapter == null) return;
                    Intent intent = new Intent( Settings.ACTION_NFC_SETTINGS );
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setMessage("NFC TAG 설정창으로 이동하시겠습니까?");
            alert.show();
        }

        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            if (!mNfcAdapter.isEnabled()) {
                showWirelessSettingsDialog();
            }
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    private void showWirelessSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("NFC TAG 설정창으로 이동하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.create().show();
        return;
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            StringBuilder sb = new StringBuilder();
            byte[] id = ((Tag)tag).getId();
            Toast.makeText(this, sb.append("Tag ID (HEX): ").append(getHex(id)).toString(), Toast.LENGTH_LONG).show();

            if(activityToGo.compareTo("write")==0) {
                Intent writeIntent = new Intent(NFCTaggingActivity.this, com.example.junyeop_imaciislab.firsttechscm.TagWriteActivity.class);
                writeIntent.putExtra("NFCtag", getHex(id).toString());
                startActivity(writeIntent);
                finish();
            } else {
                Intent readIntent = new Intent(NFCTaggingActivity.this, com.example.junyeop_imaciislab.firsttechscm.TagReadActivity.class);
                readIntent.putExtra("NFCtag", getHex(id).toString());
                startActivity(readIntent);
                finish();
            }
        }
    }

    private String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i < bytes.length-1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }
}

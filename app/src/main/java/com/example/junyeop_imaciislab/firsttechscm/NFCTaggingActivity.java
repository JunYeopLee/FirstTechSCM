package com.example.junyeop_imaciislab.firsttechscm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcBarcode;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class NFCTaggingActivity extends Activity {
    public static final String TAG = "NFCTaggingActivity";
    private NfcAdapter mNfcAdapter; // NFC 어댑터
    private PendingIntent pendingIntent;
    private IntentFilter[] mIntentFilters; // 인텐트 필터
    private TextView NFCtextview;
    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    NfcBarcode.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfctagging);
        Log.d(TAG, "onCreate");
        NFCtextview = (TextView)findViewById(R.id.txt_nfctag);
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
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        Intent targetIntent = new Intent(this,getClass());
        targetIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, targetIntent, 0);
        /*
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        iFilter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        iFilter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        */
        IntentFilter ndefDiscovered = new IntentFilter(
                NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndefDiscovered.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            ndefDiscovered.addDataType("*/*");
            mIntentFilters = new IntentFilter[] { ndefDiscovered };
        } catch (Exception e) {
            NFCtextview.setText("Make IntentFilter error");
        }
        if( mNfcAdapter != null )
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, mIntentFilters, techList);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if( mNfcAdapter != null )
            mNfcAdapter.disableForegroundDispatch(this);
    }

    // NFC 태그 정보 수신 함수. 인텐트에 포함된 정보를 분석해서 화면에 표시
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
        // 인텐트에서 액션을 추출
        String action = intent.getAction();
        // 인텐트에서 태그 정보 추출
        String tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG).toString();
        String strMsg = action + "\n\n" + tag;
        // 액션 정보와 태그 정보를 화면에 출력
        NFCtextview.setText(strMsg);

        // 인텐트에서 NDEF 메시지 배열을 구한다
        Parcelable[] messages = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_ID);
        if(messages == null) return;

        for(int i=0; i < messages.length; i++)
            showMsg((NdefMessage)messages[i]);
    }

    // NDEF 메시지를 화면에 출력
    public void showMsg(NdefMessage mMessage) {
        String strMsg = "", strRec="";
        // NDEF 메시지에서 NDEF 레코드 배열을 구한다
        NdefRecord[] recs = mMessage.getRecords();
        for (int i = 0; i < recs.length; i++) {
            // 개별 레코드 데이터를 구한다
            NdefRecord record = recs[i];
            byte[] payload = record.getPayload();
            // 레코드 데이터 종류가 텍스트 일때
            if( Arrays.equals(record.getType(), NdefRecord.RTD_TEXT) ) {
                // 버퍼 데이터를 인코딩 변환
                strRec = byteDecoding(payload);
                strRec = "Text: " + strRec;
            }
            // 레코드 데이터 종류가 URI 일때
            else if( Arrays.equals(record.getType(), NdefRecord.RTD_URI) ) {
                strRec = new String(payload, 0, payload.length);
                strRec = "URI: " + strRec;
            }
            strMsg += ("\n\nNdefRecord[" + i + "]:\n" + strRec);
        }
        NFCtextview.append(strMsg);
    }

    // 버퍼 데이터를 디코딩해서 String 으로 변환
    public String byteDecoding(byte[] buf) {
        String strText="";
        String textEncoding;
        if((buf[0] & 0200) == 0) textEncoding="UTF-8";
        else textEncoding="UTF-16";
        int langCodeLen = buf[0] & 0077;
        try {
            strText = new String(buf, langCodeLen + 1,
                    buf.length - langCodeLen - 1, textEncoding);
        } catch(Exception e) {
            Log.d(TAG, e.toString());
        }
        return strText;
    }
}

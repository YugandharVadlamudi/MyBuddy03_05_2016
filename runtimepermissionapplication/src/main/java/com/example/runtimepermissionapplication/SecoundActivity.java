package com.example.runtimepermissionapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecoundActivity extends AppCompatActivity {
    private final String TAG = SecoundActivity.class.getSimpleName();
    private Button buttonRecall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secound);
        Intent intent = getIntent();
//        intent.h
        if (intent.hasExtra("SendData")) {
            String storeValue = intent.getExtras().getString("SendData");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SecoundActivity.this);
            Log.e(TAG, "onCreate: storeValue->" + storeValue + "\n sharedPreference->" + sharedPreferences);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("SendData", storeValue);
            editor.commit();
        } else {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SecoundActivity.this);
            String value = sharedPreferences.getString("SendData", "nullJ");
            Log.e(TAG, "onCreate: value is->"+value );
        }
        buttonRecall = (Button) findViewById(R.id.bt_secound_recall);
        buttonRecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SecoundActivity.class));
            }
        });

    }
}

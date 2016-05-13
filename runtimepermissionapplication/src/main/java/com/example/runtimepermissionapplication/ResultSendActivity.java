package com.example.runtimepermissionapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResultSendActivity extends AppCompatActivity {
private Button buttonFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_send);
        buttonFinish=(Button)findViewById(R.id.bt_resultsend_destroy);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setData(Uri.parse("hello"));
                setResult(1,intent);
                finish();
            }
        });
    }

}

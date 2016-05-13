package com.example.kiran.mybuddy.activitys;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kiran.mybuddy.R;
import com.example.kiran.mybuddy.dbhelper.DataBaseHelper;

public class RegistrationActivity extends Activity implements View.OnClickListener{
    private EditText etReistrationFirstName;
    private EditText etReistrationLastName;
    private EditText etReistrationEmailId;
    private EditText etReistrationPassword;
    private EditText etReistrationConformPassword;
    private ImageView ivWindowTitleMenu;
    private ImageView ivWindowTitleCreateContact;
    private TextView tvWindowTitleTitle;
    private Toolbar toolbar;

    Button btReistrationsbmit;
    private final String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_registration);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        initilization();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_windowtitle_menu:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                break;
            /*case android.R.id.:
                Toast.makeText(RegistrationActivity.this, "Navigation Click", Toast.LENGTH_SHORT).show();
                break;*/
        }

    }

    private void initilization() {
        etReistrationFirstName = (EditText) findViewById(R.id.et_registration_firstname);
        etReistrationLastName = (EditText) findViewById(R.id.et_registration_lastname);
        etReistrationEmailId = (EditText) findViewById(R.id.et_registration_emailid);
        etReistrationPassword = (EditText) findViewById(R.id.et_registration_password);
        etReistrationConformPassword = (EditText) findViewById(R.id.et_registration_confirmpassword);
        btReistrationsbmit = (Button) findViewById(R.id.bt_registration_submit);
        toolbar=(Toolbar)findViewById(R.id.tb_appbar);
        Log.e(TAG, "initilization: toolbar->"+toolbar);
        toolbar.setTitle(getResources().getString(R.string.titleRegistration));
        toolbar.setTitleTextColor(getResources().getColor(R.color.bg_login));
        toolbar.setTitleTextAppearance(getApplicationContext(),R.style.titletextstyle);
        toolbar.setNavigationIcon(R.drawable.ic_all_thin_back_64);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btReistrationsbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseInsertUserDetails();

            }
        });
        etReistrationConformPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(RegistrationActivity.this, "Done Click", Toast.LENGTH_SHORT).show();
                    databaseInsertUserDetails();
                }
                return false;
            }
        });
//        ivWindowTitleMenu.setOnClickListener(this);
    }

    private void databaseInsertUserDetails() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COLUMN_FNAME, etReistrationFirstName.getText().toString());
        contentValues.put(DataBaseHelper.COLUMN_LNAME, etReistrationLastName.getText().toString());
        contentValues.put(DataBaseHelper.COLUMN_EMAIL, etReistrationEmailId.getText().toString());
        contentValues.put(DataBaseHelper.COLUMN_PASSWORD, etReistrationConformPassword.getText().toString());
        long rowId = dataBaseHelper.insertQuery(contentValues);
        Log.e(TAG, "onClick: rowId->" + rowId);
        if (rowId != -1) {
            Toast.makeText(RegistrationActivity.this, "Row inserted", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else {
            Toast.makeText(RegistrationActivity.this, "Row not inserted", Toast.LENGTH_SHORT).show();
        }
    }
}

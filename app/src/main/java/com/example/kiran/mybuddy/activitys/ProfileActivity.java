package com.example.kiran.mybuddy.activitys;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kiran.mybuddy.R;
import com.example.kiran.mybuddy.Utils.Utils;
import com.example.kiran.mybuddy.dbhelper.DataBaseHelper;

public class ProfileActivity extends Activity implements View.OnClickListener{
    private EditText etProfileFirstName;
    private EditText etProfileLastName;
    private EditText etProfileEmail;
    private ImageView ivProfilePick;
    private ImageView ivWindowTitleMenu;
    private ImageView ivWindowTitleCreateContact;
    private TextView tvWindowTitleheader;
    private Button btProfileSubmit;
    private Toolbar toolbar;
    private DataBaseHelper dbProfile;
    private Cursor crsorProfile;
    private String id;
    private final String TAG=ProfileActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_profile);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.window_title);
        initilization();
    }

    private void initilization() {
        etProfileEmail=(EditText)findViewById(R.id.et_profile_email);
        etProfileFirstName=(EditText)findViewById(R.id.et_profile_firstname);
        etProfileLastName=(EditText)findViewById(R.id.et_profile_lastname);
        ivProfilePick=(ImageView)findViewById(R.id.iv_profile_profilepic);
//        ivWindowTitleMenu=(ImageView)findViewById(R.id.iv_windowtitle_menu);
//        ivWindowTitleCreateContact=(ImageView)findViewById(R.id.iv_windowtitle_createcontact);
//        tvWindowTitleheader=(TextView)findViewById(R.id.tv_windowtitle_Title);
        btProfileSubmit=(Button)findViewById(R.id.bt_profile_submit);
        toolbar=(Toolbar)findViewById(R.id.tb_appbar);
        toolbar.setTitle(getResources().getString(R.string.title_profile));
        toolbar.setTitleTextColor(getResources().getColor(R.color.bg_login));
        toolbar.setTitleTextAppearance(getApplicationContext(), R.style.titletextstyle);
        toolbar.setNavigationIcon(R.drawable.ic_all_thin_back_64);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btProfileSubmit.setOnClickListener(this);
        dbProfile=new DataBaseHelper(getApplicationContext());

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
//        ivWindowTitleCreateContact.setVisibility(View.INVISIBLE);
//        ivWindowTitleMenu.setImageResource(R.drawable.ic_all_back_64);
//        ivWindowTitleMenu.setOnClickListener(this);
//        tvWindowTitleheader.setText(getString(R.string.proifile_title));
        id = sharedPreferences.getString(Utils.USERID,"null");
        setDataToEditText();
        registeringListers();

    }

    private void setDataToEditText() {
        crsorProfile = dbProfile.selectProfileFromDB(Integer.valueOf(id));
        crsorProfile.moveToFirst();
        etProfileFirstName.setText(crsorProfile.getString(crsorProfile
                .getColumnIndex(DataBaseHelper.COLUMN_FNAME)));
        etProfileLastName.setText(crsorProfile.getString(crsorProfile
                .getColumnIndex(DataBaseHelper.COLUMN_LNAME)));
        etProfileEmail.setText(crsorProfile.getString(crsorProfile
                .getColumnIndex(DataBaseHelper.COLUMN_EMAIL)));

    }

    private void registeringListers() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_windowtitle_menu:
                finish();
                break;
            case R.id.bt_profile_submit:
                String fname=etProfileFirstName.getText().toString().trim();
                String lname=etProfileLastName.getText().toString().trim();
                int noEffectedRows=dbProfile.upDateUserDetails(id,fname,lname);
                if(noEffectedRows==1)
                {
                    Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ProfileActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void onBackPressed() {
         finish();
    }
}

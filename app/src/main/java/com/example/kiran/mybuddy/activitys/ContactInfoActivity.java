package com.example.kiran.mybuddy.activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kiran.mybuddy.R;
import com.example.kiran.mybuddy.Utils.Utils;
import com.example.kiran.mybuddy.dbhelper.DataBaseHelper;

public class ContactInfoActivity extends Activity {
    private Intent intent;
    private DataBaseHelper dataBaseHelper;
    private Cursor cursor;
    private ImageView ivContactInfoProfile;
    private ImageView ivWidowTitleMenu;
    private ImageView ivWidowTitleCreateContact;
    private TextView tvContactInfoName;
    private TextView tvContactInfoEmail;
    private TextView tvContactInfoWork;
    private TextView tvContactInfoCity;
    private TextView tvContactInfoState;
    private TextView tvContactInfoCountry;
    private TextView tvContactInfoProfssion;
    private TextView tvContactInfoPhoneNumber;
    private TextView tvContactInfoBloodGroup;
    private TextView tvContactInfoAge;
    private TextView tvWindowTitle;


    private String TAG = ContactInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_contact_info);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.window_title);
        initilization();
        intent = getIntent();
        String id = intent.getStringExtra(Utils.CONTACTID);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Log.e(TAG, "onCreate: " + id);
        cursor = dataBaseHelper.selectContactInfo(id);
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            Bitmap bitmap= BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CIMGPATH)));
            Log.e(TAG, "onCreate: bitmap->"+bitmap );
            ivContactInfoProfile.setImageBitmap(bitmap);
            tvContactInfoName.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CNAME)));
            tvContactInfoAge.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CAGE)));
            tvContactInfoPhoneNumber.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CPHONENUMBER)));
            tvContactInfoEmail.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CEMAIL)));
            tvContactInfoBloodGroup.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CBLOODGROUP)));
            tvContactInfoProfssion.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CPRFESION)));
            tvContactInfoWork.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CWORKING)));
            tvContactInfoCity.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CCITY)));
            tvContactInfoState.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CSTATE)));
            tvContactInfoCountry.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CCOUNTRY)));

        }

    }

    private void initilization() {
        ivContactInfoProfile = (ImageView) findViewById(R.id.iv_contactinfo_profilepick);
        ivWidowTitleMenu = (ImageView) findViewById(R.id.iv_windowtitle_menu);
        ivWidowTitleCreateContact = (ImageView) findViewById(R.id.iv_windowtitle_createcontact);
        tvWindowTitle=(TextView)findViewById(R.id.tv_windowtitle_Title);
        tvContactInfoName = (TextView) findViewById(R.id.tv_contactinfo_name);
        tvContactInfoPhoneNumber = (TextView) findViewById(R.id.tv_contactinfo_phonenumber);
        tvContactInfoAge = (TextView) findViewById(R.id.tv_contactinfo_age);
        tvContactInfoEmail = (TextView) findViewById(R.id.tv_contactinfo_email);
        tvContactInfoBloodGroup = (TextView) findViewById(R.id.tv_contactinfo_bloodgroup);
        tvContactInfoWork = (TextView) findViewById(R.id.tv_contactinfo_working);
        tvContactInfoCity = (TextView) findViewById(R.id.tv_contactinfo_city);
        tvContactInfoState = (TextView) findViewById(R.id.tv_contactinfo_state);
        tvContactInfoCountry = (TextView) findViewById(R.id.tv_contactinfo_country);
        tvContactInfoProfssion = (TextView) findViewById(R.id.tv_contactinfo_profession);
        tvWindowTitle.setText(getString(R.string.contactinfotitle));
        ivWidowTitleMenu.setImageResource(R.drawable.ic_all_back_64);
        ivWidowTitleCreateContact.setVisibility(View.INVISIBLE);
        ivWidowTitleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

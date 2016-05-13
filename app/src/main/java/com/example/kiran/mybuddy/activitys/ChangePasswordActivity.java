package com.example.kiran.mybuddy.activitys;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kiran.mybuddy.R;
import com.example.kiran.mybuddy.Utils.Utils;
import com.example.kiran.mybuddy.dbhelper.DataBaseHelper;

public class ChangePasswordActivity extends Activity implements View.OnClickListener {
private EditText etNewPassword;
private EditText etConfirmPasswrod;
    private ImageView ivWindowTitleMenu;
    private ImageView ivWindowTitleCreateContact;
    private TextView tvWindowTitleheader;
    private Button btChangePasswordSubmit;
    private Toolbar toolbar;
    private CheckBox cbChangePasswordShowPassword;
    private DataBaseHelper dbProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_change_password);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.window_title);
        initilization();
    }

    private void initilization() {
        etNewPassword=(EditText)findViewById(R.id.et_changepassword_newpasswrod);
        etConfirmPasswrod=(EditText)findViewById(R.id.et_changepassword_cofirmpassword);
//        ivWindowTitleMenu=(ImageView)findViewById(R.id.iv_windowtitle_menu);
//        ivWindowTitleCreateContact=(ImageView)findViewById(R.id.iv_windowtitle_createcontact);
//        tvWindowTitleheader=(TextView)findViewById(R.id.tv_windowtitle_Title);
        btChangePasswordSubmit=(Button)findViewById(R.id.bt_changepassword_submit);
        cbChangePasswordShowPassword=(CheckBox)findViewById(R.id.cb_changepassword_showpassword);
        toolbar=(Toolbar)findViewById(R.id.tb_appbar);
        toolbar.setTitle(getResources().getString(R.string.title_changepassword));
        toolbar.setTitleTextColor(getResources().getColor(R.color.bg_login));
        toolbar.setTitleTextAppearance(getApplicationContext(),R.style.titletextstyle);
        toolbar.setNavigationIcon(R.drawable.ic_all_thin_back_64);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbProfile=new DataBaseHelper(getApplicationContext());
//        tvWindowTitleheader.setText(getString(R.string.changepasswordTitle));
//        ivWindowTitleMenu.setImageResource(R.drawable.ic_all_back_64);
//        ivWindowTitleCreateContact.setVisibility(View.INVISIBLE);
        btChangePasswordSubmit.setOnClickListener(this);
//        ivWindowTitleMenu.setOnClickListener(this);
        cbChangePasswordShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    Toast.makeText(ChangePasswordActivity.this, "Chebox Checked", Toast.LENGTH_SHORT).show();
                    etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etConfirmPasswrod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this, "Chekbox UnCheckeds", Toast.LENGTH_SHORT).show();
                    etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etConfirmPasswrod.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_changepassword_submit:
//                Toast.makeText(ChangePasswordActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                String newPassword;
                String confirmPassword;
                newPassword=etNewPassword.getText().toString().trim();
                confirmPassword=etConfirmPasswrod.getText().toString().trim();
                if(newPassword.equals(confirmPassword))
                {
                    SharedPreferences sharedPreferences= PreferenceManager
                                                        .getDefaultSharedPreferences(ChangePasswordActivity.this);
                    int noRowsEffected=dbProfile.upDatePassword(newPassword,sharedPreferences.getString(Utils.USERID,""));
                    if(noRowsEffected==1)
                    {
                        Toast.makeText(ChangePasswordActivity.this, "Successfully ChangedPassword", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this, "New password,Conform Password didn't match", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_windowtitle_menu:
                finish();
                break;
        }
    }
}

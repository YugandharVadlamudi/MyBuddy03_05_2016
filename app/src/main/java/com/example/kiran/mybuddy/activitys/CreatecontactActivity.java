
package com.example.kiran.mybuddy.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kiran.mybuddy.R;
import com.example.kiran.mybuddy.Utils.Utils;
import com.example.kiran.mybuddy.dbhelper.DataBaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class CreatecontactActivity extends Activity implements View.OnClickListener {

    private EditText etName;
    private EditText etAge;
    private EditText etPhoneNumber;
    private EditText etEmail;
    private EditText etProfession;
    private EditText etBloodGroup;
    private EditText etWorking;
    private EditText etCity;
    private EditText etState;
    private EditText etCountry;
    private ImageView ivProfilePick;
    private ImageView ivWindowTitleMenu;
    private ImageView ivWindowTitleCreateContact;
    private TextView tvWindowTitleTitle;
    private Button btSumbit;
    private ContentValues contactValues;
    private String imagePath;
    private final int REQUEST_CAMERA = 1;
    private final int REQUST_GALLERY = 2;
    private final static String TAG = CreatecontactActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_createcontact);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
        initlization();
    }

    private void initlization() {
        etName = (EditText) findViewById(R.id.et_createcontact_name);
        etAge = (EditText) findViewById(R.id.et_createcontact_age);
        etPhoneNumber = (EditText) findViewById(R.id.et_createcontact_phonenumber);
        etEmail = (EditText) findViewById(R.id.et_createcontact_email);
        etProfession = (EditText) findViewById(R.id.et_createcontact_profession);
        etBloodGroup = (EditText) findViewById(R.id.et_createcontact_bloodgroup);
        etWorking = (EditText) findViewById(R.id.et_createcontact_working);
        etCity = (EditText) findViewById(R.id.et_createcontact_city);
        etState = (EditText) findViewById(R.id.et_createcontact_state);
        etCountry = (EditText) findViewById(R.id.et_createcontact_country);
        btSumbit = (Button) findViewById(R.id.bt_contactregistration_submit);
        ivProfilePick = (ImageView) findViewById(R.id.iv_createcontact_click);
        ivWindowTitleMenu=(ImageView)findViewById(R.id.iv_windowtitle_menu);
        ivWindowTitleCreateContact=(ImageView)findViewById(R.id.iv_windowtitle_createcontact);
        tvWindowTitleTitle =(TextView)findViewById(R.id.tv_windowtitle_Title);
        ivWindowTitleMenu.setImageResource(R.drawable.ic_all_back_64);
        ivWindowTitleCreateContact.setVisibility(View.INVISIBLE);
        tvWindowTitleTitle.setText(getString(R.string.CreateContactTitle));
        ivProfilePick.setOnClickListener(this);
        ivWindowTitleMenu.setOnClickListener(this);
        btSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = Utils.dataBaseHelper(getApplicationContext());
                contactValues = new ContentValues();
                putValues();
                long rowId = dataBaseHelper.insertContact(contactValues);
                if (rowId != -1) {
                    Toast.makeText(CreatecontactActivity.this, "Contact Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),ContactsActivity.class)
                                    .putExtra(Utils.FROMSCREEN,"Login"));

                } else {
                    Toast.makeText(CreatecontactActivity.this, "Contact didn't created try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void putValues() {
        contactValues.put(DataBaseHelper.COLUMN_CNAME, etName.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CAGE, etAge.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CPHONENUMBER, etPhoneNumber.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CEMAIL, etEmail.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CPRFESION, etProfession.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CBLOODGROUP, etBloodGroup.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CWORKING, etWorking.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CCITY, etCity.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CSTATE, etState.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CCOUNTRY, etCountry.getText().toString());
        contactValues.put(DataBaseHelper.COLUMN_CIMGPATH,imagePath);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_createcontact_click:
//                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(CreatecontactActivity.this, android.R.style.Theme_Dialog))
                AlertDialog.Builder adialog = new AlertDialog.Builder(CreatecontactActivity.this)
                        .setTitle("Add photo")
                        .setItems(new String[]{"Camera", "Galler", "Cancle"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e(TAG, "onClick: which item seleted" + which);
                                switch (which) {
                                    case 0:
                                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                                , REQUEST_CAMERA);
                                        break;
                                    case 1:
                                        Intent selectPickGallery = new Intent(Intent.ACTION_PICK
                                                , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        selectPickGallery.setType("image/*");
                                        startActivityForResult(Intent.createChooser(selectPickGallery, "Select Image"), REQUST_GALLERY);
                                        break;
                                    case 2:
                                        dialog.dismiss();
                                        break;
                                    default:
                                        break;
                                }
                            }

                        });
                adialog.create().show();
                break;
            case R.id.bt_contactregistration_submit:
                break;
            case R.id.iv_windowtitle_menu:
                startActivity(new Intent(getApplicationContext(),ContactsActivity.class)
                        .putExtra(Utils.FROMSCREEN,"Login"));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: RequstCamera");
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);
            imagePath=getRealPath(tempUri);
            ivProfilePick.setImageBitmap(bitmap);
//            imagePath=getRealPath(data.getData());
//            ivProfilePick.setImageBitmap(bitmap);
        } else if (requestCode == REQUST_GALLERY && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: RequstGallery");
            Uri uri = data.getData();
           imagePath= getRealPath(uri);
            try {
                decodeBitMap(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    private void decodeBitMap(Uri uri) throws FileNotFoundException {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),null,options);
        final int REQUIRED_SIZE=100;
        int width_temp=options.outWidth;
        int height_temp=options.outHeight;
        int scale=1;
        while(true)
        {
            if((width_temp/2<REQUIRED_SIZE)&&(height_temp/2<REQUIRED_SIZE))
            {
                break;
            }
            width_temp/=2;
            height_temp/=2;
            scale*=2;
        }
        BitmapFactory.Options options1=new BitmapFactory.Options();
        options1.inSampleSize=scale;
        Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(uri),null,options1);
        ivProfilePick.setImageBitmap(bitmap);
    }

    private String getRealPath(Uri tempUri) {
        Cursor cursor = getContentResolver().query(tempUri, null, null, null, null);
        cursor.moveToFirst();
        int columnpath = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        Log.e(TAG, "getRealPath: columnpath" + cursor.getString(columnpath));
        return (cursor.getString(columnpath));
    }

    private Uri getImageUri(Context applicationContext, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String imagePath = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver()
                , bitmap, "Title", "hello");
        return Uri.parse(imagePath);
    }
}
 /*String[] projection={MediaStore.MediaColumns.DATA};
            Log.e(TAG, "onActivityResult: projection"+projection );
            CursorLoader cursorLoader=new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
            Cursor cursor=cursorLoader.loadInBackground();
            int columnIndex=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String imagepath=cursor.getString(columnIndex);
            ivProfilePick.setImageBitmap(BitmapFactory.decodeFile(imagepath));*/
//            Log.e(TAG, "onActivityResult: imagepath"+imagepath);
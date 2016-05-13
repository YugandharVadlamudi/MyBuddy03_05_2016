package com.example.runtimepermissionapplication;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private final String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initilization();
    }

    private void initilization() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            int hasWriteContactPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
            Log.e(TAG, "initilization: Build.version.sdk_int"+Build.VERSION.SDK_INT
                    +"\n Build.version_codes.M"+Build.VERSION_CODES.M
                    +"\n hasWriteContactPermission->"+hasWriteContactPermission);
            if (hasWriteContactPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
            }
        }
//        insertDummyContact();
    }

    private void insertDummyContact() {
        ArrayList<ContentProviderOperation> arrayList=new ArrayList<ContentProviderOperation>(2);
        ContentProviderOperation.Builder builder=ContentProviderOperation
                                                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                                                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,null)
                                                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME,null);
        arrayList.add(builder.build());
        ContentResolver contentResolver=getContentResolver();
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY,arrayList);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "onRequestPermissionsResult: requestCode->"+requestCode
                    +"\n"+"permissions->"+permissions
                    +"\n"+"grantResults->"+grantResults);
        switch (requestCode)
        {
            case 1:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                    insertDummyContact();
                }
                else {
                    Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

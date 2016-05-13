package com.example.kiran.mybuddy.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kiran.mybuddy.R;
import com.example.kiran.mybuddy.Utils.Data;
import com.example.kiran.mybuddy.Utils.Utils;
import com.example.kiran.mybuddy.adapters.ContactListAdapter;
import com.example.kiran.mybuddy.dbhelper.DataBaseHelper;

import java.util.ArrayList;

public class ContactsActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private Button btcontactsCreateConact;
    private ImageView ivWindowTitleMenu;
    private ImageView ivWindowTitleCreateContact;
    private TextView tvWindowTitleTitle;
    private Toolbar toolbar;
    private Cursor cursor;
    private Data data;
    private ContactListAdapter contactsListAdapter;
    private ArrayList<Data> arrayListContacts;
    private Context context;
    private Intent intent;
    private String checkFrom;
    private DataBaseHelper dataBaseHelper;
    private int toContacts;
    private final String TAG = ContactsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        checkFrom = intent.getStringExtra("FromScreen");
        Log.e(TAG, "onCreate: checkFrom->" + checkFrom);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_contacts);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.window_title);
        initalization();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_windowtitle_createcontact:
                startActivity(new Intent(context, CreatecontactActivity.class));
                finish();
                break;
            case R.id.iv_windowtitle_menu:
                startActivity(new Intent(context, ContactsActivity.class)
                        .putExtra(Utils.FROMSCREEN, "Login"));
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick: position->" + position);
        Toast.makeText(ContactsActivity.this, "contactscount->" + arrayListContacts.size(), Toast.LENGTH_SHORT).show();
        Intent intentCVP = new Intent(context, ContactViewPagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Utils.CONTACTS, arrayListContacts);
        bundle.putInt(Utils.POSITION, position);
//        startActivity(intentCVP);
        if (checkFrom.equals("Login")) {
            bundle.putString(Utils.FROMSCREEN, "Login");
            intentCVP.putExtras(bundle);
            startActivityForResult(intentCVP, 1);
        } else if (checkFrom.equals("Favourite")) {
            bundle.putString(Utils.FROMSCREEN, "Favourite");
            intentCVP.putExtras(bundle);
            startActivityForResult(intentCVP, 2);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(ContactsActivity.this, "Hold Multiwindow icon fiew secounds select logout ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "Profile");
        menu.add(0, 2, 2, "Favourit");
        menu.add(0, 3, 3, "Change Password");
        menu.add(0, 4, 4, "Logout");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case 2:
                startActivity(new Intent(getApplicationContext(), ContactsActivity.class)
                        .putExtra(Utils.FROMSCREEN, "Favourite"));
                finish();
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                break;
            case 4:
                final AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this)
                        .setTitle(getString(R.string.contactsactivity_logout))
                        .setMessage(getString(R.string.contactsactivity_message))
                        .setPositiveButton(getString(R.string.contactsactivity_logoutyes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            }
                        })
                        .setNegativeButton(getString(R.string.contactsactivity_logoutno), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.e(TAG, "onActivityResult: LoginContacts");
            initalization();
        } else if (requestCode == 2) {
            Log.e(TAG, "onActivityResult: FavouritContacts");
            initalization();
        }
    }

    private void initalization() {
        context = getApplicationContext();
        listView = (ListView) findViewById(R.id.lv_contacts_show);
        toolbar = (Toolbar) findViewById(R.id.tb_appbar);
        Log.e(TAG, "initalization: loge->" + toolbar);
//        ivWindowTitleMenu=(ImageView)findViewById(R.id.iv_windowtitle_menu);
//        ivWindowTitleCreateContact=(ImageView)findViewById(R.id.iv_windowtitle_createcontact);
//        tvWindowTitleTitle =(TextView)findViewById(R.id.tv_windowtitle_Title);
//        ivWindowTitleCreateContact.setVisibility(View.GONE);
//        ivWindowTitleCreateContact.setImageResource(R.drawable.ic_contacts_createcontact_64);
//        ivWindowTitleMenu.setImageResource(R.drawable.ic_contacts_menu_64);
        arrayListContacts = new ArrayList<>();
        if (checkFrom.equals("Login")) {
//            tvWindowTitleTitle.setText(getString(R.string.ContactsTitle));
//            ivWindowTitleCreateContact.setOnClickListener(this);
//            ivWindowTitleMenu.setVisibility(View.INVISIBLE);
            toolbar.setTitle(getResources().getString(R.string.title_contacts));
            toolbar.setTitleTextColor(getResources().getColor(R.color.bg_login));
            toolbar.setTitleTextAppearance(getApplicationContext(), R.style.titletextstyle);
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menuitems);
            getContacts();
        } else if (checkFrom.equals("Favourite")) {
//            tvWindowTitleTitle.setText(getString(R.string.FavouriteTitle));
//            ivWindowTitleMenu.setImageResource(R.drawable.ic_all_back_64);
//            ivWindowTitleCreateContact.setVisibility(View.INVISIBLE);
//            ivWindowTitleMenu.setOnClickListener(this);
            toolbar.setTitle(getResources().getString(R.string.title_Favourit));
            toolbar.setTitleTextColor(getResources().getColor(R.color.bg_login));
            toolbar.setTitleTextAppearance(getApplicationContext(), R.style.titletextstyle);
            toolbar.setNavigationIcon(R.drawable.ic_all_thin_back_64);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, ContactsActivity.class)
                            .putExtra(Utils.FROMSCREEN, "Login"));
                    finish();
                }
            });
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, ContactsActivity.class)
                            .putExtra(Utils.FROMSCREEN, "Login"));
                    finish();
                }
            });
            getFavouiteContacts();
        }
        ;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getOrder()) {
                    case 1:
                        startActivity(new Intent(context, CreatecontactActivity.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(), ContactsActivity.class)
                                .putExtra(Utils.FROMSCREEN, "Favourite"));
                        finish();
                        break;
                    case 5:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this)
                                .setTitle(getString(R.string.contactsactivity_logout))
                                .setMessage(getString(R.string.contactsactivity_message))
                                .setPositiveButton(getString(R.string.contactsactivity_logoutyes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                                    }
                                })
                                .setNegativeButton(getString(R.string.contactsactivity_logoutno), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();
                        break;

                }

                return false;
            }
        });


    }

    private void getFavouiteContacts() {
        dataBaseHelper = Utils.dataBaseHelper(context);
        cursor = dataBaseHelper.seleFavouritContact();
        Log.e(TAG, "getFavouiteContacts: cursorFavouritCount->" + cursor.getCount());
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            arrayListContacts.add(setData());
        } else {
            while (cursor.moveToFirst()) {
                arrayListContacts.add(setData());
            }
        }
        contactsListAdapter = new ContactListAdapter(context, arrayListContacts);
        listView.setAdapter(contactsListAdapter);
        listView.setOnItemClickListener(this);

    }

    private void getContacts() {

        dataBaseHelper = Utils.dataBaseHelper(context);
        cursor = dataBaseHelper.selectContacts();
        Log.e(TAG, "getContacts: " + cursor.getCount());

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            arrayListContacts.add(setData());
        } else {

            while (cursor.moveToNext()) {
                /*data = new Data();
                data.setImagePath(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CIMGPATH)));
                data.setName(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CNAME)));
                data.setPhonenumber(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CPHONENUMBER)));*/
//                arrayListContacts.add(data);
                arrayListContacts.add(setData());
            }
        }

        contactsListAdapter = new ContactListAdapter(context, arrayListContacts);
        listView.setAdapter(contactsListAdapter);
        listView.setOnItemClickListener(this);

    }


    private Data setData() {
        data = new Data();
        data.setImagePath(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CIMGPATH)));
        data.setName(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CNAME)));
        data.setPhonenumber(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CPHONENUMBER)));
        data.setAge(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CAGE)));
        data.setBloodGroup(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CBLOODGROUP)));
        data.setCity(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CCITY)));
        data.setCountry(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CCOUNTRY)));
        data.setEmail(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CEMAIL)));
        data.setState(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CSTATE)));
        data.setWorking(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CWORKING)));
        data.setProfession(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CPRFESION)));
        data.setFavourit(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CFAVOURIT)));
        data.setId(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_CID)));
        return data;
    }
}

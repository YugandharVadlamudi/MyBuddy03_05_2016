package com.example.kiran.mybuddy.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kiran.mybuddy.R;
import com.example.kiran.mybuddy.Utils.Data;
import com.example.kiran.mybuddy.Utils.Utils;
import com.example.kiran.mybuddy.dbhelper.DataBaseHelper;

import java.io.File;
import java.util.ArrayList;

public class ContactViewPagerActivity extends Activity implements View.OnClickListener {
    private View view;
    private TextView tvContactViewPagerEmpDetails;
    private TextView tvContactViewPagername;
    private TextView tvWindowTitleHeader;
    private ImageView imageView;
    private ImageView ivContactViewPagerCall;
    private ImageView ivContactViewPagerSms;
    private ImageView ivContactViewPagerEmail;
    private ImageView ivWindowTitleMenu;
    private ImageView ivWindowTitleCreateContact;
    private LinearLayout llContactViewPagerFavourit;
    private LinearLayout llContactViewPagerContactInfo;
    private ViewPager vpContactViewPager;
    private LayoutInflater layoutInflater;
    private ArrayList<Data> contacts;
    private DataBaseHelper dbHelperFavourit;
    private CustomViewPageAdapter customViewPageAdapter;
    private static int position;
    private static final String TAG = ContactViewPagerActivity.class.getSimpleName();
    private String selectScreen;


    int deviceHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_view_pager);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.window_title);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        deviceHeight = metrics.widthPixels;

        initilization();
        enablePermissions();
    }

    private void enablePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        }

    }

    private void initilization() {
        getBundle();
        tvContactViewPagerEmpDetails = (TextView) findViewById(R.id.tv_contactviewpager_empdetails);
        tvContactViewPagername = (TextView) findViewById(R.id.tv_contactviewpager_contactname);
        tvWindowTitleHeader = (TextView) findViewById(R.id.tv_windowtitle_Title);
        imageView = (ImageView) findViewById(R.id.iv_cotactviewpager_icfavourit);
        ivContactViewPagerCall = (ImageView) findViewById(R.id.iv_contactviewpage_call);
        ivContactViewPagerSms = (ImageView) findViewById(R.id.iv_contactviewpager_sms);
        ivContactViewPagerEmail = (ImageView) findViewById(R.id.iv_contactviewpage_email);
        ivWindowTitleMenu = (ImageView) findViewById(R.id.iv_contactviewpager_back);
        ivWindowTitleCreateContact = (ImageView) findViewById(R.id.iv_contactviewpager_delete);
        vpContactViewPager = (ViewPager) findViewById(R.id.vp_contactviewpager_imagesilde);
        llContactViewPagerFavourit = (LinearLayout) findViewById(R.id.ll_contactviewpager_favourit);
        llContactViewPagerContactInfo = (LinearLayout) findViewById(R.id.ll_contactviewpager_contactinfo);
        dbHelperFavourit = new DataBaseHelper(getApplicationContext());
        setFavouritIcon(ContactViewPagerActivity.position);
        customViewPageAdapter = new CustomViewPageAdapter();
        vpContactViewPager.setAdapter(customViewPageAdapter);
        vpContactViewPager.setCurrentItem(position);
        vpContactViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setDetailsOnCreate(position);
                ContactViewPagerActivity.position = position;
                setFavouritIcon(ContactViewPagerActivity.position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Log.i(TAG, "initilization: pageTitle");
        setDetailsOnCreate(position);
        llContactViewPagerFavourit.setOnClickListener(this);
        llContactViewPagerContactInfo.setOnClickListener(this);
        ivContactViewPagerCall.setOnClickListener(this);
        ivContactViewPagerSms.setOnClickListener(this);
        ivContactViewPagerEmail.setOnClickListener(this);
        ivWindowTitleMenu.setOnClickListener(this);
        ivWindowTitleCreateContact.setOnClickListener(this);
    }

    private void setFavouritIcon(int position) {
        String checkFavourit = contacts.get(position).getFavourit();
        if (checkFavourit.equals("NF")) {
            imageView.setImageResource(R.drawable.ic_contactviewpager_favourit_64);
        } else {
            imageView.setImageResource(R.drawable.ic_contactviewpager_favourit_yellow_64);
        }
    }

    private void setDetailsOnCreate(int position) {
        String text = getString(R.string.contactviewpager_tv_iworkas) + " "
                + "<font COLOR=\'#00ADED\'>" + contacts.get(position).getWorking() + "</font>"
                + getString(R.string.contactviewpager_tv_andilive)
                + getString(R.string.contactviewpager_tv_cama)
                + "<font COLOR=\'#00ADED\'>" + contacts.get(position).getCity() + "</font>"
                + getString(R.string.contactviewpager_tv_cama)
                + "<font COLOR=\'#00ADED\'>" + contacts.get(position).getState() + "</font>"
                + getString(R.string.contactviewpager_tv_cama)
                + "<font COLOR=\'#00ADED\'>" + contacts.get(position).getCountry() + "</font>";
        tvContactViewPagerEmpDetails.setText(Html.fromHtml(text));
        tvContactViewPagername.setText(contacts.get(position).getName());
//        tvWindowTitleHeader.setText(contacts.get(position).getName());
        Toast.makeText(ContactViewPagerActivity.this, "getID->" + contacts.get(position).getId(), Toast.LENGTH_SHORT).show();
    }

    private void getData(int position) {
        tvContactViewPagerEmpDetails.setText("I work as " + contacts.get(position).getProfession()
                + " and live in" + contacts.get(position).getCity()
                + "," + contacts.get(position).getState()
                + "," + contacts.get(position).getCountry());


    }

    public void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Log.i(TAG, "getBundle: bundleoption->" + bundle);
        contacts = (ArrayList<Data>) bundle.getSerializable(Utils.CONTACTS);
        position = bundle.getInt(Utils.POSITION);
        selectScreen = bundle.getString(Utils.FROMSCREEN);
        Log.e(TAG, "getBundle: contacts->" + contacts.get(position).getEmail() + "::Postion->" + position);
        Log.e(TAG, "getBundle: favourit" + contacts.get(position).getFavourit());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_contactviewpager_favourit:
                imageView.setImageResource(R.drawable.ic_contactviewpager_favourit_yellow_64);
                int effectedRows = dbHelperFavourit.updateFavouritContact(Integer.valueOf(contacts.get(position).getId()));
                if (effectedRows == 1) {
                    Toast.makeText(ContactViewPagerActivity.this, "Contact updated", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_contactviewpage_call:
                Toast.makeText(ContactViewPagerActivity.this, "Call ", Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + contacts.get(ContactViewPagerActivity.position).getPhonenumber()));
                startActivity(callIntent);
                break;
            case R.id.iv_contactviewpager_sms:
                Toast.makeText(ContactViewPagerActivity.this, "SMS", Toast.LENGTH_SHORT).show();
//                SmsManager.getDefault().sendTextMessage(contacts.get(ContactViewPagerActivity.position).getPhonenumber(),null,"hello",null,null);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:" + contacts.get(ContactViewPagerActivity.position).getPhonenumber()));
                startActivity(sendIntent);
//                startActivity(new Intent(Intent.ACTION_SEND).putExtra());
                break;
            case R.id.iv_contactviewpage_email:
                Toast.makeText(ContactViewPagerActivity.this, "EMAIL", Toast.LENGTH_SHORT).show();
                Intent sendEmail = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + contacts.get(ContactViewPagerActivity.position).getEmail()));
                startActivity(sendEmail);
                break;
            case R.id.iv_contactviewpager_back:

                finish();
                break;
            case R.id.iv_contactviewpager_delete:
                Toast.makeText(ContactViewPagerActivity.this, "Delete  clicked", Toast.LENGTH_SHORT)
                        .show();
                String contactId = contacts.get(ContactViewPagerActivity.position).getId();
                if (selectScreen.equals("Favourite")) {

                    int noRowsUpdate = dbHelperFavourit.unFavouritContact(contactId);
                    if (noRowsUpdate == 1) {

                        contacts.remove(ContactViewPagerActivity.position);
                        customViewPageAdapter.notifyDataSetChanged();
                        vpContactViewPager.setAdapter(customViewPageAdapter);
                        ContactViewPagerActivity.position = 0;
                        if (contacts.isEmpty()) {
                            Toast.makeText(ContactViewPagerActivity.this, "all contacts deleted", Toast.LENGTH_SHORT).show();
                            ivWindowTitleCreateContact.setVisibility(View.INVISIBLE);
                            tvContactViewPagerEmpDetails.setText("");
                            ivContactViewPagerEmail.setVisibility(View.INVISIBLE);
                            ivContactViewPagerSms.setVisibility(View.INVISIBLE);
                            ivContactViewPagerCall.setVisibility(View.INVISIBLE);
                        } else {
                            setDetailsOnCreate(ContactViewPagerActivity.position);
                        }
                    } else {
                        Toast.makeText(ContactViewPagerActivity.this, "Contact didn't deleted", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(selectScreen.equals("Login"))
                {
                    Toast.makeText(ContactViewPagerActivity.this, "FromLogin deleted", Toast.LENGTH_SHORT).show();
                    int noRowsUpdate = dbHelperFavourit.deletContact(contactId);
                    if(noRowsUpdate==1)
                    {
                        contacts.remove(ContactViewPagerActivity.position);
                        customViewPageAdapter.notifyDataSetChanged();
                        vpContactViewPager.setAdapter(customViewPageAdapter);
                        ContactViewPagerActivity.position = 0;
                        if (contacts.isEmpty()) {
                            Toast.makeText(ContactViewPagerActivity.this, "all contacts deleted", Toast.LENGTH_SHORT).show();
                            ivWindowTitleCreateContact.setVisibility(View.INVISIBLE);
                            tvContactViewPagerEmpDetails.setText("");
                            ivContactViewPagerEmail.setVisibility(View.INVISIBLE);
                            ivContactViewPagerSms.setVisibility(View.INVISIBLE);
                            ivContactViewPagerCall.setVisibility(View.INVISIBLE);
                        } else {
                            setDetailsOnCreate(ContactViewPagerActivity.position);
                        }
                    }
                    else {
                        Toast.makeText(ContactViewPagerActivity.this, "Contact didn't deleted", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case R.id.ll_contactviewpager_contactinfo:
                Toast.makeText(ContactViewPagerActivity.this, "ContactInfo clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ContactInfoActivity.class)
                        .putExtra(Utils.CONTACTID
                                , contacts.get(ContactViewPagerActivity.position).getId()));
                break;


        }
    }




    /*
* class CustomVIewPageAdapter
*
* setImages for the viewpager by using this adapter
* */

    private class CustomViewPageAdapter extends PagerAdapter {
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            Log.i(TAG, "getCount: " + contacts.size());
            return contacts.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = layoutInflater.inflate(R.layout.viewpager_item, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pageritem);
            Log.i(TAG, "instantiateItem: imagePath" + contacts.get(position).getImagePath());
            File file = new File(contacts.get(position).getImagePath());
            if (file.exists()) {
                Log.e(TAG, "instantiateItem: exixts of file->true::" + file.getAbsolutePath());
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                int bitmapWidth = bitmap.getWidth();
                int bitmapHeight = bitmap.getHeight();

                Log.e(TAG, "instantiateItem: bitmapWidth" + bitmapWidth + " bitmapHeight->" + bitmapHeight);

                double ratio = Double.parseDouble("" + bitmapHeight) / Double.parseDouble("" + bitmapWidth);
                double ht = ratio * deviceHeight;

                Log.e(TAG, "instantiateItem: ratio" + ratio + " ht->" + ht);

                int imageViewWidth = (bitmapHeight / bitmapWidth) * deviceHeight;
                Log.e(TAG, "instantiateItem: height" + deviceHeight + " Width->" + imageViewWidth);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(deviceHeight, (int) ht);
                imageView.setLayoutParams(params);

                imageView.setImageBitmap(bitmap);
            } else {
                Log.e(TAG, "instantiateItem: exixts of file->false");
            }
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


}

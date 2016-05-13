package com.example.kiran.mybuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kiran.mybuddy.R;
import com.example.kiran.mybuddy.Utils.CircleTransForm;
import com.example.kiran.mybuddy.Utils.Data;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kiran on 25-04-2016.
 */
public class ContactListAdapter extends BaseAdapter {
    private ArrayList<Data> contacts;
    private Data data;
    private Context context;
    private HolderClass holderClass;
    LayoutInflater layoutInflater;

    public ContactListAdapter(Context context, ArrayList cotacts) {
        this.context = context;
        this.contacts = cotacts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Data getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        data = contacts.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_contactlist, null);
            holderClass=new HolderClass(convertView);
            /*BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig=Bitmap.Config.ARGB_8888;
            Bitmap bitmap=BitmapFactory.decodeFile(data.getImagePath(),options);*/
            Picasso.with(context).load(new File(data.getImagePath())).transform(new CircleTransForm()).resize(96,96).into(holderClass.ivprofilepick);
//            holderClass.ivprofilepick.setImageBitmap(bitmap);
            holderClass.tvPhoneNumber.setText(data.getPhonenumber());
            holderClass.tvName.setText(data.getName());
            convertView.setTag(holderClass);
        }
        else {
            holderClass=(HolderClass)convertView.getTag();
        }

        return convertView;
    }

    class HolderClass {
        ImageView ivprofilepick;
        TextView tvName;
        TextView tvPhoneNumber;

        public HolderClass(View convertView) {
            ivprofilepick = (ImageView) convertView.findViewById(R.id.iv_row_contacticon);
            tvName = (TextView) convertView.findViewById(R.id.tv_row_contactname);
            tvPhoneNumber = (TextView) convertView.findViewById(R.id.tv_row_contactnumber);

        }
    }
}

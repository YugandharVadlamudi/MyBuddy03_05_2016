package com.example.runtimepermissionapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button button;
    private Button buttonCallAnotherActivity;
    private int changePosition;
    private String TAG = ViewPagerActivity.class.getSimpleName();
    ArrayList<Integer> viewpagerItemsCheck=new ArrayList<>();

    int[] viewpagerItems = {R.drawable.contactviewpager_five
            , R.drawable.contactviewpager_four
            , R.drawable.contactviewpager_one
            , R.drawable.contactviewpager_three
            , R.drawable.contactviewpager_two
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_FIRST_USER)
        {
            Toast.makeText(ViewPagerActivity.this, "the data"+data.getData() , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        viewPager = (ViewPager) findViewById(R.id.vp_viewpager_demo);
        button=(Button)findViewById(R.id.bt_viewpager_delete);
        buttonCallAnotherActivity=(Button)findViewById(R.id.bt_viewpager_callanother_activity);
        viewpagerItemsCheck.add(R.drawable.contactviewpager_five);
        viewpagerItemsCheck.add(R.drawable.contactviewpager_four);
        viewpagerItemsCheck.add(R.drawable.contactviewpager_one);
        viewpagerItemsCheck.add(R.drawable.contactviewpager_two);
        viewpagerItemsCheck.add(R.drawable.contactviewpager_three);
        final CustomViewPageAdapter customViewPageAdapter = new CustomViewPageAdapter();
        viewPager.setAdapter(customViewPageAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewPagerActivity.this, "Delete Called", Toast.LENGTH_SHORT).show();
            viewpagerItemsCheck.remove(changePosition);
                Log.e(TAG, "onClick: sizeof adapter"+viewpagerItemsCheck.size() );
                customViewPageAdapter.notifyDataSetChanged();
                viewPager.setAdapter(customViewPageAdapter);
                changePosition=0;
                if(viewpagerItemsCheck.isEmpty())
                {
                    button.setVisibility(View.INVISIBLE);
                    Toast.makeText(ViewPagerActivity.this, "Every Contact Deleted", Toast.LENGTH_SHORT).show();
                }
                /*if(viewpagerItemsCheck.isEmpty())
                {

                    Toast.makeText(ViewPagerActivity.this, "Every thing deleted", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else
                {

                    customViewPageAdapter.notifyDataSetChanged();
                    viewPager.setAdapter(customViewPageAdapter);
                }*/
            }
        });
        buttonCallAnotherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(),ResultSendActivity.class),1);
            }
        });
       viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               Log.d(TAG, "onPageSelected: position->"+position);
            changePosition=position;
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });


    }

    private class CustomViewPageAdapter extends PagerAdapter {
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            Log.i(TAG, "getCount: " + viewpagerItemsCheck.size());
            return viewpagerItemsCheck.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = layoutInflater.inflate(R.layout.viewpager_item, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pageritem);
//            Log.i(TAG, "instantiateItem: imagePath" + contacts.get(position).getImagePath());
            /*File file = new File(viewpagerItems[position]);
            if (file.exists()) {
                Log.e(TAG, "instantiateItem: exixts of file->true::" + file.getAbsolutePath());
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                Log.e(TAG, "instantiateItem: Bitmap->"+bitmap);
                imageView.setImageBitmap(bitmap);
            } else {
                Log.e(TAG, "instantiateItem: exixts of file->false");
            }*/
            imageView.setImageResource(viewpagerItemsCheck.get(position));
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }


}

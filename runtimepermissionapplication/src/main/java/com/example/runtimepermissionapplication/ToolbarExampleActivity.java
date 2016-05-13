package com.example.runtimepermissionapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class ToolbarExampleActivity extends Activity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_example);
        toolbar=(Toolbar)findViewById(R.id.tb_toolbarexample_example);
        toolbar.setTitle("hello");
        toolbar.setLogo(R.drawable.ic_contacts_menu_64);
        toolbar.inflateMenu(R.menu.menuitems);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(ToolbarExampleActivity.this, "item is->"+item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}

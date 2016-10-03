package com.srepollock.goodgists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MyGists extends AppCompatActivity {

    private Menu pMenu;
    private boolean starred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gists);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mygists_menu, menu);
        pMenu = menu;
        starred = false;

        return super.onCreateOptionsMenu(menu);
    }

    public void starItem(MenuItem item) {
        if (starred) {
            starred = false;
            pMenu.getItem(0).setIcon(getResources()
                    .getDrawable(R.drawable.ic_star_white_24dp));
        } else {
            starred = true;
            pMenu.getItem(0).setIcon(getResources()
                    .getDrawable(R.drawable.ic_star_border_white_24dp));
        }
    }
}

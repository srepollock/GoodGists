package com.srepollock.goodgists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
    }

    public void searchScreen(View view) {
        // Intent for the activity to open when user selects the notification
        Intent detailsIntent = new Intent(this, SearchScreen.class);
        startActivity(detailsIntent);
    }

    public void myGistsScreen(View view) {
        Intent detailsIntent = new Intent(this, MyGists.class);
        startActivity(detailsIntent);
    }
}

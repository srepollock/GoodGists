package com.srepollock.goodgists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MenuScreen extends AppCompatActivity {

    private GitHubController ghc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        String oauthkey =
                GitHubController.getOAuthKey(getApplicationContext(),
                        getString(R.string.oauthkeyfile));
        ghc = new GitHubController();
        if (oauthkey != null){
            if(ghc.connect(oauthkey)) {
                loggedInMenu();
            }
            loggedOutMenu();
        } else {
            loggedOutMenu();
        }
    }

    public void searchScreen(View view) {
        // Intent for the activity to open when user selects the notification
        Intent detailsIntent = new Intent(this, SearchScreen.class);
        Bundle b = new Bundle();
        b.putParcelable("ghc", ghc);
        detailsIntent.putExtras(b);
        startActivity(detailsIntent);
    }

    public void myGistsScreen(View view) {
        Intent detailsIntent = new Intent(this, MyGists.class);
        Bundle b = new Bundle();
        b.putParcelable("ghc", ghc);
        detailsIntent.putExtras(b);
        startActivity(detailsIntent);
    }

    public void starredScreen(View view) {
        Intent detailsIntent = new Intent(this, StarredScreen.class);
        Bundle b = new Bundle();
        b.putParcelable("ghc", ghc);
        detailsIntent.putExtras(b);
        startActivity(detailsIntent);
    }

    public void accountScreen(View view) {
        Intent detailsIntent = new Intent(this, AccountScreen.class);
        Bundle b = new Bundle();
        b.putParcelable("ghc", ghc);
        detailsIntent.putExtras(b);
        startActivity(detailsIntent);
    }

    public void logoutScreen(View view) {
        Intent detailsIntent = new Intent(this, LogoutScreen.class);
        Bundle b = new Bundle();
        b.putParcelable("ghc", ghc);
        detailsIntent.putExtras(b);
        startActivity(detailsIntent);
    }

    public void loginScreen(View view) {
        Intent detailsIntent = new Intent(this, LoginScreen.class);
        Bundle b = new Bundle();
        b.putParcelable("ghc", ghc);
        detailsIntent.putExtras(b);
        startActivity(detailsIntent);
    }
    // TODO Hide login
    private void loggedInMenu() {

    }
    // TODO Visible only logged in stuff
    private void loggedOutMenu() {

    }
}

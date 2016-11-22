package com.srepollock.goodgists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuScreen extends AppCompatActivity {

    private GitHubController ghc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        checkLoggedIn();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        checkLoggedInResume();
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

    private void checkLoggedIn() {
        String oauthkey = GitHubController.getOAuthKey(getApplicationContext(),
                        getString(R.string.oauthkeyfile));
        ghc = new GitHubController();
        if (oauthkey != null && ghc.connect(oauthkey)){
            loggedInMenuToast();
            loggedInMenuVisibility();
        } else {
            loggedOutMenuToast();
            loggedOutMenuVisibility();
            ghc = new GitHubController();
        }
    }

    private void checkLoggedInResume() {
        String oauthkey = GitHubController.getOAuthKey(getApplicationContext(),
                getString(R.string.oauthkeyfile));
        ghc = new GitHubController();
        if (oauthkey != null && ghc.connect(oauthkey)){
            loggedInMenuVisibility();
        } else {
            loggedOutMenuVisibility();
            ghc = new GitHubController();
        }
    }

    private void loggedInMenuToast() {
        Toast.makeText(getApplicationContext(),
                R.string.logged_in_toast,
                Toast.LENGTH_SHORT).show();
    }

    // TODO Hide login
    private void loggedInMenuVisibility() {
        findViewById(R.id.menuLoginButton).setVisibility(View.GONE);
        findViewById(R.id.menuGistRow).setVisibility(View.VISIBLE);
        findViewById(R.id.menuStarRow).setVisibility(View.VISIBLE);
        findViewById(R.id.menuAccountRow).setVisibility(View.VISIBLE);
        findViewById(R.id.menuLogoutRow).setVisibility(View.VISIBLE);
    }

    private void loggedOutMenuToast() {
        Toast.makeText(getApplicationContext(),
                R.string.logged_out_toast,
                Toast.LENGTH_SHORT).show();
    }

    // TODO Visible only logged in stuff
    private void loggedOutMenuVisibility() {
        findViewById(R.id.menuGistRow).setVisibility(View.GONE);
        findViewById(R.id.menuStarRow).setVisibility(View.GONE);
        findViewById(R.id.menuAccountRow).setVisibility(View.GONE);
        findViewById(R.id.menuLogoutRow).setVisibility(View.GONE);
        findViewById(R.id.menuLoginButton).setVisibility(View.VISIBLE);
    }
}

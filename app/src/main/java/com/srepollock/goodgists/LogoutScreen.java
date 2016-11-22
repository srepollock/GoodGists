package com.srepollock.goodgists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;

public class LogoutScreen extends AppCompatActivity {

    // TODO Enter password to logout

    GitHubController ghc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_screen);
        checkLoggedIn();
        EditText username = (EditText)findViewById(R.id.gitHubUsername);
        try {
            username.setText(ghc.getMyself().getLogin());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent data = new Intent();
        Bundle b = new Bundle();
        b.putParcelable("ghc", new GitHubController());
        data.putExtras(b);
        setResult(RESULT_OK, data);
    }

    public void logoutButton(View view) {
        EditText username = (EditText)findViewById(R.id.gitHubUsername);
        EditText password = (EditText)findViewById(R.id.gitHubPassword);
        if (password.getText() != null) {
            ghc.connect(username.getText().toString(), password.getText().toString());
            deleteFile();
        }
        finish();
    }

    private boolean deleteFile() {
        File f = new File(getApplicationContext().getFilesDir(), getString(R.string.oauthkeyfile));
        return f.delete();
    }

    private void checkLoggedIn() {
        String oauthkey = GitHubController.getOAuthKey(getApplicationContext(),
                getString(R.string.oauthkeyfile));
        ghc = new GitHubController();
        ghc.connect(oauthkey);
    }
}

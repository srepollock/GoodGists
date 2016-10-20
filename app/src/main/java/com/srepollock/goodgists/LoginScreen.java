package com.srepollock.goodgists;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void loginToGitHub(View view) {
        // Get the value of login and check valid username/email
        EditText usernameLogin = (EditText) findViewById(R.id.usernameLogin);
        EditText password = (EditText) findViewById(R.id.password);
        if(checkEmail(usernameLogin.getText().toString())
                && checkPassword(password.getText().toString())) {
            // Get
            finish(); // End activity
        } else {
            // Display dialog "Invalid username or email"
            invalidUserEmail();
        }
    }

    private boolean checkEmail(String u) {
        if (u != null)
            return u.contains("@") && u.contains(".");
        return false;
    }

    private boolean checkPassword(String p) {
        if (p != null)
            return true;
        return false;
    }

    private void invalidUserEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.invalidUserEmail);
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

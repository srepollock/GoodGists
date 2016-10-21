package com.srepollock.goodgists;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    // TODO Temporary, remove this for future use and use parcelable
    GitHubController gitHubController = new GitHubController();

    private boolean OAuthResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        // TODO Retrieve parcelable
    }

    public void loginToGitHub(View view) {
        String username = editTextAsString((EditText) findViewById(R.id.usernameLogin));
        String password = editTextAsString((EditText) findViewById(R.id.password));
        if(!checkUser(username) && !checkPassword(password)) {
            boolean connected = gitHubController.connect(username, password);
            if (connected) {
                createOAuthTokenDialog();
            } else if (!connected){
                cannotConnectDialog();
            }
        } else {
            // Display dialog "Invalid username or email"
            noUserDialog();
        }
    }

    private String editTextAsString(EditText editText) {
        return editText.getText().toString();
    }

    private boolean checkUser(String u) {
        return u.matches("");
    }

    private boolean checkPassword(String p) {
        return p.matches("");
    }

    private void noUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.noUser);
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void noPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.noPassword);
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void didNotCreateOAuthTokenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.didNotCreateOAuthToken);
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void cannotConnectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.cannotConnect);
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean createOAuthTokenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Chain together various setter methods to set the dialog characteristics
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
              switch (which) {
                  case DialogInterface.BUTTON_POSITIVE:
                      OAuthResult = true;
                      break;
                  case DialogInterface.BUTTON_NEGATIVE:
                      OAuthResult = false;
                      break;
              }
          }
        };
        builder.setMessage(R.string.cannotConnect)
                .setPositiveButton(R.string.yes, dialogClickListener)
                .setNegativeButton(R.string.no, dialogClickListener);
        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
        return OAuthResult;
    }
}

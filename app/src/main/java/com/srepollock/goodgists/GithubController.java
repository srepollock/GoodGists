package com.srepollock.goodgists;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.kohsuke.github.GHAuthorization;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Spencer on 2016-10-19.
 */

public class GitHubController implements Parcelable {
    private int mData;
    private static final ArrayList<String> scope =
            new ArrayList<>(Arrays.asList(
                    GHAuthorization.GIST,
                    GHAuthorization.NOTIFICATIONS,
                    GHAuthorization.PUBLIC_REPO,
                    GHAuthorization.USER,
                    GHAuthorization.USER_FOLLOW
                    ));
    private GitHub gitHub;

    // Parcelable (refer http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents)
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<GitHubController> CREATOR =
            new Parcelable.Creator<GitHubController>() {
        public GitHubController createFromParcel(Parcel in) {
            return new GitHubController(in);
        }

        public GitHubController[] newArray(int size) {
            return new GitHubController[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private GitHubController(Parcel in) {
        mData = in.readInt();
    }

    public GitHubController() {

    }

    public boolean connect(String login, String password) {
        try {
            gitHub = GitHub.connectUsingPassword(login, password);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    public void getOAuthKeyFile(Context appContext, String filename) {

    }

    public void setOAuthKeyFile(Context appContext, String filename) {
        File f = new File(appContext.getFilesDir(), filename);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = appContext.openFileOutput(filename, Context.MODE_PRIVATE);
//            fileOutputStream.write();
//            fileOutputStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private String createOAuthKey(Context appContext) {
        String OAuthKey;
        GHAuthorization ghAuthorization;
        try {
            ghAuthorization = gitHub.createToken(scope, appContext.getString(R.string.app_name), null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return null;
    }
}


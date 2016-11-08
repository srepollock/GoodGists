package com.srepollock.goodgists;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.kohsuke.github.GHAuthorization;
import org.kohsuke.github.GHGist;
import org.kohsuke.github.GHGistFile;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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
    // TODO Connect methods need to be done in an async task 
    public boolean connect(String login, String password) {
        try {
            gitHub = GitHub.connectUsingPassword(login, password);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean connect(String oauth) {
        try {
            gitHub = GitHub.connectUsingOAuth(oauth);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getOAuthKey(Context appContext, String fileName) {
        String OAuth = null;
        BufferedReader reader;
        try {
            String filepathname = appContext.getFilesDir().getPath() + File.separator + fileName;
            reader = new BufferedReader(
                    new FileReader(filepathname));
            OAuth = "";
            String line;
            while ((line = reader.readLine()) != null) { OAuth += line; }
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return OAuth;
    }

    // TODO Fix permissions
    public File setOAuthKeyFile(Context appContext, String fileName) throws IOException {
        FileOutputStream outputStream;
        File f = new File(appContext.getFilesDir(), fileName);
        outputStream = appContext.openFileOutput(fileName, Context.MODE_APPEND);
        outputStream.write(createOAuthKey(appContext).getBytes());
        outputStream.close();
        return f;
    }

    private String createOAuthKey(Context appContext) {
        String OAuthToken = null;
        try {
            OAuthToken = gitHub.createToken(scope, appContext.getString(R.string.app_name), null).getToken();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return OAuthToken;
    }

    public GHMyself getMyself() throws IOException {
        return gitHub.getMyself();
    }

    public GHGist getGist(String gistID) throws IOException {
        return gitHub.getGist(gistID);
    }

    public GHGistFile getGistFile(GHGist gist, String fileName) {
        return gist.getFile(fileName);
    }
}


package com.srepollock.goodgists;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

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
import java.util.concurrent.ExecutionException;

/**
 * Created by Spencer on 2016-10-19.
 */

public class GitHubController implements Parcelable {
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
        // out.write(/*data*/);
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
        /*private data*/// = in.read();
    }

    public GitHubController() {

    }

    private class ConnectToGitHubPass extends AsyncTask<Pair<String, String>, Void, Boolean> {
        protected Boolean doInBackground(Pair<String, String>... data) {
            try {
                gitHub = GitHub.connectUsingPassword(data[0].first, data[0].second);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private class ConnectToGitHubOAuth extends AsyncTask<String, Void, Boolean> {
        protected Boolean doInBackground(String... oauth) {
            try {
                gitHub = GitHub.connectUsingOAuth(oauth[0]);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private class CreateOAuthKey extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... app_name) {
            String OAuthToken = null;
            try {
                OAuthToken = gitHub.createToken(scope, app_name[0], null).getToken();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return OAuthToken;
        }
    }

    private class GetMyself extends AsyncTask<Void, Void, GHMyself> {
        protected GHMyself doInBackground(Void... v) {
            try {
                return gitHub.getMyself();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            }
        }
    }

    public boolean connect(String login, String password) {
        try {
            return new ConnectToGitHubPass().execute(new Pair<>(login, password)).get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return false;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return false;
        }
    }

    public boolean connect(String oauth) {
        try {
            return new ConnectToGitHubOAuth().execute(oauth).get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return false;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return false;
        }
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

    public File setOAuthKeyFile(Context appContext, String fileName) {
        FileOutputStream outputStream;
        File f = new File(appContext.getFilesDir(), fileName);
        try {
            outputStream = appContext.openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(createOAuthKey(appContext).getBytes());
            outputStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return f;
    }

    private String createOAuthKey(Context appContext) {
        try {
            return new CreateOAuthKey().execute(appContext.getString(R.string.app_name)).get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return null;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return null;
        }
    }

    public GHMyself getMyself() throws IOException {
        try {
            return new GetMyself().execute().get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return null;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return null;
        }
    }

    public GHGist getGist(String gistID) throws IOException { return gitHub.getGist(gistID); }

    public GHGistFile getGistFile(GHGist gist, String fileName) {
        return gist.getFile(fileName);
    }
}


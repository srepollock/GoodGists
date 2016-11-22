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

    private class GetOAuthKey extends AsyncTask<Context, Void, String> {
        protected String doInBackground(Context... c) {
            String OAuthToken = null;
            try {
                OAuthToken = gitHub.createOrGetAuth(
                        c[0].getString(R.string.clientid),
                        c[0].getString(R.string.clientsecret),
                        scope,
                        c[0].getString(R.string.app_name),
                        null
                ).getToken();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return OAuthToken;
        }
    }

    private class DeleteAuth extends AsyncTask<GHMyself, Void, Boolean> {
        protected Boolean doInBackground(GHMyself... ghm) {
            try {
                gitHub.deleteAuth(ghm[0].getId());
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return false;
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

    private class GetFollowers extends AsyncTask<Void, Void, Integer> {
        protected Integer doInBackground(Void... v) {
            try {
                return gitHub.getMyself().getFollowersCount();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            }
        }
    }

    private class GetFollowing extends AsyncTask<Void, Void, Integer> {
        protected Integer doInBackground(Void... v) {
            try {
                return gitHub.getMyself().getFollowingCount();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            }
        }
    }

    private class GetGist extends AsyncTask<String, Void, GHGist> {
        protected GHGist doInBackground(String... gistID) {
            try {
                return gitHub.getGist(gistID[0]);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return null;
            }
        }
    }

    private class GetGistFile extends AsyncTask<Pair<GHGist, String>, Void, GHGistFile> {
        protected GHGistFile doInBackground(Pair<GHGist, String>... data) {
            return data[0].first.getFile(data[0].second);
        }
    }

    // TODO Search/Get Search results

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
            return new GetOAuthKey().execute(appContext).get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return null;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return null;
        }
    }

    public boolean deleteOAuth() {
        try {
            GHMyself ghm = new GetMyself().execute().get();
            return new DeleteAuth().execute(ghm).get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (ExecutionException ee) {
            ee.printStackTrace();
        }
        return true;
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

    public GHGist getGist(String gistID) throws IOException {
        try {
            return new GetGist().execute(gistID).get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return null;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return null;
        }
    }

    public GHGistFile getGistFile(GHGist gist, String fileName) {
        try {
            return new GetGistFile().execute(new Pair<>(gist, fileName)).get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return null;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return null;
        }
    }

    public int getFollowerCount() {
        try {
            return new GetFollowers().execute().get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return 0;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return 0;
        }
    }

    public int getFollowingCount() {
        try {
            return new GetFollowing().execute().get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
            return 0;
        } catch (ExecutionException ee) {
            ee.printStackTrace();
            return 0;
        }
    }
}


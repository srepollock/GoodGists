package com.srepollock.goodgists;

import org.junit.Assert;
import org.junit.Test;

import static android.test.MoreAsserts.assertNotEqual;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Spencer on 2016-11-07.
 */

public class GitHubControllerUnitTesting {
    private GitHubController ghc;

    /**
     * Quick method just to sign in repeatedly for individual tests
     */
    private boolean signin() {
        ghc = new GitHubController();
        return ghc.connect("srsavage", "$ecure5ystem"); // TODO Enter password for testing
    }
    @Test
    public void githubConnection() throws Exception {
        assertTrue("Connection to GitHub",
                signin());
    }
    @Test
    public void getProfile() throws Exception {
        signin();
        Assert.assertNotNull("GHMyself not null",
                ghc.getMyself());
    }
    @Test
    public void getProfileName() throws Exception {
        signin();
        Assert.assertEquals("Get name of srsavage",
                ghc.getMyself().getName(),
                "Spencer");
    }
    @Test
    public void getProfileEmail() throws Exception {
        signin();
        Assert.assertNull("Get email of srsavage",
                ghc.getMyself().getEmail()); // because not public
    }
    // TODO Create file/read token (permissions error)
//    @Test
//    public void createAndSaveToken() throws Exception {
//        signin();
//        Assert.assertNotNull("Create token not null",
//                ghc.setOAuthKeyFile(InstrumentationRegistry.getInstrumentation().getContext(), "oauthkey").toString());
//    }
//    @Test
//    public void getToken() throws Exception {
//        signin();
//        ghc.setOAuthKeyFile(InstrumentationRegistry.getInstrumentation().getContext(), "oauthkey");
//        Assert.assertNotNull("Read token not null",
//                ghc.getOAuthKey(InstrumentationRegistry.getInstrumentation().getContext(), "oauthkey"));
//    }
    @Test
    public void getGistNotNull() throws Exception {
        signin();
        Assert.assertNotNull("Get Gist not null",
                ghc.getGist("71e72bd0388c1b9ce6dbe9fa4d5c53d3"));
    }
    @Test
    public void getGistFileNotNull() throws Exception {
        signin();
        assertNotNull("Get Gist from user srsavage",
                ghc.getGistFile(ghc.getGist("71e72bd0388c1b9ce6dbe9fa4d5c53d3"),
                        "helloworld.md")); // contents
    }
    @Test
    public void getGistFileContent() throws Exception {
        signin();
        assertEquals("Gist content srsavage helloworld.md file",
                ghc.getGistFile(
                        ghc.getGist("71e72bd0388c1b9ce6dbe9fa4d5c53d3"), "helloworld.md")
                        .getContent(),
                "# Hello World");
    }
    @Test
    public void getFollowerNumber() throws Exception {
        signin();
        assertNotEqual("Follower number not -1", -1, ghc.getMyself().getFollowersCount());
    }
    // TODO Test follow user
    @Test
    public void getFollowingNumber() throws Exception {
        signin();
        assertEquals("Follower number is 1", 1, ghc.getMyself().getFollowingCount());
    }
    // TODO Test unfollow user
}

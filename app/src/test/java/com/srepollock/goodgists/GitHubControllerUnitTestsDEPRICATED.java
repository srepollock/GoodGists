package com.srepollock.goodgists;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Spencer on 2016-10-19.
 */

public class GitHubControllerUnitTestsDEPRICATED {
    private GitHubController ghc;

    /**
     * Quick method just to sign in repeatedly for individual tests
     */
    private void signin() {
        ghc = new GitHubController();
        ghc.connect("srsavage", "$ecure5ystem");
    }
    @Test
    public void githubConnection() throws Exception {
        ghc = new GitHubController();
        assertTrue("Connection to GitHub",
                ghc.connect("srsavage",
                "$ecure5ystem"));
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
    // token creation
    // TODO Create token tests
    @Test
    public void createToken() throws Exception {
        signin();
//        Assert.assertNotNull("Create token not null",
//                ghc.setOAuthKeyFile(InstrumentationRegistry.getInstrumentation().getContext().getDir("tmp1", 0), "ouathkey"));
    }
    @Test
    public void getToken() throws Exception {

    }
    @Test
    public void saveToken() throws Exception {

    }
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
}

package com.srepollock.goodgists;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Spencer on 2016-09-27.
 */

public class UITest {
    @Rule
    public ActivityTestRule<MenuScreen> menuActivityTestRule = new ActivityTestRule<>(MenuScreen.class);

    @Test
    public void menuScreen_SearchClick(){
        onView(withId(R.id.menuSearchButton)).perform(click());
    }

    @Test
    public void menuScreen_MyGistsClick() {
        onView(withId(R.id.menuGistButton)).perform(click());
    }

    @Test
    public void menuScreen_StarredClick() {
        onView(withId(R.id.menuStarButton)).perform(click());
    }

    @Test
    public void menuScreen_AccountClick() {
        onView(withId(R.id.menuAccountButton)).perform(click());
    }

    @Test
    public void menuScreen_LogoutClick() {
        onView(withId(R.id.menuLogoutButton)).perform(click());
    }

    @Test
    public void menuScreen_LoginClick() {
        onView(withId(R.id.menuLoginButton)).perform(click());
    }
}

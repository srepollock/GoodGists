package com.srepollock.goodgists;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Matcher;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Spencer on 2016-09-27.
 */

public class UITest {
    @Rule
    public ActivityTestRule<MenuScreen> menuActivityTestRule = new ActivityTestRule<>(MenuScreen.class);

    @Test
    public void menuScreen_SearchClick() {
        onView(withId(R.id.menuSearchButton))
                .perform(click());
        onView(withId(R.id.activity_search_screen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_SearchClick_Back() {
        // Press 'Search' button
        onView(withId(R.id.menuSearchButton)).perform(click());
        // Press [Back] button
        onView(withId(R.id.activity_search_screen)).perform(pressBack());
        // Is the 'Menu Screen' displayed?
        onView(withId(R.id.activity_menu_screen)).check(matches(isDisplayed()));
    }

    @Test
    public void searchScreen_Input() {
        onView(withId(R.id.menuSearchButton))
                .perform(click());
        onView(withId(R.id.gist_search))
                .perform(click());
        onView(isAssignableFrom(EditText.class))
                .perform(setText("Test"),
                        pressKey(KeyEvent.KEYCODE_ENTER));
    }

    @Test
    public void menuScreen_MyGistsClick() {
        onView(withId(R.id.menuGistButton))
                .perform(click());
        onView(withId(R.id.activity_my_gists))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_MyGistsClick_Back() {
        onView(withId(R.id.menuGistButton))
                .perform(click());
        onView(withId(R.id.activity_my_gists))
                .perform(pressBack());
        onView(withId(R.id.activity_menu_screen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_StarredClick() {
        onView(withId(R.id.menuStarButton))
                .perform(click());
        onView(withId(R.id.activity_starred_screen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_StarredClick_Back() {
        onView(withId(R.id.menuStarButton))
                .perform(click());
        onView(withId(R.id.activity_starred_screen))
                .perform(pressBack());
        onView(withId(R.id.activity_menu_screen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_AccountClick() {
        onView(withId(R.id.menuAccountButton))
                .perform(click());
        onView(withId(R.id.activity_account_screen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_AccountClick_Back() {
        onView(withId(R.id.menuAccountButton))
                .perform(click());
        onView(withId(R.id.activity_account_screen))
                .perform(pressBack());
        onView(withId(R.id.activity_menu_screen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_LogoutClick() {
        onView(withId(R.id.menuLogoutButton))
                .perform(click());
        onView(withId(R.id.activity_logout_screen))
                .check(matches(isDisplayed()));
    }
    /* Maybe don't need this one since it will just logout */
    @Test
    public void menuScreen_LogoutClick_Back() {
        onView(withId(R.id.menuLogoutButton))
                .perform(click());
        onView(withId(R.id.activity_logout_screen))
                .perform(pressBack());
        onView(withId(R.id.activity_menu_screen))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_LoginClick() {
        onView(withId(R.id.menuLoginButton))
                .perform(click());
        onView((withId(R.id.activity_login_screen)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void menuScreen_LoginClick_Back() {
        onView(withId(R.id.menuLoginButton))
                .perform(click());
        onView(withId(R.id.activity_login_screen))
                .perform(pressBack());
        onView(withId(R.id.activity_menu_screen))
                .check(matches(isDisplayed()));
    }

    public static ViewAction setText(final String text){
        return new ViewAction(){
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public String getDescription() {
                return "Change view text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(text);
            }
        };
    }
}
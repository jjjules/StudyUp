package ch.epfl.sweng.studyup;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ChooseColorActivityTest {

    @Rule
    public final ActivityTestRule<ChooseColorActivity> rule =
            new ActivityTestRule<>(ChooseColorActivity.class);

    @Before
    public void initIntent() {
        Intents.init();
    }

    @After
    public void releaseIntent() {
        Intents.release();
    }

    @Test
    public void canSelectThemeRed() {
        onView(withId(R.id.setThemeRed)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void canSelectThemeGreen() {
        onView(withId(R.id.setThemeGreen)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void canSelectThemeBlue() {
        onView(withId(R.id.setThemeBlue)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void canSelectThemeBrown() {
        onView(withId(R.id.setThemeBrown)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void canSelectThemeMulti() {
        onView(withId(R.id.setThemeMulti)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}
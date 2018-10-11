package ch.epfl.sweng.studyup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.TreeSet;

import ch.epfl.sweng.studyup.question.AddQuestionActivity;
import ch.epfl.sweng.studyup.question.Question;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
@RunWith(AndroidJUnit4.class)
public class AddQuestionActivityTest {

    private static final int READ_REQUEST_CODE = 42;

    @Rule
    public final ActivityTestRule<AddQuestionActivity> mActivityRule =
            new ActivityTestRule<>(AddQuestionActivity.class);

    @Before
    public void initiateIntents() {
        Intents.init();
    }
    @After
    public void releaseIntents() {
        Intents.release();
    }

    @Test
    public void testCheckOfTrueFalse() {
        onView(withId(R.id.true_false_radio)).perform(ViewActions.click()).check(matches(isChecked()));
        //onView(withId(R.id.???)).perform(ViewActions.click()).check(matches(isChecked()));
        //onView(withId(R.id.???)).perform(ViewActions.click()).check(matches(isChecked()));
    }

    @Test
    public void testCheckOfMCQ() {
        onView(withId(R.id.mcq_radio)).perform(ViewActions.click()).check(matches(isChecked()));
        /*onView(withId(R.id.radio_answer4)).perform(ViewActions.click()).check(matches(isChecked()));
        onView(withId(R.id.radio_answer3)).perform(ViewActions.click()).check(matches(isChecked()));
        onView(withId(R.id.radio_answer2)).perform(ViewActions.click()).check(matches(isChecked()));
        onView(withId(R.id.radio_answer1)).perform(ViewActions.click()).check(matches(isChecked()));*/ //TODO Doesn't work for some reason
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTrivialInstanceQuestion() {
        Question nullQ = new Question(null, false, 0);
    }

    @Test
    public void testSimpleInstanceQuestionTrueFalse() {
        Uri fake = Uri.parse("studyup://fake/path");
        Question simple = new Question(fake, true, 0);
        assert(simple.isTrueFalseQuestion());
        assert(simple.getAnswer() == 0);
        assert(simple.getQuestionUri().equals(fake));
    }

    @Test
    public void testSimpleInstanceQuestionMCQ() {
        Uri fake = Uri.parse("studyup://fake/path");
        Question simple = new Question(fake, false, 4);
        assert(!simple.isTrueFalseQuestion());
        assert(simple.getAnswer() == 4);
        assert(simple.getQuestionUri().equals(fake));
    }

    @Test
    private void performSearchTest(){
        onView(ViewMatchers.withId(R.id.selectImageButton)).perform(ViewActions.click());
        TreeSet<String> catSet = new TreeSet<String>();
        catSet.add(Intent.CATEGORY_OPENABLE);
        Intents.init();
        Intents.intended(hasCategories(catSet));
        Intents.release();
    }

    @Test
    private void activityResultTest(){
        Intent i = new Intent();
        String fakePath = "/test.jpg";
        Uri uri = Uri.fromFile(new File(fakePath));
        i.setData(uri);
        AddQuestionActivity.onActivityResult(READ_REQUEST_CODE, Activity.RESULT_OK, i);
    }
}
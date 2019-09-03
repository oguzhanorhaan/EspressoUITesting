package com.example.espressofirst;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static androidx.test.espresso.intent.Checks.checkArgument;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    public static int getResourceId(String s) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        String packageName = targetContext.getPackageName();
        return targetContext.getResources().getIdentifier(s, "id", packageName);
    }

    //Matcher for checking if EditText fields contain text hints
    public static Matcher<View> withItemHint(String itemHintText) {
        // use preconditions to fail fast when a test is creating an invalid matcher.
        checkArgument(!(itemHintText.equals(null)));
        return withItemHint(is(itemHintText));
    }

    public static Matcher<View> withItemHint(final Matcher<String> matcherText) {
        // use preconditions to fail fast when a test is creating an invalid matcher.
        checkNotNull(matcherText);
        return new BoundedMatcher<View, EditText>(EditText.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("with item hint: " + matcherText);
            }

            @Override
            protected boolean matchesSafely(EditText editTextField) {
                return matcherText.matches(editTextField.getHint().toString());
            }
        };
    }

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.inputField))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.changeText)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.inputField)).check(matches(withText("Lalala")));
    }

    @Test
    public void ensureTextChangesWork_v2(){
        // Type text and then press the button.
        onView(withId(getResourceId("inputField")))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(getResourceId("changeText"))).perform(click());

        // Check that the text was changed.
        onView(withId(getResourceId("inputField"))).check(matches(withText("Lalala")));
    }

    @Test
    public void ensureToastMessageIsDisplayed(){
        // Type text and then press the button.
        onView(withId(getResourceId("inputField")))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(getResourceId("changeText"))).perform(click());

        // Check that the toast is displayed
        onView(withText("Example")).inRoot(withDecorView(
                not(is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void changeText_newActivity() {
        // Type text and then press the button.
        onView(withId(R.id.inputField)).perform(typeText("NewText"),
                closeSoftKeyboard());
        onView(withId(R.id.switchActivity)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.resultView)).check(matches(withText("NewText")));
    }

    @Test
    public void ensurePlaceholderDisplayed(){
        //withItemHint is a custom Espresso Matcher and controls edittext's placeholder
        onView(withItemHint("example hint"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void buttonShouldUpdateText_ASYNC(){
        onView(withId(R.id.asyncButton)).perform(click());
        onView(withId(R.id.inputField)).check(matches(withText("Done")));
    }

    @Test
    public void navigateToSecondActivity() {
        onView(withText("Change Page")).perform(click());
        Activity activity = getActivityInstance();
        boolean b = (activity instanceof  SecondActivity);
        assertTrue(b);
    }

    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable( ) {
            public void run() {
                Activity currentActivity = null;
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });

        return activity[0];
    }

}

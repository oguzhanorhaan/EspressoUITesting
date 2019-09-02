package com.example.espressofirst;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.espressofirst.MainActivityTest.getResourceId;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class MainActivityIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityIntentRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void triggerIntentTest() {
        // check the button is there
        onView(withId(getResourceId("switchActivity"))).check(matches(notNullValue() ));
        onView(withId(getResourceId("switchActivity"))).check(matches(withText("Change Page")));
        onView(withId(getResourceId("switchActivity"))).perform(click());
        intended(toPackage(SecondActivity.class.getPackage().getName()));
        intended(hasExtra("Developer", "Oguzhan Orhan"));
    }
}

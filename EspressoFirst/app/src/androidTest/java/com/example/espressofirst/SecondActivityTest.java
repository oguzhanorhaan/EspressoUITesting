package com.example.espressofirst;


import android.content.Intent;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SecondActivityTest {

    @Rule
    // third parameter is set to false which means the activity is not started automatically
    public ActivityTestRule<SecondActivity> rule =
            new ActivityTestRule(SecondActivity.class, true, false);

    @Test
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("input", "Test");
        rule.launchActivity(intent);
        onView(withId(R.id.resultView)).check(matches(withText("Test")));
    }


}

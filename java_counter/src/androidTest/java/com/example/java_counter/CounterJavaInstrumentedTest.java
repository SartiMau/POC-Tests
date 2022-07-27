package com.example.java_counter;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import android.content.Context;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.java_counter.activity.JavaCounterActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CounterJavaInstrumentedTest {

    private static final String ZERO_STRING = "0";
    private static final String ONE_STRING = "1";

    // In the rule we declare witch activity is going to be tested
    @Rule
    public ActivityScenarioRule<JavaCounterActivity> counterActivity =
            new ActivityScenarioRule<>(JavaCounterActivity.class);

    @Test
    public void allFunctionalityTest() {
        Context applicationContext = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();

        // Fist we check that the view is being displayed, and then we check if the content is what we expected it to be
        Espresso.onView(withId(R.id.java_counter_title)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.java_counter_title)).check(matches(withText(applicationContext.getString(R.string.java_counter_title))));

        // Here we do the same, but with the number that's being displayed
        Espresso.onView(withId(R.id.java_counter_text)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.java_counter_text)).check(matches(withText(ZERO_STRING)));

        // We use the .perform(click()) to do exactly that, perform a click on the desired element,
        // and use the run blocking in this case to wait for the coroutine
        Espresso.onView(withId(R.id.java_counter_plus_button)).perform(click());
        Espresso.onView(withId(R.id.java_counter_text)).check(matches(withText(ONE_STRING)));

        Espresso.onView(withId(R.id.java_counter_minus_button)).perform(click());
        Espresso.onView(withId(R.id.java_counter_text)).check(matches(withText(ZERO_STRING)));

        Espresso.onView(withId(R.id.java_counter_plus_button)).perform(click());
        Espresso.onView(withId(R.id.java_counter_reset_button)).perform(click());
        Espresso.onView(withId(R.id.java_counter_text)).check(matches(withText(ZERO_STRING)));
    }
}

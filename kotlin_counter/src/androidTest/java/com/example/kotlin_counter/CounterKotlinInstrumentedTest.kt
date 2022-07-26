package com.example.kotlin_counter

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.kotlin_counter.activity.KotlinCounterActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CounterKotlinInstrumentedTest {

    private val countingIdlingResource = CountingIdlingResource("count")
    private val intent = Intent(ApplicationProvider.getApplicationContext(), KotlinCounterActivity::class.java)

    // In the rule we declare witch activity is going to be tested
    @get:Rule
    var counterActivity: ActivityScenarioRule<KotlinCounterActivity> = ActivityScenarioRule<KotlinCounterActivity>(intent)

    @Test
    fun allFunctionalityTest() {
        val applicationContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

        // Fist we check that the view is being displayed, and then we check if the content is what we expected it to be
        Espresso.onView(withId(R.id.kotlin_counter_title)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.kotlin_counter_title)).check(
            matches(withText(applicationContext.getString(R.string.kotlin_counter_title)))
        )

        // Here we do the same, but with the number that's being displayed
        Espresso.onView(withId(R.id.kotlin_counter_text)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.kotlin_counter_text)).check(matches(withText(ZERO_STRING)))

        // We use the .perform(click()) to do exactly that, perform a click on the desired element,
        // and use the run blocking in this case to wait for the coroutine
        Espresso.onView(withId(R.id.kotlin_counter_plus_button)).perform(click())

        //runBlocking { delay(1100) }

        Espresso.onView(withId(R.id.kotlin_counter_text)).check(matches(withText(ONE_STRING)))



        Espresso.onView(withId(R.id.kotlin_counter_minus_button)).perform(click())
        runBlocking { delay(1100) }

        Espresso.onView(withId(R.id.kotlin_counter_text)).check(matches(withText(ZERO_STRING)))

        Espresso.onView(withId(R.id.kotlin_counter_plus_button)).perform(click())
        runBlocking { delay(1100) }

        Espresso.onView(withId(R.id.kotlin_counter_reset_button)).perform(click())
        runBlocking { delay(1100) }

        Espresso.onView(withId(R.id.kotlin_counter_text)).check(matches(withText(ZERO_STRING)))
    }

    companion object {
        const val ZERO_STRING = "0"
        const val ONE_STRING = "1"
    }
}

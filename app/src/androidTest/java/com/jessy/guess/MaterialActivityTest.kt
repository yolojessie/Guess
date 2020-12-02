package com.jessy.guess

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.*
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches


@RunWith(AndroidJUnit4::class)
class MaterialActivityTest {
    @get:Rule
    val rule = ActivityScenarioRule(MaterialActivity::class.java)

    @Test
    fun guessWrong() {
        val scenario =  rule.scenario
        // get resources from activities
        lateinit var resources:Resources
        var secret = 0
        scenario.onActivity {
            secret = it.secretNumber.secret
            Log.d("TEST", "secret: $secret")
            resources = it.resources
        }
        val n = 6
        for (n in 1..10) {
            if (n != secret) {
                Log.d("TEST", "n: ${n}")
                onView(withId(R.id.ed_number))
                    .perform(typeText("5"))
                onView(withId(R.id.ed_number))
                    .perform(clearText())
                onView(withId(R.id.ed_number))
                    .perform(typeText(n.toString()))
                onView(withId(R.id.ok_button))
                    .perform(click())
                println()
                val message =
                    if (n < secret) resources.getString(R.string.bigger)
                    else resources.getString(R.string.smaller)
                onView(withText(message)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            }
        }
    }
}
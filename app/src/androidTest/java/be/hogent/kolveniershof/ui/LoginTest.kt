package be.hogent.kolveniershof.ui

import be.hogent.kolveniershof.R

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import be.hogent.kolveniershof.ui.MainActivity
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginTest {


    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule((MainActivity::class.java))

    @Test
    fun logout(){
        //Open the menu
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()))

        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_logout))
    }
    @Test
    fun user_successfully_logged_in_phone(){
        onView(withId(R.id.input_email)).perform(typeText("client1@gmail.com"))
        onView(withId(R.id.input_password)).perform(scrollTo(),typeText("test00##"))
        onView(withId(R.id.button_sign_in)).perform(scrollTo(),click())
        onView(withId(R.id.dateSelectorMinusOne)).check(matches(isDisplayed()))

    }

    @Test
    fun user_wrong_email_log_in_phone(){
        onView(withId(R.id.input_email)).perform(typeText("wrongclient@gmail.com"))
        onView(withId(R.id.input_password)).perform(scrollTo(),typeText("test00##"))
        onView(withId(R.id.button_sign_in)).perform(scrollTo(),click())
        onView(withText("Something went wrong")).check(matches(isDisplayed()));

    }

}

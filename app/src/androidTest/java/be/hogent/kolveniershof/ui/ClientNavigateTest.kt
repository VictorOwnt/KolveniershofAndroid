package be.hogent.kolveniershof.ui


import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import be.hogent.kolveniershof.R
import be.hogent.kolveniershof.adapters.DateAdapter
import junit.framework.Assert.assertEquals
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.util.*

@RunWith(AndroidJUnit4::class)
class ClientNavigateTest{

    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule((MainActivity::class.java))
    @Before
    fun setup(){
        logout()
        login()
    }

    @Test
    fun swipeToNextDay(){
        //Check if set to current day
       var date =  rule.activity.findViewById<DateButton>(R.id.dateSelectorNow)
        assertEquals(date.dateTextView.text,DateTime.now().toString("dd"))
        assertEquals(date.monthTextView.text,DateTime.now().toString("MMM"))

        //Check ability to swipe to next day
        //onView(withId(R.id.dateSelectorNow)).check(matches(withText(DateTime.now().toString())))
        onView(withId(R.id.main_content_container)).perform(ViewActions.swipeLeft())
        date =  rule.activity.findViewById(R.id.dateSelectorNow)
        assertEquals(date.dateTextView.text,DateTime.now().plusDays(1).toString("dd"))
        assertEquals(date.monthTextView.text,DateTime.now().plusDays(1).toString("MMM"))

    }

    private fun logout(){
        //Open the menu
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen()))

        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_logout))
    }

    private fun login(){
        onView(withId(R.id.input_email))
            .perform(ViewActions.typeText("client1@gmail.com"))
        onView(withId(R.id.input_password))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("test00##"))
        onView(withId(R.id.button_sign_in))
            .perform(ViewActions.scrollTo(), ViewActions.click())


    }
}
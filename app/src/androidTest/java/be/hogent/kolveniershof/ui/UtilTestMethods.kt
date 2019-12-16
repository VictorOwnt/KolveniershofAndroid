package be.hogent.kolveniershof.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import be.hogent.kolveniershof.R
import junit.framework.Assert
import org.joda.time.DateTime

class UtilTestMethods {

    fun loginAdmin(){
        Espresso.onView(ViewMatchers.withId(R.id.input_email))
            .perform(ViewActions.typeText("admin1@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("test00##"))
        Espresso.onView(ViewMatchers.withId(R.id.button_sign_in))
            .perform(ViewActions.scrollTo(), ViewActions.click())
    }
    fun loginClient(){
        Espresso.onView(ViewMatchers.withId(R.id.input_email))
            .perform(ViewActions.typeText("client1@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("test00##"))
        Espresso.onView(ViewMatchers.withId(R.id.button_sign_in))
            .perform(ViewActions.scrollTo(), ViewActions.click())


    }
    fun logout(){
        //Open the menu
        try {
            Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open())
            Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isOpen()))

            Espresso.onView(ViewMatchers.withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_logout))
        }catch(e:Exception){
            //Catch if user is already logged in
        }

    }

    fun checkDay(i: Int,rule: ActivityTestRule<MainActivity>) {
        val date =  rule.activity.findViewById<DateButton>(R.id.dateSelectorNow)
        Assert.assertEquals(date.dateTextView.text, DateTime.now().plusDays(i).toString("dd"))
        Assert.assertEquals(date.monthTextView.text, DateTime.now().plusDays(i).toString("MMM"))
    }
}
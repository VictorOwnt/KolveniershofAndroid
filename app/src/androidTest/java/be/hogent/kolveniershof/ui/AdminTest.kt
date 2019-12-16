package be.hogent.kolveniershof.ui
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import be.hogent.kolveniershof.R
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.anything
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdminTest{

   private val util=UtilTestMethods()
    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule((MainActivity::class.java))
    @Before
    fun setup(){
        util.logout()
        util.loginAdmin()
    }

    @Test
    fun getPictoFromSelectedUser(){
        //Open listView and select an user
        onView(withId(R.id.action_userSelector)).perform(click())
        onView(withId(R.id.user_list)).check(matches(isDisplayed()))
        onData(anything()).inAdapterView(withId(R.id.user_list)).atPosition(0).perform(click())
        //Check if shown workday = workday of selected user
    }





}
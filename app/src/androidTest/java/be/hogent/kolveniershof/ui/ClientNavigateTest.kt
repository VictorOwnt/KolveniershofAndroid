package be.hogent.kolveniershof.ui


import android.app.DatePickerDialog
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import be.hogent.kolveniershof.R
import junit.framework.Assert.assertEquals
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
//import org.robolectric.RobolectricTestRunner
//import org.robolectric.shadows.ShadowDatePickerDialog
import java.util.*


@RunWith(AndroidJUnit4::class/*RobolectricTestRunner::class*/)
class ClientNavigateTest{

    private val util=UtilTestMethods()
    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule((MainActivity::class.java))
    @Before
    fun setup(){
        util.logout()
        util.loginClient()
    }

    @Test
    fun swipeToNextDay(){
        //Check if set to current day
        util.checkDay(0,rule)
        //Check ability to swipe to next day
        onView(withId(R.id.main_content_container)).perform(ViewActions.swipeLeft())
        //Check if day equals current day +1
        util.checkDay(1,rule)

    }
    @Test
    fun clickToNextDay(){
        util.checkDay(0,rule)
        onView(withId(R.id.dateSelectorPlusOne)).perform(click())
        util.checkDay(1,rule)

    }
    @Test
    fun changeDateUsingDatePicker(){
        /*checkDay(0)
        onView(withId(R.id.action_calendar)).perform(click())
        onView(withId(R.id.action_calendar)).perform(PickerActions.setDate(2019,10,28))
        //onView(withId(R.id.nav_calendar)).check(matches(withText("25-01-2017")));
        val dialog = ShadowDatePickerDialog.getLatestDialog() as DatePickerDialog
        val calendar: Calendar = Calendar.getInstance()
        val expectedYear: Int = calendar.get(Calendar.YEAR)
        val expectedMonth: Int = calendar.get(Calendar.MONTH)
        val expectedDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

        assertEquals(dialog.datePicker.dayOfMonth, expectedDay)
        assertEquals(dialog.datePicker.month, expectedMonth)
        assertEquals(dialog.datePicker.year, expectedYear)*/
    }
    @Test
    fun showTextIfEmptyDay(){

    }


}
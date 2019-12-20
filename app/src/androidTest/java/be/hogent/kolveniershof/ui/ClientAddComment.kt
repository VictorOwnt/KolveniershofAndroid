package be.hogent.kolveniershof.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import be.hogent.kolveniershof.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClientAddComment{
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
    fun addCommentOnWeekend(){
        onView(withId(R.id.action_calendar)).perform(click())
        onView(withId(R.id.main_content_container)).perform(ViewActions.swipeRight())
        onView(withId(R.id.input_comment)).perform(typeText("Testing"))
        onView(withId(R.id.buttonSendComment)).perform(click())

    }
}
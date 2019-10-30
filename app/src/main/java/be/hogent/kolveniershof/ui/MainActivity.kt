package be.hogent.kolveniershof.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.ListFragment
import be.hogent.kolveniershof.R
import com.google.android.material.navigation.NavigationView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    /**
     * Whether or not the activity is in tablet mode.
     */
    private var tablet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check is user is logged in
        if (!getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE).getBoolean("ISLOGGEDIN", false)) {
            // Open AuthActivity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set name and email in navHeader
        val headerView = navView.getHeaderView(0)
        val navHeaderName = headerView.findViewById<TextView>(R.id.nav_header_name)
        val navHeaderEmail = headerView.findViewById<TextView>(R.id.nav_header_email)
        navHeaderName.text = (getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)
            .getString("FIRSTNAME", getString(R.string.app_name)) + " " +
                getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)
                    .getString("LASTNAME", ""))
        navHeaderEmail.text = getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)
            .getString("EMAIL", "")

        navView.setNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            // Check the first item in the navigation menu
            navView.menu.findItem(R.id.nav_calendar).isChecked = true
            navView.menu.performIdentifierAction(R.id.nav_calendar, 0)
        }

        // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
        // If this view is present, then the activity should be in two-pane mode.
        if (main_detail_container != null)
            tablet = true

        // Set logger
        Logger.addLogAdapter(AndroidLogAdapter())


    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button.

        when (item.itemId) {
            R.id.action_calendar -> {
                val cal = Calendar.getInstance()
                val y = cal.get(Calendar.YEAR)
                val m = cal.get(Calendar.MONTH)
                val d = cal.get(Calendar.DAY_OF_MONTH)


                val datepickerdialog: DatePickerDialog = DatePickerDialog(this, R.style.DialogTheme, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

                    openDetailFragment(DateSelectorFragment.newInstance(("$year-${monthOfYear+1}-$dayOfMonth")))

                }, y, m, d)


                datepickerdialog.show()
            }
        }

        return true




    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        // Handle navigation view item clicks.
        when (item.itemId) {
            R.id.nav_calendar -> openDetailFragment(DateSelectorFragment.newInstance("date")) // todo
            //R.id.nav_busses -> openDetailFragment(BUSSES) // todo
            R.id.nav_logout -> {
                // Logout
                val sharedPref = getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)
                sharedPref.edit().clear().apply()
                // Open AuthActivity
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /*override fun onListFragmentInteraction(itemKindId: Int, itemId: String?) {
        when (itemKindId) {
            R.id.nav_speedCamera -> openDetailFragment(SpeedCameraFragment.newInstance(itemId))
            R.id.nav_avgSpeedCheck -> openDetailFragment(AvgSpeedCheckFragment.newInstance(itemId))
            R.id.nav_policeCheck -> openDetailFragment(PoliceCheckFragment.newInstance(itemId))
        }
    }*/

    private fun openDetailFragment(newFragment: Fragment) {
        if (tablet) {
            this.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_detail_container, newFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        } else {
            this.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_content_container, newFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }
    }

    fun hideKeyboard() {
        val inputManager = this
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = this.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}

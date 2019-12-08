package be.hogent.kolveniershof.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import be.hogent.kolveniershof.R
import be.hogent.kolveniershof.adapters.UserAdapter
import be.hogent.kolveniershof.model.User
import be.hogent.kolveniershof.util.GlideApp
import be.hogent.kolveniershof.viewmodels.UserViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.user_list.*
import org.joda.time.DateTime
import java.util.*


class MainActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    /*Datepicker*/
    private val cal = Calendar.getInstance()
    private var year = cal.get(Calendar.YEAR)
    private var month = cal.get(Calendar.MONTH)
    private var day = cal.get(Calendar.DAY_OF_MONTH)
    private lateinit var clickedUser: User
    /**
     * Whether or not the activity is in two pane mode.
     */
    private var twoPane: Boolean = false
    private lateinit var clientsListView: ListView
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        
        // Check is user is logged in
        if (!sharedPreferences.getBoolean("ISLOGGEDIN", false)) {
            // Open AuthActivity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        // The detail container view will be present only in the large-screen layouts (res/values-w900dp).
        // If this view is present, then the activity should be in two-pane mode.
        if (main_detail_container != null ) {
            twoPane = true
        }
        if(sharedPreferences.getBoolean("ADMIN", false) && twoPane){
            fillListView()
        }



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Setup navHeader
        val headerView = navView.getHeaderView(0)
        val navHeaderImage = headerView.findViewById<ImageView>(R.id.nav_header_image)
        val navHeaderName = headerView.findViewById<TextView>(R.id.nav_header_name)
        val navHeaderEmail = headerView.findViewById<TextView>(R.id.nav_header_email)
        // Load image
        val imgUrl = sharedPreferences.getString("IMGURL", "")!!
        val img = try {
            FirebaseStorage.getInstance().reference.child(imgUrl)
        } catch (e: Exception) {
            when(e) {
                is StorageException, is IllegalArgumentException -> null
                else -> throw e
            }
        }
        GlideApp.with(this)
            .load(img)
            .placeholder(R.drawable.ic_face)
            .fallback(R.drawable.ic_face)
            .fitCenter()
            .circleCrop()
            .into(navHeaderImage)
        // Load name
        navHeaderName.text = (sharedPreferences.getString("FIRSTNAME", getString(R.string.app_name)) + " " +
                sharedPreferences.getString("LASTNAME", ""))
        // Load email
        navHeaderEmail.text = sharedPreferences.getString("EMAIL", "")

        navView.setNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            // Check the first item in the navigation menu
            navView.menu.findItem(R.id.nav_calendar).isChecked = true
            navView.menu.performIdentifierAction(R.id.nav_calendar, 0)
        }

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
                // Get date to show


                // Show datepicker dialog
                val datePickerDialog = DatePickerDialog(this, R.style.DialogTheme, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    // Change dateSelector to selected date
                    val date = DateTime(year, monthOfYear+1, dayOfMonth, 0, 0, 0)
                    this.year = year
                    this.month = monthOfYear
                    this.day = dayOfMonth
                    if (::clickedUser.isInitialized) {
                        openDetailFragment(
                            DateSelectorFragment.newInstance(
                                date,
                                clickedUser.id
                            )
                        )
                    } else {
                        openDetailFragment(
                        DateSelectorFragment.newInstance(
                            date,
                            sharedPreferences.getString("ID", "")!!
                        )
                        )
                    }

                }, year, month, day)
                datePickerDialog.show()
            }
            R.id.action_userSelector ->{

               // setContentView(R.layout.user_list)

                fillListView()
                main_content_container.visibility = View.GONE
            }
        }


        return true

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        // Handle navigation view item clicks.
        when (item.itemId) {
            R.id.nav_calendar -> openDetailFragment(
                DateSelectorFragment.newInstance(
                    DateTime.now(),
                    sharedPreferences.getString("ID", "")!!
                )
            )
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

    private fun openDetailFragment(newFragment: Fragment) {
        if (twoPane && sharedPreferences.getBoolean("ADMIN", false)) {
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
            if (main_detail_container != null) {
                main_detail_container?.visibility = View.GONE
                main_content_container.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            }
        }
    }

    private fun openDetailFragmentOfSelectedUser(
        newFragment: Fragment
    ) {
        if(twoPane) {
            this.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_detail_container, newFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }else{
            this.supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_content_container, newFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
            user_list_container.visibility = View.GONE
            main_content_container.visibility = View.VISIBLE

        }
    }
    private fun fillListView(){
        if(!twoPane)
        user_list_container.visibility = View.VISIBLE
        clientsListView = findViewById(R.id.user_list)
        val clients = UserViewModel().getClients().blockingFirst()
        val adapter = UserAdapter(this.applicationContext, clients)
        clientsListView.adapter = adapter
        clientsListView.setOnItemClickListener { parent, view, position, id ->

            clickedUser = parent.getItemAtPosition(position) as User
            openDetailFragmentOfSelectedUser(
                DateSelectorFragment.newInstance(
                    DateTime(year, month + 1, day, 0, 0, 0),
                    clickedUser.id
                )
            )
            Toast.makeText(
                this,
                clickedUser.firstName + " " + clickedUser.lastName,
                Toast.LENGTH_SHORT
            ).show()


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

package be.hogent.kolveniershof.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.inputmethod.InputMethodManager
import be.hogent.kolveniershof.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_auth)

        // Open welcome fragment
        this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.auth_content_container, LoginFragment())
            .commit()

        supportActionBar!!.hide()
    }

    fun hideKeyboard() {
        val inputManager = this
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = this.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}

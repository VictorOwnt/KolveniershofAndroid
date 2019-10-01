package be.hogent.kolveniershof.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.hogent.kolveniershof.R

class MainActivity : AppCompatActivity() {

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
    }
}

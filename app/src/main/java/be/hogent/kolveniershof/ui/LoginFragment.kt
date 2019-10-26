package be.hogent.kolveniershof.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import be.hogent.kolveniershof.R
import be.hogent.kolveniershof.databinding.FragmentLoginBinding
import be.hogent.kolveniershof.viewmodels.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import javax.security.auth.login.LoginException

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var signinButton: Button
    private lateinit var usernameInput: TextInputEditText
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInput: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signinButton = view.findViewById(R.id.button_sign_in)
        usernameInput = view.findViewById(R.id.input_email)
        emailInputLayout = view.findViewById(R.id.inputlayout_email)
        passwordInput = view.findViewById(R.id.input_password)
        passwordInputLayout = view.findViewById(R.id.inputlayout_password)

        // OnClickListener sign in button
        signinButton.setOnClickListener {
            try {
                val loggedInUser = viewModel.login(usernameInput.text.toString(), passwordInput.text.toString())

                // Save logged in user
                val sharedPreferences = activity!!.getSharedPreferences("USER_CREDENTIALS",
                    Context.MODE_PRIVATE
                )
                sharedPreferences.edit()
                    .putString("ID", loggedInUser.id)
                    .putString("USERNAME", loggedInUser.userName)
                    .putString("FIRSTNAME", loggedInUser.firstName)
                    .putString("LASTNAME", loggedInUser.lastName)
                    .putBoolean("ADMIN", loggedInUser.isAdmin)
                    .putString("TOKEN", "Bearer " + loggedInUser.token)
                    .putBoolean("ISLOGGEDIN", true)
                    .apply()

                // Open MainActivity
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                (activity as AuthActivity).hideKeyboard()
                activity!!.finish()
            } catch (e: LoginException) {
                (activity as AuthActivity).hideKeyboard()
                AlertDialog.Builder(context!!)
                    .setTitle("Something went wrong")
                    .setMessage(e.message)
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(R.drawable.ic_error)
                    .show()
            }
        }

        // TextWatchers
        usernameInput.addTextChangedListener(watcher)
        passwordInput.addTextChangedListener(watcher)
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            emailInputLayout.error = getString(R.string.required)
            emailInputLayout.isErrorEnabled = usernameInput.text.isNullOrBlank()

            passwordInputLayout.error = getString(R.string.required)
            passwordInputLayout.isErrorEnabled = passwordInput.text.isNullOrBlank()

            val nonBlank = !(usernameInput.text.isNullOrBlank() || passwordInput.text.isNullOrBlank())
            signinButton.isEnabled = nonBlank
        }
    }

}


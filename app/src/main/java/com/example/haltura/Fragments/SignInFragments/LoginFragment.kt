package com.example.haltura.Fragments.SignInFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.fragment.app.activityViewModels
import com.example.haltura.R
import com.example.haltura.ViewModels.LoginViewModel

import com.example.haltura.Helpers.Validation
import com.example.haltura.Sql.Items.UserLoginSerializable

import com.example.haltura.Utils.Const
import com.example.haltura.activities.LoginActivity


class LoginFragment : Fragment() {
    lateinit var loadingScreen: RelativeLayout
    private lateinit var fragmentView: View
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflation layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_login, container, false)
        loginButton = fragmentView.findViewById<View>(R.id.btn_SignIn) as Button
        signUpButton = fragmentView.findViewById<View>(R.id.btn_SignUp) as Button

        loadingScreen = (activity as LoginActivity).loadingScreen

        //helper = UserOpenHelper(this)
        etEmail = fragmentView.findViewById<View>(R.id.et_Email) as EditText
        etPassword = fragmentView.findViewById<View>(R.id.et_Password) as EditText
        //oneTapClient = Identity.getSignInClient(this)
        setClickListeners()
        return fragmentView
    }
        //todo: try to change it - bug: cant go to signup twice.
    private fun switchFragment(fragment: Fragment, fragmentId: String) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.login_fragment, fragment, fragmentId)
            transaction.addToBackStack(fragmentId)
            transaction.commit()
        }
    }


    private fun setClickListeners() {
        loginButton.setOnClickListener {
            signIn()
        }
        signUpButton.setOnClickListener{
            switchFragment(SignUpFragment(), Const.signup_fragment)

        }
//        _fragmentView.findViewById<TextView>(R.id.login_reset_password).setOnClickListener {
//            forgotPassword()
//        }
    }

    fun signIn() {
        if (Validation.signInValid(
                etEmail, etPassword
            )
        ) {
            val user = UserLoginSerializable(
                etEmail.text.toString(),
                null, etPassword.text.toString())// ,"0","0","0")
            //DeviceNotifications.notifyDefault(this,"Test","testing")
            viewModel.userSignIn(user)
        }

    }


//    fun signUp(view: View) {
//        //startActivity(Intent(this, AddWorkActivity::class.java))
////        startActivity(Intent(this, SignUpActivity::class.java))
//        startActivity(Intent(this, RegisterActivity::class.java))
////        //todo: do this with user open helper - It is not the responsibility of the class
//    }




//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
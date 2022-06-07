package com.example.haltura.Fragments.SignInFragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.haltura.R
import com.example.haltura.ViewModels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

import androidx.fragment.app.activityViewModels
import com.example.haltura.DeviceNotifications
import com.example.haltura.Helpers.Validation
import com.example.haltura.Sql.Items.UserSerializable

import com.example.haltura.Sql.UserOpenHelper
import com.example.haltura.activities.MainActivity2
import com.example.haltura.activities.RegisterActivity
import com.example.haltura.activities.SignInActivity

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    lateinit var loadingScreen: RelativeLayout
    private lateinit var fragmentView: View
    private lateinit var loginButton: Button
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
        loadingScreen = (activity as SignInActivity).loadingScreen

        //helper = UserOpenHelper(this)
        etEmail = fragmentView.findViewById<View>(R.id.et_Email) as EditText
        etPassword = fragmentView.findViewById<View>(R.id.et_Password) as EditText
        //oneTapClient = Identity.getSignInClient(this)
        setClickListeners()
        return fragmentView
    }
    private fun setClickListeners() {
        loginButton.setOnClickListener {
            signIn()
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
            val user = UserSerializable(
                etEmail.text.toString(),
                "",
                "",
                "",
                etPassword.text.toString()
            )// ,"0","0","0")
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
package com.example.haltura.Fragments.SignInFragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.haltura.R
import com.example.haltura.Sql.Items.UserLoginSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.ProfileData
import com.example.haltura.Utils.Validation
import com.example.haltura.ViewModels.LoginViewModel
import com.example.haltura.activities.LoginActivity
import com.kizitonwose.calendarview.model.DayOwner
import kotlin.system.exitProcess


class LoginFragment : Fragment() {
    lateinit var loadingScreen: RelativeLayout
    private lateinit var fragmentView: View
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var _resetPassword : TextView
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
        _resetPassword = fragmentView.findViewById(R.id.tv_ForgotYourPassword) as TextView
        //helper = UserOpenHelper(this)
        etEmail = fragmentView.findViewById<View>(R.id.et_Email) as EditText
        etPassword = fragmentView.findViewById<View>(R.id.et_Password) as EditText
        //oneTapClient = Identity.getSignInClient(this)
        setClickListeners()
        initBackPressed()
        return fragmentView
    }
    private fun switchFragment(fragment: Fragment, fragmentId: String) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.login_fragment, fragment, fragmentId)
            transaction.addToBackStack(null)//fragmentId
            transaction.setReorderingAllowed(true)
            transaction.commit()
        }
    }


    private fun setClickListeners() {

        _resetPassword.setOnClickListener {
            resetEmail()
        }

        loginButton.setOnClickListener {
            signIn()
        }
        signUpButton.setOnClickListener{
            switchFragment(SignUpFragment(), Const.signup_fragment)

        }
    }

    private fun resetEmail()
    {

        val resetEmailView: View = layoutInflater.inflate(R.layout.reset_password_popup, null)
        val popup = PopupWindow(
            resetEmailView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val cancel = resetEmailView.findViewById(R.id.cancel) as TextView
        val update = resetEmailView.findViewById(R.id.update) as TextView
        val email = resetEmailView.findViewById(R.id.email) as EditText

        cancel.setOnClickListener {
            popup.dismiss()
            removeBackground(true)
        }

        update.setOnClickListener {
            //_viewModel.restPassword(email.text.toString())
            popup.dismiss()
            removeBackground(true)
        }

        removeBackground(false)
        popup.isFocusable = true
        popup.update()
        popup.showAtLocation(fragmentView, Gravity.CENTER, 0, 0)
    }

    private fun removeBackground(show: Boolean) {
        if (show) {
            fragmentView.visibility = View.VISIBLE

        } else {
            fragmentView.visibility = View.GONE
        }
    }

    fun signIn() {
        if (Validation.signInValid(
                etEmail, etPassword
            )
        ) {
            val user = UserLoginSerializable(
                etEmail.text.toString(),
                null, etPassword.text.toString())// ,"0","0","0")
            viewModel.userSignIn(user)
        }

    }
    private fun initBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            this.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                    exitProcess(0)
                }
            })
    }

}
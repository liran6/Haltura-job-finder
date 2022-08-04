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
import androidx.lifecycle.Observer
import com.example.haltura.Utils.Validation
import com.example.haltura.R
import com.example.haltura.Utils.Const
import com.example.haltura.ViewModels.LoginViewModel
import com.example.haltura.activities.LoginActivity

class SignUpFragment : Fragment() {

    lateinit var loadingScreen: RelativeLayout
    private lateinit var fragmentView: View
    private lateinit var signUpButton: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etUserName: EditText
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflation layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_sign_up, container, false)
        signUpButton = fragmentView.findViewById<View>(R.id.btn_SignUp) as Button

        loadingScreen = (activity as LoginActivity).loadingScreen

        etEmail = fragmentView.findViewById<View>(R.id.et_Email) as EditText
        etUserName = fragmentView.findViewById<View>(R.id.et_UserName) as EditText
        etPassword = fragmentView.findViewById<View>(R.id.et_Password) as EditText
        etConfirmPassword = fragmentView.findViewById<View>(R.id.et_ConfirmPassword) as EditText
        val registrationSuccess = Observer<Boolean>{value ->
            if(value){
                switchFragment(LoginFragment(),Const.login_fragment)
                viewModel.mutableSignUpSucess.value = false
            }
        }
        setClickListeners()
        registrationObserver(registrationSuccess)
        return fragmentView
    }
    private fun registrationObserver(registrationSuccess:Observer<Boolean>){
        viewModel.mutableSignUpSucess.observe(viewLifecycleOwner,registrationSuccess)
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
        signUpButton.setOnClickListener {
            signUp()
        }
    }
    fun signUp() {
        if (Validation.registerValid(
                etPassword, etConfirmPassword,
                etEmail, etUserName
            )
        ) {
           var email =  etEmail!!.text.toString()
            var userName = etUserName!!.text.toString()
            var password =etPassword!!.text.toString()
            viewModel.createUser(email, userName, password)
        }
    }
















}
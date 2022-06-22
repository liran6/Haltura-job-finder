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

//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [SignUpFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
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
        //oneTapClient = Identity.getSignInClient(this)
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
//        signUpButton.setOnClickListener{
//            switchFragment(SignUpFragment(), Const.signup_fragment)
//
//        }
//        _fragmentView.findViewById<TextView>(R.id.login_reset_password).setOnClickListener {
//            forgotPassword()
//        }
    }
    fun signUp() {
        if (Validation.registerValid(
                etPassword, etConfirmPassword,
                etEmail, etUserName
            )
        ) {
            //todo: Admin user
           var email =  etEmail!!.text.toString()
            var userName = etUserName!!.text.toString()
            var password =etPassword!!.text.toString()
            viewModel.createUser(email, userName, password)
        }
    }















//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sign_up, container, false)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment SignUpFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            SignUpFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}
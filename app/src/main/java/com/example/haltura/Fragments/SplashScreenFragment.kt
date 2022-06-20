package com.example.haltura.Fragments
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import android.widget.RelativeLayout
//import androidx.fragment.app.activityViewModels
//import com.example.haltura.R
//import com.example.haltura.Sql.UserOpenHelper
//import com.example.haltura.ViewModels.LoginViewModel
//import com.google.android.gms.auth.api.identity.BeginSignInRequest
//import com.google.android.gms.auth.api.identity.Identity
//import com.google.android.gms.auth.api.identity.SignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.firebase.auth.FirebaseAuth
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [SplashScreenFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class SplashScreenFragment : Fragment() {
//    lateinit var loadingScreen: RelativeLayout
//    lateinit var fragmentView: View
//    private lateinit var etEmail: EditText
//    private lateinit var etPassword: EditText
////    private val viewModel: LoginViewModel by activityViewModels<>()
////    companion object {
////        private const val RC_SIGN_IN = 120
////    }
//    private lateinit var helper: UserOpenHelper
////    private lateinit var auth: FirebaseAuth
//    private lateinit var oneTapClient: SignInClient
//    private lateinit var googleSignInClient: GoogleSignInClient
//    private lateinit var signInRequest: BeginSignInRequest
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflation layout for this fragment
//        fragmentView = inflater.inflate(R.layout.fragment_splash_screen, container, false)
////        auth = FirebaseAuth.getInstance()
//        //helper = UserOpenHelper(this)
//        etEmail = fragmentView.findViewById<View>(R.id.et_Email) as EditText
//        etPassword = fragmentView.findViewById<View>(R.id.et_Password) as EditText
//        //oneTapClient = Identity.getSignInClient(this)
//
//        return fragmentView
//    }
////    private fun init(){
////        //setContentView(R.layout.activity_login)
////        auth = FirebaseAuth.getInstance()
////        helper = UserOpenHelper(this)
////        etEmail = fragmentView.findViewById<View>(R.id.et_Email) as EditText
////        etPassword = fragmentView.findViewById<View>(R.id.et_Password) as EditText
////        oneTapClient = Identity.getSignInClient(this)
////    }
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment SplashScreenFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            SplashScreenFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}
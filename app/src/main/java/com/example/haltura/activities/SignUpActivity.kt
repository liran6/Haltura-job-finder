//package com.example.haltura.activities
//
//import android.content.Intent
//import android.os.Bundle
//import android.text.InputFilter
//import android.text.InputFilter.LengthFilter
//import android.util.Log
//import android.view.View
//import android.widget.*
//import android.widget.AdapterView.OnItemSelectedListener
//import androidx.appcompat.app.AppCompatActivity
//import com.example.haltura.R
//import com.example.haltura.Helpers.Validation
//import com.example.haltura.Helpers.Validation.Companion.signUpValid
//import com.example.haltura.Sql.Items.Address
//import com.example.haltura.Sql.Items.User
//import com.example.haltura.Sql.UserOpenHelper
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//
//class SignUpActivity : AppCompatActivity() {
//    private val FILTER4 = arrayOf<InputFilter>(LengthFilter(4))
//    private val FILTER3 = arrayOf<InputFilter>(LengthFilter(3))
//    private val FILTER2 = arrayOf<InputFilter>(LengthFilter(2))
////    var city: String ? = null
////    var cities: Array<String> ? = null
////    var etUserName: EditText ? = null
////    var etFirstName: EditText ? = null
////    var etLastName: EditText ? = null
////    var spinnerCity: Spinner ? = null
////    var etApartment: EditText ? = null
////    var etStreetName: EditText ? = null
////    var etStreetNumber: EditText ? = null
////    var etFloor: EditText ? = null
////    var etEmail: EditText ? = null
////    var etPhone: EditText ? = null
////    var etPassword: EditText ? = null
////    var etConfirmPassword: EditText ? = null
////    var btnSignUp: Button ? = null
//    private lateinit var city: String
//    private lateinit var cities: Array<String>
//    private lateinit var etUserName: EditText
//    private lateinit var etFirstName: EditText
//    private lateinit var etLastName: EditText
//    private lateinit var spinnerCity: Spinner
//    private lateinit var etApartment: EditText
//    private lateinit var etStreetName: EditText
//    private lateinit var etStreetNumber: EditText
//    private lateinit var etFloor: EditText
//    private lateinit var etEmail: EditText
//    private lateinit var etPhone: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var etConfirmPassword: EditText
//    private lateinit var btnSignUp: Button
//
//    var helper = UserOpenHelper(this)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_up)
//        cities = resources.getStringArray(R.array.cities)
//        etUserName = findViewById<View>(R.id.et_UserName) as EditText
//        etFirstName = findViewById<View>(R.id.et_FirstName) as EditText
//        etLastName = findViewById<View>(R.id.et_LastName) as EditText
//        etStreetName = findViewById<View>(R.id.et_StreetName) as EditText
//        etStreetNumber = findViewById<View>(R.id.et_StreetNumber) as EditText
//        etFloor = findViewById<View>(R.id.et_Floor) as EditText
//        etEmail = findViewById<View>(R.id.et_Email) as EditText
//        etPhone = findViewById<View>(R.id.et_Phone) as EditText
//        etPassword = findViewById<View>(R.id.et_Password) as EditText
//        etConfirmPassword = findViewById<View>(R.id.et_ConfirmPassword) as EditText
//        etApartment = findViewById<View>(R.id.et_Apartment) as EditText
//        btnSignUp = findViewById<View>(R.id.btn_SignUp) as Button
//        spinnerCity = findViewById<View>(R.id.spinner_City) as Spinner
//        spinnerCity!!.onItemSelectedListener = object : OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View,
//                position: Int,
//                id: Long
//            ) {
//                city = cities[position]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        }
//    }
//
//    fun signUp(view: View) {
//        if (signUpValid(
//                etFirstName, etLastName, etUserName, spinnerCity, city, etStreetName,
//                etStreetNumber, etFloor, etApartment, etPhone, etPassword, etConfirmPassword,
//                etEmail
//            )
//        ) {
//            //todo: check username and email existence - need to talk about that
//            if (true){//Validation.userNameExists(etUserName, this)) {
//                val user = User()
//                user.setUserName(etUserName.text.toString())
//                user.setUserFirstName(etFirstName!!.text.toString())
//                user.setUserLastName(etLastName!!.text.toString())
//                user.setPassword(etPassword!!.text.toString())
//                user.setEmail(etEmail!!.text.toString())
//                user.setAddress(
//                    Address(
//                        city,
//                        etStreetName!!.text.toString(),
//                        etStreetNumber!!.text.toString(),
//                        etFloor!!.text.toString(),
//                        etApartment!!.text.toString()
//                    )
//                )
//                user.setUserPhone(etPhone!!.text.toString())
//                user.setIsAdmin(false)
//                //helper.createUser(user)
//            } else {
//                Toast.makeText(
//                    this,
//                    "user name already taken!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//}
////
////    public override fun onStart() {
////        super.onStart()
////        // Check if user is signed in (non-null) and update UI accordingly.
////        val currentUser = auth.currentUser
////        if(currentUser != null){
////            reload();
////        }
////    }
////    private fun createAccount(email: String, password: String) {
////        // [START create_user_with_email]
////        auth.createUserWithEmailAndPassword(email, password)
////            .addOnCompleteListener(this) { task ->
////                if (task.isSuccessful) {
////                    // Sign in success, update UI with the signed-in user's information
////                    Log.d(TAG, "createUserWithEmail:success")
////                    val user = auth.currentUser
////                    updateUI(user)
////                } else {
////                    // If sign in fails, display a message to the user.
////                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
////                    Toast.makeText(baseContext, "Authentication failed.",
////                        Toast.LENGTH_SHORT).show()
////                    updateUI(null)
////                }
////            }
////        // [END create_user_with_email]
////    }
////
////    private fun signIn(email: String, password: String) {
////        // [START sign_in_with_email]
////        auth.signInWithEmailAndPassword(email, password)
////            .addOnCompleteListener(this) { task ->
////                if (task.isSuccessful) {
////                    // Sign in success, update UI with the signed-in user's information
////                    Log.d(TAG, "signInWithEmail:success")
////                    val user = auth.currentUser
////                    updateUI(user)
////                } else {
////                    // If sign in fails, display a message to the user.
////                    Log.w(TAG, "signInWithEmail:failure", task.exception)
////                    Toast.makeText(baseContext, "Authentication failed.",
////                        Toast.LENGTH_SHORT).show()
////                    updateUI(null)
////                }
////            }
////        // [END sign_in_with_email]
////    }
////
////    private fun sendEmailVerification() {
////        // [START send_email_verification]
////        val user = auth.currentUser!!
////        user.sendEmailVerification()
////            .addOnCompleteListener(this) { task ->
////                // Email Verification sent
////            }
////        // [END send_email_verification]
////    }
////
////    private fun updateUI(user: FirebaseUser?) {
////
////    }
////
////    private fun reload() {
////
////    }
////
////    companion object {
////        private const val TAG = "EmailPassword"
////    }
////}
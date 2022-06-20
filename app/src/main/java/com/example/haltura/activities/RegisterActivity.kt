package com.example.haltura.activities
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
//import com.example.haltura.Helpers.Validation.Companion.registerValid
//import com.example.haltura.Helpers.Validation.Companion.signUpValid
//import com.example.haltura.Sql.Items.Address
//import com.example.haltura.Sql.Items.User
//import com.example.haltura.Sql.Items.UserSerializable
//import com.example.haltura.Sql.UserOpenHelper
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//
//class RegisterActivity : AppCompatActivity() {
//    private lateinit var etEmail: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var etConfirmPassword: EditText
//    private lateinit var btnSignUp: Button
//    var helper = UserOpenHelper(this)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
//        etEmail = findViewById<View>(R.id.et_Email) as EditText
//        etPassword = findViewById<View>(R.id.et_Password) as EditText
//        etConfirmPassword = findViewById<View>(R.id.et_ConfirmPassword) as EditText
//        btnSignUp = findViewById<View>(R.id.btn_SignUp) as Button
//    }
////    fun signUp(view: View) {
////        if (registerValid(
////                etPassword, etConfirmPassword,
////                etEmail
////            )
////        ) {
////            //todo: Admin user
////           var email =  etEmail!!.text.toString()
////            var password =etPassword!!.text.toString()
////            helper.createUser(email, password)
////        }
////    }
//}

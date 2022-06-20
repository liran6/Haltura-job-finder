package com.example.haltura.activities
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import com.example.haltura.Helpers.Validation.Companion.resetPasswordValid
//import com.example.haltura.R
//import com.example.haltura.Sql.UserOpenHelper
//
//class ForgotYourPasswordActivity : AppCompatActivity() {
//    private lateinit var etEmailForgotPassword: EditText
//    private lateinit var btnForgotMyPassword: Button
//    private lateinit var helper: UserOpenHelper
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_forgot_your_password)
//        helper = UserOpenHelper(this)
//        etEmailForgotPassword = findViewById<View>(R.id.et_EmailForgotPassword) as EditText
//        btnForgotMyPassword = findViewById<View>(R.id.btn_ForgotMyPassword) as Button
//    }
//
//    fun resetPassword(view: View)
//    {
//        resetPasswordValid(etEmailForgotPassword)
//        helper.resetPassword(etEmailForgotPassword)
//
//    }
//}
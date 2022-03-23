package com.example.haltura.activities

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.haltura.R
import com.example.haltura.Sql.UserOpenHelper


class LoginActivity : AppCompatActivity() {
    private lateinit var etUserName: EditText
    private lateinit var etPassword: EditText
    private lateinit var login: Button
    private lateinit var helper: UserOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        helper = UserOpenHelper(this)
        etUserName = findViewById<View>(R.id.etUserName) as EditText
        etPassword = findViewById<View>(R.id.etPassword) as EditText
        login = findViewById<View>(R.id.btn_login) as Button
    }

    fun login(view: View)
    {
        if (helper.checkUser(etUserName.text.toString(), etPassword.text.toString())) {
            UserData.currentUser = helper.getUserByUsername(etUserName.text.toString())
            if (UserData.currentUser != null) {
                //                     Toast.makeText(LogInActivity.this, UserData.currentUser.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "login complete!", Toast.LENGTH_SHORT).show()
                val mainIntent: Intent
                mainIntent = if (UserData.isAdmin()) {
                    Intent(this, LoginActivity::class.java)
                } else {
                    Intent(this, MainActivity::class.java)
                }
                startActivity(mainIntent)
                finish()
            }
        } else {
            Toast.makeText(
                this,
                "user or password incorrect!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    fun register(view: View)
    {
        startActivity(Intent(this, SignUpActivity::class.java))
    }
    fun forgotPassword(view: View)
    {
        startActivity(Intent(this, ForgotYourPasswordActivity::class.java))
    }
}
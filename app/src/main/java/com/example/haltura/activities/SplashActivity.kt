package com.example.haltura.activities
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haltura.Sql.UserOpenHelper

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var helper: UserOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        helper = UserOpenHelper(this)
        if(!(helper.isUserLoggedIn())){
            startActivity(Intent(this, LoginActivity::class.java)) //todo: change
        }
        finish()
    }
}
package com.example.haltura.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haltura.Sql.UserOpenHelper

class SplashActivity : AppCompatActivity() {
    private lateinit var helper: UserOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        helper = UserOpenHelper(this)
        if(!(helper.isUserLoggedIn())){
            startActivity(Intent(this, SignInActivity::class.java)) //todo: change
        }
        finish()
    }
}
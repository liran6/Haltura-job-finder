package com.example.haltura.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.haltura.R
import com.example.haltura.Sql.UserOpenHelper
import com.example.haltura.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var helper: UserOpenHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = UserOpenHelper(this)
//        auth = FirebaseAuth.getInstance()
//
//        logoutBtn.setOnClickListener {
//            auth.signOut()
//            Toast.makeText(baseContext,"Logged Out", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this,LoginActivity::class.java))
//            finish()
//        }
    }

    fun signOut(view: View)
    {
        helper.signOut()
    }

    fun chat(view: View)
    {
        //helper.chats() // todo: should not be in user open helper
        this.startActivity(Intent(this, ChatsActivity::class.java))
    }

    fun MoveToBusiness(view: View)
    {
        //helper.moveToBusiness() // todo: should not be in user open helper
        this.startActivity(Intent(this, BusinessAccountActivity::class.java))
    }

}
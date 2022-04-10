package com.example.haltura.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.haltura.R
import com.example.haltura.Sql.UserOpenHelper

class BusinessAccountActivity : AppCompatActivity() {
    private lateinit var helper: UserOpenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_account)
        helper = UserOpenHelper(this)
    }

    fun addWork(view: View)
    {
        //helper.addWork() // todo: should not be in user open helper
        //this.startActivity(Intent(this, AddWorkActivity::class.java))
    }
}
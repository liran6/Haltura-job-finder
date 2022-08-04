package com.example.haltura.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.haltura.AppNotifications
import com.example.haltura.R
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.Preferences
import com.example.haltura.Utils.Preferences.set
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.LoginViewModel
import com.example.haltura.databinding.ActivityMain2Binding
import com.google.android.material.appbar.MaterialToolbar


class MainActivity2 : AppCompatActivity() {

    internal lateinit var binding: ActivityMain2Binding
    private lateinit var preferences: SharedPreferences
    private lateinit var supportActionBar: MaterialToolbar
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = Preferences.customPrefs(this, Const.loginPreferences)
        val toastObserver = Observer<String> { message ->
            AppNotifications.toastBar(this, message)
        }

        val logOutObserver = Observer<Boolean> { value ->
            if (value || !(preferences.getBoolean(Const.IsLoggedIn,false))) {
                preferences.set(Const.IsLoggedIn, false)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }

        }

        observersInit(toastObserver, logOutObserver)
        loginViewModel.getCurrentUser()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar = findViewById(R.id.toolbar)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_work,
                R.id.navigation_calendar,
                R.id.navigation_chats//R.id.navigation_home,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //preventing double click on nav menu item
        navView.setOnItemSelectedListener {item->
            if (navController.currentDestination?.id == item.itemId ){
                false
            } else {
                onNavDestinationSelected(
                    item,
                    navController
                )
            }
        }
    }

    private fun actionBarObservers() {
        supportActionBar.setNavigationOnClickListener {
            this.onBackPressed()
        }

        supportActionBar.title = "Welcome" + UserData.currentUser?.email



        supportActionBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                else -> false
            }
        }
    }

    private fun observersInit(
        toastObserver: Observer<String>,
        logOutObserver: Observer<Boolean>
    ) {
        loginViewModel.mutableMessageToasting.observe(this, toastObserver)
        loginViewModel.mutableLogout.observe(this, logOutObserver)
    }


    //remove focus from edit texts
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}

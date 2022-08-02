package com.example.haltura.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.haltura.AppNotifications
import com.example.haltura.R
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.Preferences
import com.example.haltura.Utils.Preferences.set
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.LoginViewModel
import com.example.haltura.databinding.ActivityManageBinding
import com.google.android.material.appbar.MaterialToolbar

class ManageActivity : AppCompatActivity() {

    internal lateinit var binding: ActivityManageBinding
    private lateinit var preferences: SharedPreferences
    //private lateinit var user: UserSerializable
    private lateinit var supportActionBar: MaterialToolbar
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = Preferences.customPrefs(this, Const.loginPreferences)
        val toastObserver = Observer<String> { message ->
            AppNotifications.toastBar(this, message)
        }
        //todo: check if admin and change nav
        //todo implement logout here!!!!
        val logOutObserver = Observer<Boolean> { value ->
            if (value || !(preferences.getBoolean(Const.IsLoggedIn,false))) {
                preferences.set(Const.IsLoggedIn, false)
                //this.onBackPressed()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }

        }

        observersInit(toastObserver, logOutObserver)
        loginViewModel.getCurrentUser()
        binding = ActivityManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar = findViewById(R.id.toolbar)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_manage)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_manage_profiles,
                R.id.navigation_manage_works,
                R.id.navigation_reports,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //preventing double click on nav menu item
        navView.setOnItemSelectedListener {item->
            if (navController.currentDestination?.id == item.itemId ){
                false
            } else {
                NavigationUI.onNavDestinationSelected(
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
}
//    : AppCompatActivity() {
//
//    private lateinit var binding: ActivityManageBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityManageBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_manage)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_manage_profiles, R.id.navigation_manage_works, R.id.navigation_reports
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//}
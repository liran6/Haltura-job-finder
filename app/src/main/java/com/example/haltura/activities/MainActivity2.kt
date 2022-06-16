package com.example.haltura.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.haltura.AppNotifications
import com.example.haltura.R
import com.example.haltura.Sql.Items.UserSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.Preferences
import com.example.haltura.Utils.Preferences.set
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.LoginViewModel
import com.example.haltura.databinding.ActivityMain2Binding
import com.kizitonwose.calendarview.model.DayOwner


class MainActivity2 : AppCompatActivity() {

    internal lateinit var binding: ActivityMain2Binding
    private lateinit var preferences: SharedPreferences
    private lateinit var user: UserSerializable
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = Preferences.customPrefs(this,Const.loginPreferences)
        val toastObserver = Observer<String> { message ->
//            loadingScreen.visibility = View.GONE
            AppNotifications.toastBar(this, message)
        }
        //todo implement logout here!!!!
        val logOutObserver = Observer<Boolean> { value ->
//            loadingScreen.visibility = View.GONE
            if (value){
                preferences.set(Const.IsLoggedIn,false)
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }

        }
        observersInit(toastObserver,logOutObserver)
        loginViewModel.getCurrentUser()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Welcome back "+UserData.currentUser?.email



//
//        this.onBackPressedDispatcher.addCallback(
//            this,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    ac
//                }
//            })

        //user = intent.getParcelableExtra(Const.USER_OBJECT)!!
        val navView: BottomNavigationView = binding.navView
//        navView.setOnNavigationItemSelectedListener { item ->
//            navController.navigate(item.itemId, args)
//            true
//        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_work, R.id.navigation_calendar , R.id.navigation_chats//R.id.navigation_home,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //todo: add menu of logout and profile
        //todo: change stay login to just token - func of getCurrent and check if token still valid
    }
    private fun observersInit(
        toastObserver: Observer<String>,
        logOutObserver: Observer<Boolean>
    ) {
        loginViewModel.mutableMessageToasting.observe(this, toastObserver)
        loginViewModel.mutableLogout.observe(this, logOutObserver)
    }

//    override fun onStart() {
//        super.onStart()
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Welcome back "+UserData.currentUser?.email
//
//    }
}
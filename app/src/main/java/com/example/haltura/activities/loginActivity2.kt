package com.example.haltura.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.haltura.Fragments.LoginFragment
import com.example.haltura.Menagment.Preferences
import com.example.haltura.R
import com.example.haltura.Utils.Const
import com.example.haltura.ViewModels.LoginViewModel
import io.reactivex.disposables.CompositeDisposable

class loginActivity2 : AppCompatActivity() {
    lateinit var loadingScreen: RelativeLayout
    private var compositeDisposable = CompositeDisposable()
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_2)
        loading()
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        Preferences.init(this)
//        ServiceBuilder.updateRetrofit(DbConstants.SERVER_URL)
//        createChannelForNotification()
//        stayLoggedIn()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container,LoginFragment.newInstance())
                .commitNow()
        }
//        initObservers()
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    private fun loading() {
        loadingScreen = findViewById(R.id.loading_screen)
    }

//    private fun createChannelForNotification() {
//        NotificationHelper.createNotificationChannel(
//            this, true, getString(R.string.app_name), NotificationManagerCompat.IMPORTANCE_HIGH
//        )
//    }

//    private fun stayLoggedIn() {
//        if (AppPreferences.stayLoggedIn) {
//            //  run the background service (it has to run from the application for one time so it'll
//            //  be able to tun when the device reboots
//            AlarmScheduler.runBackgroundService(this)
//            loadingScreen.visibility = View.VISIBLE
//            _viewModel.loginUser(AppPreferences.email, AppPreferences.password)
//        }
//    }

//    private fun fragmentInit(savedInstanceState: Bundle?) {
//
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.login_fragment,LoginFragment())
//                .addToBackStack(Const.splashFragment)
//                .commitNow()
//        }
//
////        if (savedInstanceState == null) {
////            val fragmentTransaction: FragmentTransaction =
////                supportFragmentManager.beginTransaction()
////            fragmentTransaction.add(
////                R.id.login_fragment,
////                SplashScreenFragment()
////            )
////            fragmentTransaction.addToBackStack(DbConstants.SPLASH_FRAGMENT_ID)
////            fragmentTransaction.commit()
//        }


}
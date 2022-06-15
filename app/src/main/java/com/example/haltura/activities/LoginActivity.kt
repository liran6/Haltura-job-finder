package com.example.haltura.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.haltura.AppNotifications.toastBar
import com.example.haltura.Fragments.SignInFragments.LoginFragment
import com.example.haltura.Utils.Preferences
import com.example.haltura.R
import com.example.haltura.Sql.Items.UserLoginSerializable
import com.example.haltura.Sql.Items.UserObject
import com.example.haltura.Sql.Items.UserSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.Preferences.get
import com.example.haltura.Utils.Preferences.set
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.LoginViewModel
import com.google.gson.Gson

import io.reactivex.disposables.CompositeDisposable


class LoginActivity : AppCompatActivity() {
    lateinit var loadingScreen: RelativeLayout
    private lateinit var sharedPreferences:SharedPreferences
    private var compositeDisposable = CompositeDisposable()
    private val userEmail = Const.Email
    private val userPassword = Const.Password
    private val isUserLoggedIn = Const.IsLogin
    private val viewModel: LoginViewModel by viewModels()

    //private lateinit var viewModel: LoginViewModel
    //private val sharedPreferences:Preferences
    private var json = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadingScreen = findViewById(R.id.loading_screen)
        sharedPreferences=Preferences.customPrefs(this, Const.loginPreferences)
//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        isLoggedIn()
        val toastObserver = Observer<String> { message ->
            loadingScreen.visibility = View.GONE
            toastBar(this, message)
        }

        val authObserver = Observer<UserSerializable> { authObserver ->
            sharedPreferences.set(userEmail, authObserver.email)
            sharedPreferences.set(userPassword, authObserver.password)
            sharedPreferences.set(isUserLoggedIn, true)
            loggingIn(authObserver)
        }

//        val nameObserver = Observer<String> { newName ->
//            // Update the UI, in this case, a TextView.
//            loadingScreen.visibility = View.GONE
//            snackBar(this, newName)
//        }
//        viewModel.mutableMessageToasting.observe(this, nameObserver)
        //Preferences.init(this)


        observersInit(toastObserver, authObserver)
//        ServiceBuilder.updateRetrofit(DbConstants.SERVER_URL)
//        createChannelForNotification()
//        stayLoggedIn()//todo: implement
        if (savedInstanceState == null) {
            //val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            supportFragmentManager.beginTransaction()
                .add(R.id.login_fragment, LoginFragment(), Const.login_fragment)
                .addToBackStack(Const.login_fragment)
                .setReorderingAllowed(true)
                .commit()
        }

    }

    private fun isLoggedIn() {
        if (sharedPreferences.get(isUserLoggedIn)) {
            loadingScreen.visibility = View.VISIBLE
            val user = UserLoginSerializable(
                sharedPreferences.get(userEmail), sharedPreferences.get(userPassword)
            )
            viewModel.userSignIn(user)
        }
    }

    private fun observersInit(
        toastObserver: Observer<String>,
        authObserver: Observer<UserSerializable>
    ) {
        viewModel.mutableMessageToasting.observe(this, toastObserver)
        viewModel.mutableUserHolder.observe(this, authObserver)
    }
//        viewModel.mutableMessageToasting.observe(
//            this,
//            Observer { toastMessage ->
//                toastMessage?.let {
//                    //  hide loading screen
//                    loadingScreen.visibility = View.GONE
//                    snackBar(this, toastMessage)
//                }
//            })
//
//        viewModel.mutableUserHolder.observe(
//            this,
//            Observer { user ->
//                user?.let {
////                    var res = response.body()?.string()
////                    var user = json.fromJson(res, UserSerializable::class.java)
//                    loggingIn(user)
//                }
//            })
//    }


    private fun loggingIn(user: UserSerializable) {
        val intent = Intent(this, MainActivity2::class.java)
        //val userBundle = Bundle()
        // intent.putExtra(Const.Logged_User, user)
        UserData.currentUser = UserObject(
            user.id,
            user.email,
            user.token,
            null,
            null
        )// add in node server profile info

        val bundle = Bundle()
        bundle.putParcelable(Const.USER_OBJECT, user)
        intent.putExtras(bundle)
        //  hide loading screen
        loadingScreen.visibility = View.GONE
        startActivity(intent)
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
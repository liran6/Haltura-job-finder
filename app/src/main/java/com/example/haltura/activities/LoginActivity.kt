package com.example.haltura.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.haltura.AppNotifications.toastBar
import com.example.haltura.Fragments.SignInFragments.LoginFragment
import com.example.haltura.Utils.Preferences
import com.example.haltura.R
import com.example.haltura.Sql.Items.UserObject
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.Preferences.get
import com.example.haltura.Utils.Preferences.set
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.LoginViewModel
import com.google.gson.Gson
//TODO : CHECK DISPOSABLE (in on stop to)
//import io.reactivex.disposables.CompositeDisposable


class LoginActivity : AppCompatActivity() {
    lateinit var loadingScreen: RelativeLayout
    private lateinit var preferences:SharedPreferences
    //private var compositeDisposable = CompositeDisposable()
    private lateinit var userObject:UserObject
    private val loginViewModel: LoginViewModel by viewModels()

    //private lateinit var viewModel: LoginViewModel
    //private val sharedPreferences:Preferences
    private var json = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadingScreen = findViewById(R.id.loading_screen)
        //sharedPreferences=Preferences
        preferences= Preferences.customPrefs(this, Const.loginPreferences)
//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        isLoggedIn()
        val toastObserver = Observer<String> { message ->
            loadingScreen.visibility = View.GONE
            toastBar(this, message)
        }

        val authObserver = Observer<UserObject> { authObserver ->
//            sharedPreferences.set(userEmail, authObserver.email)
//            sharedPreferences.set(userPassword, authObserver.password)
//            sharedPreferences.set(userToken, authObserver.token)
//            sharedPreferences.set(userId, authObserver.id)

            preferences.set(Const.Logged_User,authObserver)
            preferences.set(Const.IsLoggedIn, true)
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
        if (preferences.get(Const.IsLoggedIn)) {
            loadingScreen.visibility = View.VISIBLE
            val us:String = preferences.get(Const.Logged_User)
            val user = json.fromJson(us, UserObject::class.java)
                //UserLoginSerializable(preferences.get(userEmail), preferences.get(userPassword))
            loggingIn(user)
        }
    }

    private fun observersInit(
        toastObserver: Observer<String>,
        authObserver: Observer<UserObject>
    ) {
        loginViewModel.mutableMessageToasting.observe(this, toastObserver)
        loginViewModel.mutableUserHolder.observe(this, authObserver)
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


    private fun loggingIn(user: UserObject) {
        var intent = Intent(this, MainActivity2::class.java)
        if (user.isAdmin)
        {
            intent = Intent(this, ManageActivity::class.java)
        }
//        else
//        {
//            val intent = Intent(this, MainActivity2::class.java)
//        }
        //val intent = Intent(this, ManageActivity::class.java)//val intent = Intent(this, MainActivity2::class.java)
        //val userBundle = Bundle()
        // intent.putExtra(Const.Logged_User, user)
        UserData.currentUser = user// add in node server profile info

        val bundle = Bundle()
        bundle.putParcelable(Const.USER_OBJECT, user)
        intent.putExtras(bundle)
        //  hide loading screen
        loadingScreen.visibility = View.GONE
        startActivity(intent)
    }

    override fun onStop() {
        //compositeDisposable.clear()
        super.onStop()
    }

    private fun loading() {
        loadingScreen = findViewById(R.id.loading_screen)
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
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


class LoginActivity : AppCompatActivity() {
    lateinit var loadingScreen: RelativeLayout
    private lateinit var preferences:SharedPreferences
    private lateinit var userObject:UserObject
    private val loginViewModel: LoginViewModel by viewModels()

    private var json = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadingScreen = findViewById(R.id.loading_screen)
        preferences= Preferences.customPrefs(this, Const.loginPreferences)
        isLoggedIn()
        val toastObserver = Observer<String> { message ->
            loadingScreen.visibility = View.GONE
            toastBar(this, message)
        }

        val authObserver = Observer<UserObject> { authObserver ->

            preferences.set(Const.Logged_User,authObserver)
            preferences.set(Const.IsLoggedIn, true)
            loggingIn(authObserver)
        }


        observersInit(toastObserver, authObserver)
        if (savedInstanceState == null) {
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

    private fun loggingIn(user: UserObject) {
        var intent = Intent(this, MainActivity2::class.java)
        if (user.isAdmin)
        {
            intent = Intent(this, ManageActivity::class.java)
        }

        UserData.currentUser = user// add in node server profile info

        val bundle = Bundle()
        bundle.putParcelable(Const.USER_OBJECT, user)
        intent.putExtras(bundle)
        //  hide loading screen
        loadingScreen.visibility = View.GONE
        startActivity(intent)
    }

    override fun onStop() {
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

}
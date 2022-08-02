package com.example.haltura.activities

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.haltura.AppNotifications
import com.example.haltura.Fragments.ProfileFragments.SettingsFragment
import com.example.haltura.ViewModels.SettingsViewModel
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.R
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.ProfileData


class SettingsActivity : AppCompatActivity() {
    private val _viewModel: SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.settings_fragment, SettingsFragment(),Const.settings_fragment)
                .addToBackStack(Const.settings_fragment)
                .commit()
        }
        val toastObserver = Observer<String> { message ->
            AppNotifications.toastBar(this, message)
        }
        val profileObserver = Observer<ProfileSerializable>{ data->
            ProfileData.currentProfile = data
        }
        observersInit(toastObserver,profileObserver)
        val actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun observersInit(toastObserver: Observer<String>,profileObserver:Observer<ProfileSerializable>) {
        _viewModel.mutableMessageToasting.observe(this, toastObserver)
        _viewModel.mutableProfileHolder.observe(this, profileObserver)

    }
    // this event will enable the back
    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //finish()
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack(null, 0)
            val index =
                supportFragmentManager.backStackEntryCount - 2
            val backEntry = supportFragmentManager.getBackStackEntryAt(index)
//            backEntry.name?.let { updateNavBarIcons(it) }
        } else {
            finish()
            super.onBackPressed()
        }
    }

    //TODO: move functions to other seperated model

    fun SetProfileImage(view: View){
        var x = 1
    }
    //TODO ---------------------------------------------------------------------------

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
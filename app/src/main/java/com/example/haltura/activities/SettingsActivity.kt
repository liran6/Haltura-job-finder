package com.example.haltura.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.haltura.AppNotifications
import com.example.haltura.Fragments.ProfileFragments.SettingsFragment
import com.example.haltura.Fragments.ProfileFragments.SettingsViewModel
import com.example.haltura.R
import com.example.haltura.Sql.Items.UserObject
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.Preferences
import com.example.haltura.ViewModels.LoginViewModel
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity


class SettingsActivity : AppCompatActivity() {
    private val _ViewModel: SettingsViewModel by viewModels()
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
        observersInit(toastObserver)
        val actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun observersInit(toastObserver: Observer<String>) {
        _ViewModel.mutableMessageToasting.observe(this, toastObserver)
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

}
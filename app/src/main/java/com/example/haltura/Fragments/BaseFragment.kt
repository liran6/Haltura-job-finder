package com.example.haltura.Fragments

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.haltura.Fragments.ProfileFragments.SettingsFragment
import com.example.haltura.R
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.WorkData
import com.example.haltura.activities.AddWorkActivity
import com.example.haltura.activities.MainActivity2
import com.example.haltura.activities.SettingsActivity
import com.google.android.material.appbar.MaterialToolbar

interface HasToolbar {
    val toolbar: Toolbar? // Return null to hide the toolbar
}

interface BackButton
interface ProfileSettingsButton

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {
    val hasBackButton = this is BackButton
    val hasProfileSettingsButton = this is ProfileSettingsButton
    val homeActivityToolbar: MaterialToolbar
        get() = (requireActivity() as MainActivity2).binding.toolbar

    override fun onStart() {
        super.onStart()
        if (this is HasToolbar) {
            homeActivityToolbar.makeGone()
            (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        }
        else {

            if (hasBackButton) {
                val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
                actionBar?.setDisplayHomeAsUpEnabled(true)

                homeActivityToolbar.setNavigationOnClickListener {
                    if (activity != null) {
                        (requireActivity() as AppCompatActivity).onBackPressed()
                    }
                }
            }
            if (titleRes != null) {
                homeActivityToolbar.title = titleRes
            }


            }

            setHasOptionsMenu(true)




//        if (this is HasBackButton) {
////            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
//            actionBar?.title = if (titleRes != null) titleRes!! else ""
//            actionBar?. setDisplayHomeAsUpEnabled(true)
//
//        }
    }

    override fun onStop() {
        super.onStop()
        if (this is HasToolbar) {
            homeActivityToolbar.makeVisible()
            (requireActivity() as AppCompatActivity).setSupportActionBar(homeActivityToolbar)
        }

        if (this is BackButton) {
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            //actionBar?.title = context?.getString(R.string.app_name)
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.profile_settings)
        item.isVisible = hasProfileSettingsButton
    }

    //create the options of the appbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile_settings -> {
                // navigate to settings screen
                val intent = Intent(activity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
//            R.id.editButon -> {
//                // save profile changes
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateOptionsMenu() {
        //isEditing = !isEditing
        requireActivity().invalidateOptionsMenu()
    }

    abstract val titleRes: String?
}

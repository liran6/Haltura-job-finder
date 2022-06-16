package com.example.haltura.Fragments.CalendarFragments

import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.haltura.R
import com.example.haltura.activities.MainActivity2

interface HasToolbar {
    val toolbar: Toolbar? // Return null to hide the toolbar
}

interface HasBackButton

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    val homeActivityToolbar: Toolbar
        get() = (requireActivity() as MainActivity2).binding.toolbar

    override fun onStart() {
        super.onStart()
        if (this is HasToolbar) {
            homeActivityToolbar.makeGone()
            (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        }

        if (this is HasBackButton) {
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.title = if (titleRes != null) titleRes!! else ""
            actionBar?. setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onStop() {
        super.onStop()
        if (this is HasToolbar) {
            homeActivityToolbar.makeVisible()
            (requireActivity() as AppCompatActivity).setSupportActionBar(homeActivityToolbar)
        }

        if (this is HasBackButton) {
            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            //actionBar?.title = context?.getString(R.string.app_name)
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            if (activity != null) {
                activity?.onBackPressed()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    abstract val titleRes: String?
}

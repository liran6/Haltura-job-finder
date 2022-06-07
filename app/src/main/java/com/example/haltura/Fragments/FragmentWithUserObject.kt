package com.example.haltura.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.haltura.Sql.Items.UserObject

abstract class FragmentWithUserObject : Fragment(){
    protected lateinit var userObject: UserObject

    protected fun setUserObjectInIntent(intent: Intent) {
        val userBundle = Bundle()
        userBundle.putParcelable("USER_OBJECT", userObject)
        intent.putExtra("USER_BUNDLE", userBundle)
    }
}
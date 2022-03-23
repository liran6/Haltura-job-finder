package com.example.haltura.Helpers

import com.example.haltura.Sql.Items.User

object UserData {
    lateinit var currentUser: User

//    todo: should be "static" when login should enter user here
    fun isAdmin(): Boolean {
        return currentUser.getIsAdmin()!! || currentUser.getUserName() == "Admin"
    }
}

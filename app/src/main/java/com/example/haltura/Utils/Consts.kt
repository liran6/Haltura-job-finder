package com.example.haltura.Utils

import androidx.paging.RemoteMediator

object Const{
    //app ui
    const val LoginFragment = "LoginFragment"
    //preferences

    const val loginPreferences = "LoginPreferences"
    const val isLogin = "is_login"
    const val email = "email"
    const val password = "password"
    const val EmptyStringValue = ""
    const val showNotifications = "showNotifications"

    // server
    const val SERVER_URL = "http://10.0.0.8:4000/"
    //Login - Register
    const val Connecting_Error = "Server connection failed, please try again"
    const val Registration_Success = "User registered successfully!"
    const val Logged_User = "Logged User"
    const val Token_Error = "There was a problem signing you in, please try again"
    const val Signing_In = "Signing In"
    const val Email_Password_incorrect = "Your Email or Password is incorrect"


    //snack
    const val Snack_Bar = "snackBar"
    const val Action_Snack_Bar = "actionSnackBar"
    const val Success_Snack_Bar = "successSnackBar"

}
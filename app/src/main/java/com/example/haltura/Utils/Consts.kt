package com.example.haltura.Utils

import androidx.paging.RemoteMediator

object Const{


    //DataObjects
    const val LOGGED_USER_BUNDLE = "LOGGED_USER_BUNDLE"
    const val USER_OBJECT = "USER_OBJECT"
    const val WORK_OBJECT = "WORK_OBJECT"
    const val PROFILE_OBJECT = "Profile_OBJECT"
    //app ui
    const val LoginFragment = "LoginFragment"
    //preferences

    const val loginPreferences = "LoginPreferences"
    const val IsLoggedIn = "is_logged_in"
    const val Email = "email"
    const val Password = "password"
    const val Id = "Id"
    const val Token = "token"
    const val EmptyStringValue = ""
    const val showNotifications = "showNotifications"

    // server Omer adress = "http://192.168.1.239:4000/"
    const val SERVER_URL = "http://192.168.1.239:4000/"
    //Login - Register
    const val Connecting_Error = "Server connection failed, please try again"
    const val Registration_Success = "User registered successfully!"
    const val Logged_User = "Logged User"
    const val Email_Is_Taken = "Email address is already in use."
    const val Token_Error = "There was a problem signing you in, please try again"
    const val INVALID_TOKEN = "Your Session Has Timed Out, please login again"
    const val Signing_In = "Signing In"
    const val Email_Password_incorrect = "Your Email or Password is incorrect"
    const val login_fragment = "login_fragment"
    const val signup_fragment = "signup_fragment"
    const val settings_fragment = "settings_fragment"
    const val profile_fragment = "profile_fragment"
    //action bar titles
    const val home_title = "snackBar"


    //snack
    const val Snack_Bar = "snackBar"
    const val Action_Snack_Bar = "actionSnackBar"
    const val Success_Snack_Bar = "successSnackBar"
    const val AddWorkSuccess = "work Added successfully!"

    //calendar
    const val Calendar = "Calendar"

    //chats
    const val chat_fragment = "chat_fragment"
    const val profile_info = "profile_info"
    const val group_info = "group_info"

    //change user data
    const val CONF_PASSWORD_ERROR = "the passwords didn't match. please try again."
    const val password_changed = "Your Password was changed successfully!"
    const val email_changed = "Your Email address was changed successfully!"
    const val data_changed = "Your Data has saved successfully!"


}
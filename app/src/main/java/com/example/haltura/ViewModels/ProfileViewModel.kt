package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ProfileAPI
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.UsersAPI
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.Sql.Items.UserLoginSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.ProfileData
import com.example.haltura.Utils.UserData
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private var json = Gson()
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}
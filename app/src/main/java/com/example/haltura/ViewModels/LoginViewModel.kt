package com.example.haltura.ViewModels

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.UsersAPI
import com.example.haltura.Sql.Items.UserSerializable
import com.example.haltura.Utils.Const
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var json = Gson()


    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val mutableUserHolder: MutableLiveData<UserSerializable> by lazy {
        MutableLiveData<UserSerializable>()
    }


    fun createUser(email: String, password: String) {
        //todo: log this to server now
        var user = UserSerializable(
            email,
            Const.EmptyStringValue,
            Const.EmptyStringValue,
            Const.EmptyStringValue,
            password
        )
        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.createUser(user)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //todo save user and password for fast login
                    mutableMessageToasting.postValue(Const.Registration_Success)
                    userSignIn(user)
                } else {
                    var res = response.body()?.string()
                    var x = 1
                }
            }
        })
    }

    fun userSignIn(user: UserSerializable){

        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.userAuth(user)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val b = 1
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    var user = json.fromJson(res, UserSerializable::class.java)
                    if (user.token != "") {
                        mutableMessageToasting.postValue(Const.Signing_In)
                        mutableUserHolder.postValue(user)
                    } else {
                        mutableMessageToasting.postValue(Const.Token_Error)
                    }
                } else {
                    mutableMessageToasting.postValue(Const.Email_Password_incorrect)
                }
            }

        }
        )
    }


















    }
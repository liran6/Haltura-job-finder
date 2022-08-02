package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ProfileAPI
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.UsersAPI
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.Sql.Items.UserLoginSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var json = Gson()
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val mutableProfileHolder: MutableLiveData<ProfileSerializable> by lazy {
        MutableLiveData<ProfileSerializable>()
    }

    fun updateUserPassword(password: String) {
        val newPassword = UserLoginSerializable(null,null,password)
        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.updateUserPasswordInfo(UserData.currentUser!!.userId, "Bearer " + (UserData.currentUser?.token),newPassword)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)

                //todo logout
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mutableMessageToasting.postValue(Const.password_changed)
//                    Toast.makeText(
//                        activity, "User updated successfully ! ",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.INVALID_TOKEN)
                }
            }
        })
    }

    fun updateUserEmail(email: String) {
        val newEmail = UserLoginSerializable(email,null,null)
        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.updateUserPasswordInfo(UserData.currentUser!!.userId, "Bearer " + (UserData.currentUser?.token),newEmail)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)

                //todo logout
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mutableMessageToasting.postValue(Const.email_changed)
//                    Toast.makeText(
//                        activity, "User updated successfully ! ",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.INVALID_TOKEN)
                }
            }
        })
    }

    fun getCurrentProfile(){
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.getExtendedProfile( "Bearer " + (UserData.currentUser?.token), UserData.currentUser!!.userId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    var profileInfo = json.fromJson(res, ProfileSerializable::class.java)
                    mutableProfileHolder.postValue(profileInfo)

                    //mutableMessageToasting.postValue(Const.password_changed)
//                    Toast.makeText(
//                        activity, "User updated successfully ! ",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.INVALID_TOKEN)
                }
            }
        })
    }

    fun updateProfileData(profile:ProfileSerializable) {
        val newData = profile
        newData.username = null
        newData.email = null
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.updateProfileInfo(UserData.currentUser!!.userId, "Bearer " + (UserData.currentUser?.token),newData)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)

                //todo logout
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mutableMessageToasting.postValue(Const.data_changed)
//                    Toast.makeText(
//                        activity, "User updated successfully ! ",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.INVALID_TOKEN)
                }
            }
        })
    }
}
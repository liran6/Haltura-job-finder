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
    // TODO: Implement the ViewModel
    private var json = Gson()
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

//    fun updateProfileData(profile:ProfileSerializable) {
//        val newData = profile
//        newData.username = null
//        newData.email = null
//        val retroService =
//            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
//        val call = retroService.updateProfileInfo(UserData.currentUser!!.userId, "Bearer " + (UserData.currentUser?.token),newData)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                mutableMessageToasting.postValue(Const.Connecting_Error)
//
//                //todo logout
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    mutableMessageToasting.postValue(Const.data_changed)
////                    Toast.makeText(
////                        activity, "User updated successfully ! ",
////                        Toast.LENGTH_SHORT
////                    ).show()
//                    //var res = response.body()?.string()
//                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
//                    var x = 1
//                } else {
//                    mutableMessageToasting.postValue(Const.INVALID_TOKEN)
//                }
//            }
//        })
//    }
}
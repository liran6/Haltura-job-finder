package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ChatAPI
import com.example.haltura.Api.ProfileAPI
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Models.InfoChatSerializable
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowProfileInfoViewModel : ViewModel() {

    val mutableProfileInfo: MutableLiveData<ProfileSerializable> by lazy { //by lazy
        MutableLiveData<ProfileSerializable>(null)
    }

    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }//todo: connect it to activity /frag ?

    private var json = Gson()

    fun getProfileInfo(userId :String) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.getProfile("Bearer " +
                UserData.currentUser?.token!!, userId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    val chatInfo = json.fromJson(res, ProfileSerializable::class.java)
                    mutableProfileInfo.value = chatInfo
                }
                else {
                    mutableMessageToasting.postValue(Const.Token_Error)
                }
            }
        })
    }
}
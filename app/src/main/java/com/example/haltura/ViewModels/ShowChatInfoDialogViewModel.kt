package com.example.haltura.ViewModels

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ChatAPI
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Models.InfoChatSerializable
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowChatInfoDialogViewModel : ViewModel() {

    val mutableMembersList: MutableLiveData<MutableList<ProfileSerializable>> by lazy { //by lazy
        MutableLiveData<MutableList<ProfileSerializable>>(mutableListOf())
    }

    val mutableChatInfo: MutableLiveData<InfoChatSerializable> by lazy { //by lazy
        MutableLiveData<InfoChatSerializable>(null)
    }

    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }//todo: connect it to activity /frag ?

    private var json = Gson()


    fun getChatInfo(chatId :String) {
        mutableMembersList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(ChatAPI::class.java)
        val call = retroService.getChatInfo("Bearer " +
                UserData.currentUser?.token!!, chatId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    val chatInfo = json.fromJson(res, InfoChatSerializable::class.java)
                    mutableChatInfo.value = chatInfo
                    chatInfo.profileList?.let { mutableMembersList.value!!.addAll(it) }
                    mutableMembersList.notifyAllObservers()
                } else {
                    mutableMessageToasting.postValue(Const.Token_Error)
                }
            }
        })
    }

}
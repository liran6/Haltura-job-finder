package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ChatAPI
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Sql.Items.ChatSerializable
import com.example.haltura.Sql.Items.UserResponse
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatsViewModel : ViewModel() {

    val mutableChatsList: MutableLiveData<MutableList<ChatSerializable>> by lazy { //by lazy
        MutableLiveData<MutableList<ChatSerializable>>(mutableListOf())
    }

    lateinit var ChatApiLiveData: MutableLiveData<UserResponse?>
    private var json = Gson()


    fun getAllOfYourChats() {
        mutableChatsList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(ChatAPI::class.java)
        val call = retroService.getAllChatsOfUserId("Bearer " + UserData.currentUser?.token!!,
            UserData.currentUser?.userId!!)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                ChatApiLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val chats = jObject.get("chats_list") as JSONArray
                    for (i in 0 until chats.length())
                    {
                        val chat = json.fromJson(chats.getJSONObject(i).toString(), ChatSerializable::class.java)
                        mutableChatsList.value!!.add(chat)
                    }
                    mutableChatsList.notifyAllObservers()
                } else {
                    var x = 1
                }
            }
        })
    }

}
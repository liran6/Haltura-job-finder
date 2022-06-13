package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ChatAPI
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.MessageSerializable
import com.example.haltura.Sql.Items.UserResponse
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel : ViewModel() {

    val mutableMessagesList: MutableLiveData<MutableList<MessageSerializable>> by lazy { //by lazy
        MutableLiveData<MutableList<MessageSerializable>>(mutableListOf())
    }

    lateinit var ChatApiLiveData: MutableLiveData<UserResponse?>

    private var json = Gson()


    fun getAllMessages(charId : String) {
        mutableMessagesList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(ChatAPI::class.java)
        val call = retroService.getAllMessages("Bearer " + UserData.currentUser?.token!!,charId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //ChatApiLiveData.postValue(null)//todo:init
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val messages = jObject.get("messages") as JSONArray
                    //val works = jObject.get("work_list") as JSONArray
                    for (i in 0 until messages.length())
                    {
                        val message = json.fromJson(messages.getJSONObject(i).toString(), MessageSerializable::class.java)
                        mutableMessagesList.value!!.add(message)
                    }
                    mutableMessagesList.notifyAllObservers()
                } else {
                    var x = 1
                }
            }
        })
    }
}
package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ChatAPI
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Sql.Items.ChatSerializable
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

class ChatsViewModel : ViewModel() {

    val mutableChatsList: MutableLiveData<MutableList<ChatSerializable>> by lazy {
        MutableLiveData<MutableList<ChatSerializable>>(mutableListOf())
    }

    val mutableAllChatsList: MutableList<ChatSerializable> =mutableListOf()

    lateinit var ChatApiLiveData: MutableLiveData<UserResponse?>
    private var json = Gson()


    fun getAllOfYourChats() {
        mutableChatsList.value!!.clear()
        mutableAllChatsList.clear()
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
                        // var chat2 = json.fromJson(chats.getJSONObject(i).toString(), ExtendedChatSerializable::class.java)
                        mutableAllChatsList!!.add(chat)
                        //mutableChatsList.value!!.add(chat)
                    }
                    mutableChatsList.value!!.addAll(mutableAllChatsList)
                    mutableChatsList.notifyAllObservers()
                } else {
                    var x = 1
                }
            }
        })
    }

    fun filter(textToFilter: String) {
        if (textToFilter.toString().trim { it <= ' ' }.length == 0) // not empty
        {
            mutableChatsList.value!!.clear()
            mutableChatsList.value!!.addAll(mutableAllChatsList)
        }
        else
        {
            mutableChatsList.value!!.clear()
            mutableAllChatsList.forEach{

                if (it.chatName != null)
                {
                    if (it.chatName!!.toLowerCase().contains(textToFilter))
                    {
                        mutableChatsList.value!!.add(it)
                    }
                }
                else if(isUserNameContainsString(it,textToFilter))//name of the contact (not group)
                {
                    mutableChatsList.value!!.add(it)
                }
            }
        }
        mutableChatsList.notifyAllObservers()
    }

    private fun isUserNameContainsString(chat: ChatSerializable, textToFilter: String): Boolean {
        var members = chat.members
        if(members[0] != UserData.currentUser?.userId)
        {
            //todo: map to user name
            if (chat.mapUsernames[members[0]]!!.toLowerCase().contains(textToFilter))
            {
                return true
            }
            return false
        }
        return chat.mapUsernames[members[1]]!!.toLowerCase().contains(textToFilter)
    }
}






















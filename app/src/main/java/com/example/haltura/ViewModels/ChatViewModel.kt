package com.example.haltura.ViewModels

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Adapters.ChatAdapter2
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
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.SocketHandler
import com.example.haltura.activities.ChatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread


class ChatViewModel : ViewModel() {

    val mutableMessagesList: MutableLiveData<MutableList<MessageSerializable>> by lazy { //by lazy
        MutableLiveData<MutableList<MessageSerializable>>(mutableListOf())
    }


//    object AddressList: MutableLiveData<List<Address>>()
//    fun getAddressesLiveData(): LiveData<List<Address>> {
//        GlobalScope.launch {
//            withContext(Dispatchers.Main){ AddressList.value = getAddressList() }
//        }
//        return AddressList
//    }

    lateinit var ChatApiLiveData: MutableLiveData<UserResponse?>

    private var json = Gson()

    private lateinit var _chatId : String


    private lateinit var _socket: Socket

    private lateinit var _activity: Activity

    fun startLive(chatId :String, activity: Activity) {
        //SocketHandler.setSocket()
        _activity = activity
        _socket = IO.socket(Const.SERVER_URL)
        _socket.connect()
            .on("new-message") { args ->
                if (args[0] != null) {
                    var _mutableMessagesList = mutableMessagesList
                    var message = json.fromJson(args[0].toString(), MessageSerializable::class.java)

                    _mutableMessagesList.value!!.add(message)

                    //_mutableMessagesList.notifyAllObservers()
                    //_mutableMessagesList.postValue(_mutableMessagesList.value)

                    var activityThread = _activity
                    activityThread.runOnUiThread{
                        var _mutableMessagesList = mutableMessagesList
                        _mutableMessagesList.notifyAllObservers()
                    }
                    //_mutableMessagesList.notifyAllObservers()
                }
            }
        _chatId = chatId

        _socket.emit("user-connect", _chatId, UserData.currentUser?.userId)

//        _socket.on("new-message") { args ->
//            if (args[0] != null) {
//                var message = json.fromJson(args[0].toString(), MessageSerializable::class.java)
//                mutableMessagesList.value!!.add(message)
//                mutableMessagesList.notifyAllObservers()
//            }
//        }
    }

    fun SendMessage(message: MessageSerializable)
    {
        _socket.emit("send-message", _chatId, Gson().toJson(message))
        //var _mutableMessagesList = mutableMessagesList
//        _socket.on("new-message") { args ->
//            if (args[0] != null) {
//                var _mutableMessagesList = mutableMessagesList
//                var activity = _activity
//                var message = json.fromJson(args[0].toString(), MessageSerializable::class.java)
////                println(_mutableMessagesList)
////                println(mutableMessagesList)
//                _mutableMessagesList.value!!.add(message)
//                //_mutableMessagesList.postValue(_mutableMessagesList.value)
//                activity.runOnUiThread{
//                    var _mutableMessagesList = mutableMessagesList
//                    _mutableMessagesList.notifyAllObservers()
//                }
//                //_mutableMessagesList.notifyAllObservers()
//            }
//        }
    }


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
package com.example.haltura.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.UserResponse
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.UserData
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatchWorkViewModel : ViewModel() {

    lateinit var WorkApiLiveData: MutableLiveData<UserResponse?>

    lateinit var ChatApiLiveData: MutableLiveData<UserResponse?>

    fun registerToWork(work: WorkSerializable) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.addToWorkWorkIdUser("Bearer "+ UserData.currentUser?.userId!!,
            work.id!!,UserData.currentUser?.userId!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                WorkApiLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //crea
                    createChat(work)
                    //todo: add toast of register successfully
                } else {
                    //todo: print message in toast
                    var x = 1
                }
            }
        })
    }

    fun createChat(work: WorkSerializable) {
        //todo: add chat per work and when someone joins to the work add him to the chat
//        val retroService =
//            ServiceBuilder.getRetroInstance().create(ChatAPI::class.java)
//        val call = retroService.addToWorkWorkIdUser("Bearer "+ UserData.currentUser?.userId!!,
//            work.id,UserData.currentUser?.userId!!)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                WorkApiLiveData.postValue(null)
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    //crea
//                } else {
//                    //todo: print message in toast
//                    var x = 1
//                }
//            }
//        })
    }
}
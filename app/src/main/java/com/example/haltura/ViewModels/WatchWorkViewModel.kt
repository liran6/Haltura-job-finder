package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ChatAPI
import com.example.haltura.Api.ReportApi
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Models.ChatInfoSerializable
import com.example.haltura.Models.ReportSerializable
import com.example.haltura.Sql.Items.UserResponse
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatchWorkViewModel : ViewModel() {
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    lateinit var WorkApiLiveData: MutableLiveData<UserResponse?>

    lateinit var ChatApiLiveData: MutableLiveData<UserResponse?>

    fun registerToWork(work: WorkSerializable) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.putRegisterToWork("Bearer "+ UserData.currentUser?.token!!,
            work.id!!,UserData.currentUser?.userId!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                WorkApiLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //crea
                    createChat(work)
                } else {
                    var x = 1
                }
            }
        })
    }

    fun submitReport(workId: String,text :String) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(ReportApi::class.java)
        val call = retroService.submitReport("Bearer " +
                UserData.currentUser?.token!!,
            ReportSerializable(null,UserData.currentUser!!.userId,workId,text,null)
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mutableMessageToasting.postValue("The report was received")
                } else {
                    mutableMessageToasting.postValue(Const.Token_Error)
                }
            }
        })
    }


    //quitWork
    fun quitWork(workId: String, userId :String) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.removeFromWorkUserId("Bearer " +
                UserData.currentUser?.token!!, workId, userId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mutableMessageToasting.postValue("quit")
                } else {
                    mutableMessageToasting.postValue(Const.Token_Error)
                }
            }
        })
    }


    fun createChat(work: WorkSerializable) {
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
//                    var x = 1
//                }
//            }
//        })
    }
}
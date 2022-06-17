package com.example.haltura.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.AddresSerializable
import com.example.haltura.Sql.Items.UserSerializable
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddWorkViewModel : ViewModel() {

    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun createWork(work: WorkSerializable) {
        var user = UserData.currentUser
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.createWork("Bearer " + user?.token!!, user?.userId!!, work)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
                var a = 1
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    //val listType = object : TypeToken<List<String>>(){ }.type
                    //var buisness= json.fromJson(res,BusinessSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    //updateWork
    fun updateWork(work :WorkSerializable) {
        var user = UserData.currentUser
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.updateWork("Bearer " + user?.token!!, work?.id!!, work)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                var a =1
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    //val listType = object : TypeToken<List<String>>(){ }.type
                    //var buisness= json.fromJson(res,BusinessSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }
}
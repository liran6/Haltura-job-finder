package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
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

class WorkHistoryViewModel : ViewModel() {

    val mutableWorkHistoryList: MutableLiveData<MutableList<WorkSerializable>> by lazy {
        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
    }

    private var json = Gson()

    fun getWorkHistory() {
        mutableWorkHistoryList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getWorkHistoryOfUserId("Bearer " + UserData.currentUser?.token!!,
            UserData.currentUser!!.userId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //TODO: toast
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val works = jObject.get("work_list") as JSONArray
                    for (i in 0 until works.length())
                    {
                        val work = json.fromJson(works.getJSONObject(i).toString(), WorkSerializable::class.java)
                        mutableWorkHistoryList.value!!.add(work)
                    }
                    mutableWorkHistoryList.notifyAllObservers()
                } else {
                    //TODO: toast
                }
            }
        })
    }
}
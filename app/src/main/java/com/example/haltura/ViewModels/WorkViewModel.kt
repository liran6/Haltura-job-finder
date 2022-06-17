package com.example.haltura.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.UserResponse
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import okhttp3.internal.notify
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkViewModel : ViewModel() {

    val mutableWorkList: MutableLiveData<MutableList<WorkSerializable>> by lazy { //by lazy
        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
    }
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    lateinit var WorkApiLiveData: MutableLiveData<UserResponse?>
    private var json = Gson()


    fun getAllOfYourWorks() {
        mutableWorkList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getAllWorksThatUserIdPublished("Bearer " +
                UserData.currentUser?.token!!, UserData.currentUser?.userId!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //WorkApiLiveData.postValue(null)
                mutableMessageToasting.postValue(Const.Connecting_Error)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val works = jObject.get("work_list") as JSONArray
                    for (i in 0 until works.length())
                    {
                        val work = json.fromJson(works.getJSONObject(i).toString(), WorkSerializable::class.java)
                        mutableWorkList.value!!.add(work)
                    }
                    mutableWorkList.notifyAllObservers()
//                    var res = response.body()?.string()
//                    //val listType = object : TypeToken<List<String>>(){ }.type
//                    var work_list = json.fromJson(res, WorksList::class.java)
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.Token_Error)
                }
            }
        })
    }


    fun deleteWork(work: WorkSerializable) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.deleteWork("Bearer " + UserData.currentUser?.token!!,work.id!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //WorkApiLiveData.postValue(null)
                mutableMessageToasting.postValue(Const.Connecting_Error)

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mutableWorkList.value!!.remove(work) //to delete from the list
                    mutableWorkList.notifyAllObservers()
                    //todo: toast delete was successfully
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.Token_Error)
                    var x = 1
                }
            }
        })
    }

}
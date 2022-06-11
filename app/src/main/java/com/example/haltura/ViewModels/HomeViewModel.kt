package com.example.haltura.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.*
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



//class HomeViewModel : ViewModel() {
//
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
//}


class HomeViewModel : ViewModel() {

    val mutableWorkList: MutableLiveData<MutableList<WorkSerializable>> by lazy { //by lazy
        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
    }

    lateinit var WorkApiLiveData: MutableLiveData<UserResponse?>
    private var json = Gson()


    fun getAllWorks(token: String) {
        mutableWorkList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getAllWorks("Bearer $token")
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                WorkApiLiveData.postValue(null)
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
                    //mutableWorkList.notifyObserver()
//                    var res = response.body()?.string()
//                    //val listType = object : TypeToken<List<String>>(){ }.type
//                    var work_list = json.fromJson(res, WorksList::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

}


//class HomeViewModel : ViewModel() {
//    val mutableWorkList: MutableLiveData<MutableList<WorkSerializable>> by lazy {
//        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
//    }
//
//    lateinit var WorkApiLiveData: MutableLiveData<UserResponse?>
//    private var json = Gson()
//
//    //todo: make all of the things private with public link?
////    private val _text = MutableLiveData<String>().apply {
////        value = "This is home Fragment"
////    }
////    val text: LiveData<String> = _text
//
//
//    fun getAllWorks(token: String) {
//        val retroService =
//            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
//        val call = retroService.getAllWorks("Bearer $token")
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                WorkApiLiveData.postValue(null)
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    var res = response.body()?.string()
//                    //val listType = object : TypeToken<List<String>>(){ }.type
//                    var work_list = json.fromJson(res, WorksList::class.java)
//                    var x = 1
//                } else {
//                    var x = 1
//                }
//            }
//        })
//    }
//}
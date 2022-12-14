package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.*
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////  All Works  //////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    val mutableWorkList: MutableLiveData<MutableList<WorkSerializable>> by lazy {
        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
    }
    val mutableAllWorkList: MutableList<WorkSerializable> =mutableListOf()

    private var json = Gson()


    fun getAllWorks() {
        mutableWorkList.value!!.clear()
        mutableAllWorkList.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getAllWorks("Bearer " + UserData.currentUser?.token!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val works = jObject.get("work_list") as JSONArray
                    for (i in 0 until works.length())
                    {
                        val work = json.fromJson(works.getJSONObject(i).toString(), WorkSerializable::class.java)
                        mutableAllWorkList!!.add(work)
                        //mutableAllWorkList.value!!.add(work)
                    }
                    //mutableWorkList.notifyAllObservers()
                    mutableWorkList.value!!.addAll(mutableAllWorkList)
                    mutableWorkList.notifyAllObservers()
                } else {
                }
            }
        })
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////  Close Works  ////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    val mutableCloseWorksList: MutableLiveData<MutableList<WorkSerializable>> by lazy {
        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
    }

    fun getCloseWorks() {
        mutableCloseWorksList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getCloseWorksOfUserId("Bearer " + UserData.currentUser?.token!!,
        UserData.currentUser!!.userId)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val works = jObject.get("work_list") as JSONArray
                    for (i in 0 until works.length())
                    {
                        val work = json.fromJson(works.getJSONObject(i).toString(), WorkSerializable::class.java)
                        mutableCloseWorksList.value!!.add(work)
                    }
                    mutableCloseWorksList.notifyAllObservers()
                } else {
                }
            }
        })
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////  Recommended Works  //////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    val mutableRecommendedWorksList: MutableLiveData<MutableList<WorkSerializable>> by lazy {
        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
    }

    fun getRecommendedWorks() {
        mutableRecommendedWorksList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getAllWorks("Bearer " + UserData.currentUser?.token!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val works = jObject.get("work_list") as JSONArray
                    for (i in 0 until works.length())
                    {
                        val work = json.fromJson(works.getJSONObject(i).toString(), WorkSerializable::class.java)
                        mutableRecommendedWorksList.value!!.add(work)
                    }
                    mutableRecommendedWorksList.notifyAllObservers()
                } else {
                }
            }
        })
    }

    fun filter(textToFilter: String) {
        if (textToFilter.toString().trim { it <= ' ' }.length == 0) // not empty
        {
            mutableWorkList.value!!.clear()
            mutableWorkList.value!!.addAll(mutableAllWorkList)
        }
        else
        {
            mutableWorkList.value!!.clear()
            mutableAllWorkList.forEach{

                if (it.task != null)
                {
                    if (it.task!!.toLowerCase().contains(textToFilter))
                    {
                        mutableWorkList.value!!.add(it)
                    }
                }
            }
        }
        mutableWorkList.notifyAllObservers()
    }
}
package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageWorksViewModel : ViewModel() {
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////  Works List  /////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    val mutableWorkList: MutableLiveData<MutableList<WorkSerializable>> by lazy {
        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
    }
    val mutableAllWorkList: MutableList<WorkSerializable> =mutableListOf()

    private var json = Gson()


    fun getWorks() {
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
                    }
                    mutableWorkList.value!!.addAll(mutableAllWorkList)
                    mutableWorkList.notifyAllObservers()
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
                var flag = true
                if (it.task != null)
                {
                    if (it.task!!.toLowerCase().contains(textToFilter))
                    {
                        mutableWorkList.value!!.add(it)
                        flag = false
                    }
                }
                if (flag && it.id != null) // skip when added to list
                {
                    if (it.id!!.toLowerCase().contains(textToFilter))
                    {
                        mutableWorkList.value!!.add(it)
                    }
                }
            }
        }
        mutableWorkList.notifyAllObservers()
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
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.Token_Error)
                    var x = 1
                }
            }
        })
    }

}
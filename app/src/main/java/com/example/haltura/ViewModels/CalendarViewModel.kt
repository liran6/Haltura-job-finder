package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.UserResponse
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Sql.Items.WorksByDateMap
import com.example.haltura.Sql.Items.WorksList
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.DateTime
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class CalendarViewModel : ViewModel() {

    //Toasting
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    //Lists
    val mutableWorkList: MutableLiveData<MutableList<WorkSerializable>> by lazy {
        MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
    }

    val mutableWorksByDateList: MutableLiveData<MutableMap<LocalDate, MutableList<WorkSerializable>>> by lazy {
        MutableLiveData<MutableMap<LocalDate, MutableList<WorkSerializable>>>(mutableMapOf())
    }

    lateinit var WorkApiLiveData: MutableLiveData<UserResponse?>
    private var json = Gson()

    private fun jsonToMap(jsonO: JSONObject): MutableMap<LocalDate, MutableList<WorkSerializable>> {
        val map: MutableMap<LocalDate, MutableList<WorkSerializable>> = mutableMapOf()
        val keys = jsonO.names()
            for (i in 0 until keys.length()) {
                val key = keys.getString(i)
                val value = jsonO.getJSONArray(key)
                val works= mutableListOf<WorkSerializable>()
                for (j in 0 until value.length()){
                    val work = json.fromJson(value.getJSONObject(j).toString(), WorkSerializable::class.java)
                    works.add(work)

                    val x = 1
                }
                val dateKey = LocalDate.parse(key)
                map.put(dateKey,works)
        }
        return map
    }

    fun UserWorkListByDate() {
        mutableWorksByDateList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getAllWorksThatUserIdPublishedByDate("Bearer " +
                UserData.currentUser?.token!!, UserData.currentUser?.userId!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val map = jsonToMap(jObject)
                    mutableWorksByDateList.postValue(map)
                    mutableWorksByDateList.notifyAllObservers()
                    var x = 1
                }
                else
                {
                    mutableMessageToasting.postValue(Const.Token_Error)
                }
            }
        })
    }

    fun deleteWork(work: WorkSerializable,date: LocalDate?) {

    }

}


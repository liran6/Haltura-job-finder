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

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is calendar Fragment"
//    }
//    val text: LiveData<String> = _text
val mutableMessageToasting: MutableLiveData<String> by lazy {
    MutableLiveData<String>()
}
val mutableWorkList: MutableLiveData<MutableList<WorkSerializable>> by lazy { //by lazy
    MutableLiveData<MutableList<WorkSerializable>>(mutableListOf())
}
    val mutableWorksByDateList: MutableLiveData<MutableMap<LocalDate, MutableList<WorkSerializable>>> by lazy { //by lazy
    MutableLiveData<MutableMap<LocalDate, MutableList<WorkSerializable>>>(mutableMapOf())
}

    lateinit var WorkApiLiveData: MutableLiveData<UserResponse?>
    private var json = Gson()

    private fun jsonToMap(jsonO: JSONObject): MutableMap<LocalDate, MutableList<WorkSerializable>> {
        val map: MutableMap<LocalDate, MutableList<WorkSerializable>> = mutableMapOf()
//        val works= mutableListOf<WorkSerializable>()
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
                //works.clear()
            //map.put(key, json.getJSONArray(key))
        }
//        while (keys.hasNext()){
//            val key = keys.next()
//
//            map[key] = json.getJSONArray(key)
//
//        }
//        for (key in keys) {
//            map.put(key, json.getJSONArray(key))
//        }
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
                //WorkApiLiveData.postValue(null)
                mutableMessageToasting.postValue(Const.Connecting_Error)

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
//                    val works1 = jObject.names()
//                    val works2 = jObject.keys()
                    val map = jsonToMap(jObject)
                    mutableWorksByDateList.postValue(map)
                    //val keys: Iterator<String> = jObject.keys()
//                    val works = jObject.get("works_list")
//                    for (i in 0 until works.length())
//                    {
//                        val work = json.fromJson(works.getJSONObject(i).toString(), WorkSerializable::class.java)
//                        mutableWorkList.value!!.add(work)
//                    }
                    mutableWorksByDateList.notifyAllObservers()
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
                    val works = jObject.get("work_list")as JSONArray
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

                    var x = 1
                }
            }
        })
    }


    fun deleteWork(work: WorkSerializable,date: LocalDate?) {
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
//                    val date1 = LocalDate.parse(DateTime.getDate(work.startTime).replace('/', '-'))
                    mutableWorksByDateList.value!![date]!!.remove(work)
                    mutableWorkList.notifyAllObservers()
                    mutableWorksByDateList.notifyAllObservers()
                    //todo: toast delete was successfully
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.Connecting_Error)

                    //todo: toast message
                    var x = 1
                }
            }
        })
    }

}


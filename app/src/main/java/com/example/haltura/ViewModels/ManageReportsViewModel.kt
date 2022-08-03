package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ReportApi
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Models.ReportSerializable
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

class ManageReportsViewModel : ViewModel() {
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val mutableReportList: MutableLiveData<MutableList<ReportSerializable>> by lazy {
        MutableLiveData<MutableList<ReportSerializable>>(mutableListOf())
    }

    private var json = Gson()

    fun getReports() {
        mutableReportList.value!!.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(ReportApi::class.java)
        val call = retroService.getAllReports("Bearer " + UserData.currentUser?.token!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //TODO: toast
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    val works = jObject.get("reports_list") as JSONArray
                    for (i in 0 until works.length())
                    {
                        val report = json.fromJson(works.getJSONObject(i).toString(), ReportSerializable::class.java)
                        mutableReportList.value!!.add(report)
                    }
                    mutableReportList.notifyAllObservers()
                } else {
                    //TODO: toast
                }
            }
        })
    }

    fun MarkAsDone(report : ReportSerializable) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(ReportApi::class.java)
        val call = retroService.removeReport("Bearer " + UserData.currentUser?.token!!,
            report.reportId!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //TODO: toast
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mutableReportList.value!!.remove(report)
                    mutableReportList.notifyAllObservers()
                } else {
                    //TODO: toast
                }
            }
        })
    }
}
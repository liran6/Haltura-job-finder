package com.example.haltura.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ProfileAPI
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.notifyAllObservers
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageUsersViewModel : ViewModel() {
    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////  Users List  /////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    val mutableUserList: MutableLiveData<MutableList<ProfileSerializable>> by lazy {
        MutableLiveData<MutableList<ProfileSerializable>>(mutableListOf())
    }
    val mutableAllUserList: MutableList<ProfileSerializable> =mutableListOf()

    private var json = Gson()


    fun getUsers() {
        mutableUserList.value!!.clear()
        mutableAllUserList.clear()
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.getAllExtendedProfiles("Bearer " + UserData.currentUser?.token!!)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //TODO: toast
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jObject = JSONObject(response.body()!!.string())
                    //todo: check if i can do it without the for loop
                    val users = jObject.get("profile_list") as JSONArray
                    for (i in 0 until users.length())
                    {
                        val user = json.fromJson(users.getJSONObject(i).toString(), ProfileSerializable::class.java)
                        mutableAllUserList!!.add(user)
                    }
                    mutableUserList.value!!.addAll(mutableAllUserList)
                    mutableUserList.notifyAllObservers()
                } else {
                    //TODO: toast
                }
            }
        })
    }

    fun filter(textToFilter: String) {
        if (textToFilter.toString().trim { it <= ' ' }.length == 0) // not empty
        {
            mutableUserList.value!!.clear()
            mutableUserList.value!!.addAll(mutableAllUserList)
        }
        else
        {
            mutableUserList.value!!.clear()
            mutableAllUserList.forEach{
                var flag = true
                if (it.username != null)
                {
                    if (it.username!!.toLowerCase().contains(textToFilter))
                    {
                        mutableUserList.value!!.add(it)
                        flag = false
                    }
                }
                if (flag && it.id != null) // skip when added to list
                {
                    if (it.id!!.toLowerCase().contains(textToFilter))
                    {
                        mutableUserList.value!!.add(it)
                    }
                }
            }
        }
        mutableUserList.notifyAllObservers()
    }
}
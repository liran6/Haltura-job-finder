package com.example.haltura.ViewModels

import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.UsersAPI
import com.example.haltura.Sql.Items.UserLoginSerializable
import com.example.haltura.Sql.Items.UserObject
import com.example.haltura.Sql.Items.UserSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.Preferences
import com.example.haltura.Utils.UserData
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var json = Gson()

    val mutableMessageToasting: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val mutableUserHolder: MutableLiveData<UserObject> by lazy {
        MutableLiveData<UserObject>()
    }
    val mutableSignUpSucess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val mutableLogout: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


    fun createUser(email: String, username:String, password: String) {
        //todo: log this to server now
        val user = UserLoginSerializable(
            email,
            username,
            password
                )
        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.createUser(user)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
                mutableSignUpSucess.postValue(false)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    //todo save user and password for fast login
                    mutableMessageToasting.postValue(Const.Registration_Success)
                    mutableSignUpSucess.postValue(true)
                } else {
                    mutableSignUpSucess.postValue(false)
                    var res = response.errorBody()?.string()
                    var x = 1
                    mutableMessageToasting.postValue(Const.Email_Is_Taken)

                }
            }
        })
    }

    fun userSignIn(user: UserLoginSerializable){

        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.userAuth(user)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
                mutableLogout.postValue(true)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    var userInfo = json.fromJson(res, UserObject::class.java)
                    //store user password for shared preferences
                    //userInfo.password = user.password
//                    var userObject = UserObject(user.id,user.email,user.token,null)
                    if (userInfo.token != "") {
                        //var userObject = UserObject(user.id,user.email,user.token,null)
                        mutableLogout.postValue(false)
                        mutableMessageToasting.postValue(Const.Signing_In)
                        mutableUserHolder.postValue(userInfo)

                    //mutableUserHolder.postValue(UserObject(user.id,user.email,user.token,null,null))
                    } else {
                        mutableMessageToasting.postValue(Const.Token_Error)
                        mutableLogout.postValue(true)
                    }
                } else {
                    mutableMessageToasting.postValue(Const.Email_Password_incorrect)
                    mutableLogout.postValue(true)
                }
            }

        }
        )
    }
    fun getCurrentUser() {
        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.getUserInfo("Bearer " + (UserData.currentUser?.token))
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mutableMessageToasting.postValue(Const.Connecting_Error)
                mutableLogout.postValue(true)

                //todo logout
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    mutableLogout.postValue(false)

//                    Toast.makeText(
//                        activity, "User updated successfully ! ",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    mutableMessageToasting.postValue(Const.INVALID_TOKEN)
                    mutableLogout.postValue(true)
                    //todo logout
                }
            }
        })
    }

















    }
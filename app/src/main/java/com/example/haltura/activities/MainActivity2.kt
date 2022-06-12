package com.example.haltura.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.R
import com.example.haltura.Sql.Items.AddresSerializable
import com.example.haltura.Sql.Items.UserSerializable
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.databinding.ActivityMain2Binding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var user: UserSerializable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra(Const.USER_OBJECT)!!
        val navView: BottomNavigationView = binding.navView

//        navView.setOnNavigationItemSelectedListener { item ->
//            navController.navigate(item.itemId, args)
//            true
//        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_work, R.id.navigation_calendar , R.id.navigation_chats//R.id.navigation_home,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        createWork(user)
    }

    fun createWork(user: UserSerializable) {
        var address = AddresSerializable(
            "givatayim", "ben", 43, 3, "9"
        )
        var work = WorkSerializable(
            user.id,
            "isr",
            "tasktest",
            400,
            1,
            address,
            "blabla",
            "1999-12-31T22:00:00.000+00:00",
            "1999-12-31T22:00:00.000+00:00",
            "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"
        )
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.addWork("Bearer " + (user.token), user.id, work)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    //val listType = object : TypeToken<List<String>>(){ }.type
                    //var buisness= json.fromJson(res,BusinessSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }
}
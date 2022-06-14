package com.example.haltura.Sql

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import android.widget.EditText
import android.widget.Toast
import com.example.haltura.Api.UsersAPI
import com.example.haltura.Api.ProfileAPI
//import com.example.haltura.activities.ChatsActivity
import com.example.haltura.activities.LoginActivityOld
import com.example.haltura.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import androidx.lifecycle.MutableLiveData
import com.example.haltura.Api.ServiceBuilder
import com.example.haltura.Api.WorkAPI
import com.example.haltura.Sql.Items.*
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserOpenHelper {
    private var auth = FirebaseAuth.getInstance()
    private var reference = FirebaseDatabase.getInstance().getReference().child("Users")

    //private lateinit var reference : DatabaseReference
    private lateinit var activity: Activity
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private var json = Gson()
    lateinit var createNewUserLiveData: MutableLiveData<ResponseBody>

    init {
        createNewUserLiveData = MutableLiveData()
    }

    constructor(activity: Activity) {
        this.activity = activity
    }

    fun getCreateNewUserObserver(): MutableLiveData<ResponseBody> {
        return createNewUserLiveData
    }

//    fun chats() {
//        activity.startActivity(Intent(activity, ChatsActivity::class.java))
//    }

    fun signOut() {
        auth.signOut()
        Toast.makeText(
            activity, "Logged out",
            Toast.LENGTH_SHORT
        ).show()
        activity.startActivity(Intent(activity, LoginActivityOld::class.java))
        activity.finish()
    }

//    fun addWork() {
//        activity.startActivity(Intent(activity, ChatsActivity::class.java))
//    }

    fun getUserId(): String {
        var user = auth.getCurrentUser()
        if (user != null) {
            return user.getUid()
        }
        return null!!
    }
    // USERS

    fun updateUser(user: UserSerializable) {
        //update user
        user.email = "r@r.com"
        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.updateUserInfo(user.id, "Bearer " + (user.token), user)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity, "User updated successfully ! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    fun getCurrentUser(user: UserSerializable) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
        val call = retroService.getUserInfo("Bearer " + (user.token))
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity, "User updated successfully ! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }
    //PROFILES

    fun getUserProfile(user: UserSerializable) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.getProfile("Bearer " + (user.token), user.id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity, "User updated successfully ! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    var res = response.body()?.string()
                    var profile = json.fromJson(res, ProfileSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    fun updateUserProfile(user: ProfileSerializable, token: String) {
        //ToDo fix user profile schema(tabs)
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.updateProfileInfo(user.userId, "Bearer " + token, user)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity, "User updated successfully ! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }
    //BUSINESS

    fun getAllUserBusiness(user: UserSerializable) {
        //ToDo check
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.getBusinesses("Bearer " + (user.token), user.id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    //val listType = object : TypeToken<List<String>>(){ }.type
                    var buisness_list = json.fromJson(res, BusinessesList::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    fun createBusiness(user: UserSerializable) {
        var business = BusinessSerializable(null, user.id, "isr", "benet", "iff", "")
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.addBusiness("Bearer " + (user.token), user.id, business)
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

    fun updateBusiness(business: BusinessSerializable, token: String) {
        //ToDo fix business search.
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.updateBusinessInfo(
            business.userId, business.name,
            "Bearer $token", business
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity, "Business updated successfully ! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    fun deleteUserBusiness(business: BusinessSerializable, token: String) {
        //ToDo fix business search maybe from profile- search by buisness id and user id.
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.deleteBusiness(
            business.userId, business.name,
            "Bearer $token"
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity, "Business" + business.name + "Deleted successfully ! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    fun deleteAllUserBusinesses(profile: ProfileSerializable, token: String) {
        //ToDo fix business search maybe from profile- search by buisness id and user id.
        val retroService =
            ServiceBuilder.getRetroInstance().create(ProfileAPI::class.java)
        val call = retroService.deleteAllBusinesses(profile.id, "Bearer $token")
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        activity, "All Businesses Deleted successfully ! ",
                        Toast.LENGTH_SHORT
                    ).show()
                    //var res = response.body()?.string()
                    //var updatedUser = json.fromJson(res, UserSerializable::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    // WORK

//    fun createWork(user: UserSerializable) {
//        var address = AddresSerializable(
//            "givatayim", "ben", 43, 3, "9"
//        )
//        var work = WorkSerializable(
//            user.id,
//            "isr",
//            "tasktest",
//            400,
//            1,
//            address,
//            "blabla",
//            "1999-12-31T22:00:00.000+00:00",
//            "1999-12-31T22:00:00.000+00:00",
//            "imagetest"
//        )
//        val retroService =
//            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
//        val call = retroService.addWork("Bearer " + (user.token), user.id, work)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                //createNewUserLiveData.postValue(null)
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    var res = response.body()?.string()
//                    //val listType = object : TypeToken<List<String>>(){ }.type
//                    //var buisness= json.fromJson(res,BusinessSerializable::class.java)
//                    var x = 1
//                } else {
//                    var x = 1
//                }
//            }
//        })
//    }

    fun getWorkByWorkId(user: UserSerializable, workID: String) {
        //ToDo check
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getWork("Bearer " + (user.token), workID)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    var work  = json.fromJson(res, WorkSerializable::class.java)  //val listType = object : TypeToken<List<String>>(){ }.type

                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    fun getAllWorksOFUserId(user: UserSerializable) {
        //ToDo check
        var whoIsAsking = "all" //default
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getAllWorksOfUserID("Bearer " + (user.token),whoIsAsking, user.id)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    //val listType = object : TypeToken<List<String>>(){ }.type
                    var work_list = json.fromJson(res, WorksList::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

    fun getAllWorks(user: UserSerializable) {
        val retroService =
            ServiceBuilder.getRetroInstance().create(WorkAPI::class.java)
        val call = retroService.getAllWorks("Bearer " + (user.token))
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    var res = response.body()?.string()
                    //val listType = object : TypeToken<List<String>>(){ }.type
                    var work_list = json.fromJson(res, WorksList::class.java)
                    var x = 1
                } else {
                    var x = 1
                }
            }
        })
    }

//    fun userSignIn(user: UserSerializable): Boolean {
//        var test = false
//        val retroService =
//            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
//        val call = retroService.userAuth(user)
//        call.enqueue(object : retrofit2.Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                val b = 1
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    var res = response.body()?.string()
//                    var user = json.fromJson(res, UserSerializable::class.java)
//                    if (user.token != "") {
//                        true.also { test = it }
//                        Toast.makeText(
//                            activity, "Signing In",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        var workID = "629e429134ef7f54d08c0ede"
//                        getAllWorksOFUserId(user)
//                        //val intent = Intent(this,MainActivity::class.java)
////                        activity.startActivity(Intent(activity, MainActivity::class.java))
////                        activity.finish()
//                    } else {
//                        Toast.makeText(
//                            activity, "There was a problem signing you in, please try again",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    //val profileID = retroService.
////                    val editor = getSharedPreferences(PREFS, MODE_PRIVATE).edit()
////                    editor.putString("username", username)
////                    editor.apply()
////                    val intent = Intent(this@LogInActivity, MenuActivity::class.java)
////                    intent.putExtra("myUsername", username)
////                    startActivity(intent)
//                } else {
//                    Toast.makeText(
//                        activity, "Wrong email or password.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//        }
//        )
//       if(test!=false){
//           return test
//       }else
//       {return test}
//
//
//////        Firebase
//
////        auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
////            .addOnCompleteListener() { task ->
////                if (task.isSuccessful) {
////                    val user: FirebaseUser? = auth.currentUser
////                    if (user != null){
////                        loginUpdateUi(user)
//////                        Toast.makeText(activity, "Login Successful!",
//////                            Toast.LENGTH_SHORT).show()
//////                        activity.startActivity(Intent(activity, MainActivity::class.java))
//////                        activity.finish()
////                    }
////
////                    //updateUI(user)
////                } else {
////                    Toast.makeText(activity, "Wrong email or password.",
////                        Toast.LENGTH_SHORT).show()
////
////                    //updateUI(null)
////                }
////            }
//    }

//    fun createUser(email: String, password: String) {
//        //todo: log this to server now
//        var user = UserSerializable(email, "", "", "", password)
//        val retroService =
//            ServiceBuilder.getRetroInstance().create(UsersAPI::class.java)
//        val call = retroService.createUser(user)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                //createNewUserLiveData.postValue(null)
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(
//                        activity,
//                        "User registered successfully",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    userSignIn(user)
//                } else {
//                    Toast.makeText(
//                        activity,
//                        "User could not be created",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        })
//////       FIREBASE(OLD)
//
////        //var reference = FirebaseDatabase.getInstance().getReference().child("Users")
////        auth.createUserWithEmailAndPassword(user.getEmail()!!, user.getPassword()!!)
////            .addOnCompleteListener(OnCompleteListener {task ->
////                var fireBaseUser = auth.getCurrentUser()
////                if (fireBaseUser != null)
////                {
////                    var reference= this.reference
////                    var activity = this.activity
////                    reference.child(fireBaseUser.getUid()).setValue(user).addOnCompleteListener(
////                        OnCompleteListener {
////                            task -> if (task.isSuccessful)
////                            {
////                                Toast.makeText(activity,
////                                    "User registered successfully",
////                                    Toast.LENGTH_SHORT).show()
////                                loginUpdateUi(fireBaseUser)
//////                                activity.startActivity(Intent(activity, MainActivity::class.java))
//////                                activity.finish()
////                                //FirebaseDatabase.getInstance().getReference().child("Users").child("test").setValue(user(test = 'test1'))
////                            }
////                            else
////                            {
////                                Toast.makeText(activity,
////                                    "User could not be created",
////                                    Toast.LENGTH_SHORT).show()
////                            }
////                        }
////                    )
////                }
////            }
////        )
//
//    }

    fun resetPassword(etEmail: EditText) {
        auth.sendPasswordResetEmail(etEmail.text.toString()).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    activity, "Check your email to reset your password!",
                    Toast.LENGTH_LONG
                ).show()
                activity.startActivity(Intent(activity, LoginActivityOld::class.java))
                activity.finish()
            } else {
                Toast.makeText(
                    activity, "Try again! something wrong happened!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun loginUpdateUi(currentUser: FirebaseUser?, wasLoggedIn: Boolean = false) {
        // todo: check if needs to be here or should be after the calling in login
        if (currentUser != null) {
            if (!wasLoggedIn) {
                Toast.makeText(
                    activity, "Login Successful!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finish()
        }
    }

    fun isUserLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        //todo: update ui according to the user - admin? user? business?
        if (currentUser != null) {
            loginUpdateUi(currentUser, true);
            return true
        } else {
            return false
        }
    }
}
//
//private fun <TResult> Task<TResult>.addOnCompleteListener()
//{
//    var fireBaseUser = auth.getCurrentUser()
//    if (fireBaseUser != null)
//    {
//        reference.child(fireBaseUser.getUid()).setValue(user).addOnCompleteListener(
//            OnCompleteListener {
//                    task -> if (task.isSuccessful)
//            {
//                Toast.makeText(activity,
//                    "User registered successfully",
//                    Toast.LENGTH_SHORT).show()
//                activity.finish()
//                activity.startActivity(Intent(activity, MainActivity::class.java))
//            }
//            else
//            {
//                Toast.makeText(activity,
//                    "User could not be created",
//                    Toast.LENGTH_SHORT).show()
//            }
//            }
//        )
//    }
//}
//class UserOpenHelper : SQLiteOpenHelper {
//    constructor(context: Context?, name: String?, factory: CursorFactory?, version: Int) : super(
//        context,
//        name,
//        factory,
//        version
//    ) {
//    }
//
//    var allColumns = arrayOf(
//        COLUMN_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_PASSWORD,
//        COLUMN_USERNAME, COLUMN_PHONE, COLUMN_CITY, COLUMN_STREET,
//        COLUMN_STREETNUM, COLUMN_FLOOR, COLUMN_APPARTMENT, COLUMN_ADMIN
//    )
//    var database: SQLiteDatabase? = null
//
//    constructor(context: Context?) : super(context, DATABASENAME, null, DATABASEVERSION) {}
//
//    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL(CREATE_TABLE_USER)
//        Log.i("data", "Table user created")
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
//        onCreate(db)
//    }
//
//    fun open() {
//        database = this.writableDatabase
//        Log.i("data", "Database connection open")
//    }
//
//    fun createUser(c: User): User //insert user to database(with the id++)
//    {
//        val values = ContentValues()
//        values.put(COLUMN_FIRSTNAME, c.getUserFirstName())
//        values.put(COLUMN_LASTNAME, c.getUserLastName())
//        values.put(COLUMN_PASSWORD, c.getPassword())
//        values.put(COLUMN_USERNAME, c.getUserName())
//        values.put(COLUMN_PHONE, c.getUserPhone())
//        values.put(COLUMN_CITY, c.getAddress()!!.getCity())
//        values.put(COLUMN_STREET, c.getAddress()!!.getStreet())
//        values.put(COLUMN_STREETNUM, c.getAddress()!!.getStreetNum())
//        values.put(COLUMN_FLOOR, c.getAddress()!!.getFloor())
//        values.put(COLUMN_APPARTMENT, c.getAddress()!!.getAppartment())
//        values.put(COLUMN_ADMIN, c.getIsAdmin())
//        val insertId = database!!.insert(TABLE_USER, null, values)
//        Log.i("data", "customer" + insertId + "insert to database")
//        c.setId(insertId)
//        return c
//    }
//
//    //returns list of all users
//    val allUsers: ArrayList<Any>
//        get() {
//            val userList: ArrayList<User> = ArrayList<User>()
//            val cursor: Cursor =
//                readableDatabase.query(TABLE_USER, allColumns, null, null, null, null, null)
//            if (cursor.getCount() > 0) {
//                while (cursor.moveToNext()) {
//                    val id: Long = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
//                    val userFirstName: String = cursor.getString(
//                        cursor.getColumnIndex(
//                            COLUMN_FIRSTNAME)
//                    )
//                    val userLastName: String = cursor.getString(
//                        cursor.getColumnIndex(
//                            COLUMN_LASTNAME
//                        )
//                    )
//                    val password: String = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
//                    val userName: String = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
//                    val userPhone: String = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
//                    val city: String = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
//                    val street: String = cursor.getString(cursor.getColumnIndex(COLUMN_STREET))
//                    val streetnum: String = cursor.getString(
//                        cursor.getColumnIndex(
//                            COLUMN_STREETNUM
//                        )
//                    )
//                    val floor: String = cursor.getString(cursor.getColumnIndex(COLUMN_FLOOR))
//                    val appartment: String = cursor.getString(
//                        cursor.getColumnIndex(
//                            COLUMN_APPARTMENT
//                        )
//                    )
//                    val admin: String = cursor.getString(cursor.getColumnIndex(COLUMN_ADMIN))
//                    val c = User(
//                        id,
//                        userFirstName,
//                        userLastName,
//                        password,
//                        userName,
//                        userPhone,
//                        Address(city, street, streetnum, floor, appartment),
//                        admin
//                    )
//                    userList.add(c)
//                }
//            }
//            Log.i("data", "כל המשתמשים")
//            return userList
//        }
//
//    fun getAllUsersByFilter(
//        selection: String?,
//        OrderBy: String?
//    ): ArrayList<User> {
//        val cursor: Cursor = database!!.query(
//            TABLE_USER,
//            allColumns,
//            selection,
//            null,
//            null,
//            null,
//            OrderBy
//        )
//        return convertCurserToList(cursor)
//    }
//
//    private fun convertCurserToList(cursor: Cursor): ArrayList<User> {
//        val userList: ArrayList<User> = ArrayList<User>()
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                val id: Long = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
//                val userFirstName: String = cursor.getString(
//                    cursor.getColumnIndex(
//                        COLUMN_FIRSTNAME
//                    )
//                )
//                val userLastName: String = cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME))
//                val password: String = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
//                val userName: String = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
//                val userPhone: String = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
//                val city: String = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
//                val street: String = cursor.getString(cursor.getColumnIndex(COLUMN_STREET))
//                val streetnum: String = cursor.getString(cursor.getColumnIndex(COLUMN_STREETNUM))
//                val floor: String = cursor.getString(cursor.getColumnIndex(COLUMN_FLOOR))
//                val appartment: String = cursor.getString(cursor.getColumnIndex(COLUMN_APPARTMENT))
//                val admin: String = cursor.getString(cursor.getColumnIndex(COLUMN_ADMIN))
//                val c = User(
//                    id,
//                    userFirstName,
//                    userLastName,
//                    password,
//                    userName,
//                    userPhone,
//                    Address(city, street, streetnum, floor, appartment),
//                    admin
//                )
//                userList.add(c)
//            }
//        }
//        return userList
//    }
//
//    fun getUserById(rowId: Long): User? //get user by id
//    {
//        val cursor: Cursor = readableDatabase.query(
//            TABLE_USER, allColumns,
//            COLUMN_ID + "=" + rowId, null, null, null, null
//        )
//        cursor.moveToFirst()
//        if (cursor.getCount() > 0) {
//            val id: Long =
//                cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
//            val userFirstName: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME))
//            val userLastName: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME))
//            val password: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
//            val userName: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
//            val userPhone: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
//            val city: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
//            val street: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_STREET))
//            val streetnum: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_STREETNUM))
//            val floor: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_FLOOR))
//            val appartment: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_APPARTMENT))
//            val admin: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_ADMIN))
//            return User(
//                id,
//                userFirstName,
//                userLastName,
//                password,
//                userName,
//                userPhone,
//                Address(city, street, streetnum, floor, appartment),
//                admin
//            )
//        }
//        return null
//    }
//
//    fun updateByRow(user: User): Long // update user by id
//    {
//        val values = ContentValues()
//        values.put(COLUMN_FIRSTNAME, user.getUserFirstName())
//        values.put(COLUMN_LASTNAME, user.getUserLastName())
//        values.put(COLUMN_PASSWORD, user.getPassword())
//        values.put(COLUMN_USERNAME, user.getUserName())
//        values.put(COLUMN_PHONE, user.getUserPhone())
//        values.put(COLUMN_CITY, user.getAddress()!!.getCity())
//        values.put(COLUMN_STREET, user.getAddress()!!.getStreet())
//        values.put(COLUMN_STREETNUM, user.getAddress()!!.getStreetNum())
//        values.put(COLUMN_FLOOR, user.getAddress()!!.getFloor())
//        values.put(COLUMN_APPARTMENT, user.getAddress()!!.getAppartment())
//        values.put(COLUMN_ADMIN, user.getIsAdmin())
//        return writableDatabase.update(TABLE_USER, values, COLUMN_ID + "=" + user.getId(), null)
//            .toLong()
//    }
//
//    fun deleteCustomerByRow(rowId: Long): Long //delete user by id
//    {
//        return writableDatabase.delete(TABLE_USER, COLUMN_ID + "=" + rowId, null)
//            .toLong()
//    }
//
//    fun checkUserName(username: String): Boolean //check if user name exist
//    {
//        val colums = arrayOf(COLUMN_ID)
//        database = this.readableDatabase
//        val selection = COLUMN_USERNAME + " = ?"
//        val selectionArgs = arrayOf(username)
//        val cursor: Cursor = database.query(
//            TABLE_USER,
//            colums,
//            selection,
//            selectionArgs,
//            null,
//            null,
//            null
//        )
//        val cursorCount: Int = cursor.getCount()
//        cursor.close()
//        database.close()
//        return cursorCount > 0
//    }
//
//    fun checkUser(username: String, pass: String): Boolean {
//        val colums = arrayOf(COLUMN_ID)
//        val db = this.readableDatabase
//        val selection = COLUMN_USERNAME + " = ? " + "AND " + COLUMN_PASSWORD + "= ?"
//        val selectionArgs = arrayOf(username, pass)
//        val cursor: Cursor = db.query(
//            TABLE_USER,
//            colums,
//            selection,
//            selectionArgs,
//            null,
//            null,
//            null
//        )
//        val cursorCount: Int = cursor.getCount()
//        cursor.close()
//        db.close()
//        return cursorCount > 0
//    }
//
//    fun getUserByUsername(username: String): User? {
//        database = readableDatabase
//        val cursor: Cursor = database.query(
//            TABLE_USER,
//            allColumns,
//            COLUMN_USERNAME + " = ?",
//            arrayOf(username),
//            null,
//            null,
//            null
//        )
//        cursor.moveToFirst()
//        if (cursor.getCount() > 0) {
//            val id: Long =
//                cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
//            val userFirstName: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME))
//            val userLastName: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME))
//            val password: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
//            val userName: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
//            val userPhone: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
//            val city: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
//            val street: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_STREET))
//            val streetnum: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_STREETNUM))
//            val floor: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_FLOOR))
//            val appartment: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_APPARTMENT))
//            val admin: String =
//                cursor.getString(cursor.getColumnIndex(COLUMN_ADMIN))
//            return User(
//                id,
//                userFirstName,
//                userLastName,
//                password,
//                userName,
//                userPhone,
//                Address(city, street, streetnum, floor, appartment),
//                admin
//            )
//        }
//        return null
//    }
//
//    companion object {
//        const val DATABASENAME = "MY_DB.dbl1"
//        const val TABLE_USER = "users1"
//        const val DATABASEVERSION = 1
//        const val COLUMN_ID = "id"
//        const val COLUMN_FIRSTNAME = "userFirstName"
//        const val COLUMN_LASTNAME = "userLastName"
//        const val COLUMN_PASSWORD = "password"
//        const val COLUMN_USERNAME = "userName"
//        const val COLUMN_PHONE = "userPhone"
//        const val COLUMN_CITY = "city"
//        const val COLUMN_STREET = "street"
//        const val COLUMN_STREETNUM = "streetnum"
//        const val COLUMN_FLOOR = "floor"
//        const val COLUMN_APPARTMENT = "appartment"
//        const val COLUMN_ADMIN = "admin"
//        private const val CREATE_TABLE_USER = ("CREATE TABLE IF NOT EXISTS " +
//                TABLE_USER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRSTNAME + " VARCHAR," + COLUMN_LASTNAME + " VARCHAR,"
//                + COLUMN_PASSWORD + " VARCHAR," + COLUMN_USERNAME + " VARCHAR," + COLUMN_PHONE + " VARCHAR,"
//                + COLUMN_CITY + " VARCHAR," + COLUMN_STREET + " VARCHAR," + COLUMN_STREETNUM + " VARCHAR," + COLUMN_FLOOR + " VARCHAR," + COLUMN_APPARTMENT + " VARCHAR," + COLUMN_ADMIN + " VARCHAR " + ");")
//    }
//}

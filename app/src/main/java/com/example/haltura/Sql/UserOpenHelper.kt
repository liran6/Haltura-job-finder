package com.example.haltura.Sql

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.haltura.Sql.Items.Address
import com.example.haltura.Sql.Items.User
import com.example.haltura.activities.LoginActivity
import com.example.haltura.activities.MainActivity
import com.example.haltura.databinding.ActivityCalendarBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import io.realm.Realm

class UserOpenHelper
{
    private var auth =  FirebaseAuth.getInstance()
    private var reference = FirebaseDatabase.getInstance().getReference().child("Users")
    //private lateinit var reference : DatabaseReference
    private lateinit var activity: Activity

    constructor(activity: Activity)
    {
        this.activity = activity
    }

    fun createUser(user: User)
    {
        //todo: log this to server
        //var reference = FirebaseDatabase.getInstance().getReference().child("Users")
        auth.createUserWithEmailAndPassword(user.getEmail()!!, user.getPassword()!!)
            .addOnCompleteListener(OnCompleteListener {task ->
                var fireBaseUser = auth.getCurrentUser()
                if (fireBaseUser != null)
                {
                    var reference= this.reference
                    var activity = this.activity
                    reference.child(fireBaseUser.getUid()).setValue(user).addOnCompleteListener(
                        OnCompleteListener {
                            task -> if (task.isSuccessful)
                            {
                                Toast.makeText(activity,
                                    "User registered successfully",
                                    Toast.LENGTH_SHORT).show()
                                activity.finish()
                                activity.startActivity(Intent(activity, MainActivity::class.java))
                            }
                            else
                            {
                                Toast.makeText(activity,
                                    "User could not be created",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        )
//        var fireBaseUser = auth.getCurrentUser()
//        if (fireBaseUser != null)
//        {
//            reference.child(fireBaseUser.getUid()).setValue(user).addOnCompleteListener(
//                OnCompleteListener {
//                        task -> if (task.isSuccessful)
//                        {
//                            Toast.makeText(activity,
//                                "User registered successfully",
//                                Toast.LENGTH_SHORT).show()
//                            activity.finish()
//                            activity.startActivity(Intent(activity, MainActivity::class.java))
//                        }
//                    else
//                    {
//                        Toast.makeText(activity,
//                            "User could not be created",
//                            Toast.LENGTH_SHORT).show()
//                    }
//                }
//            )
//        }
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

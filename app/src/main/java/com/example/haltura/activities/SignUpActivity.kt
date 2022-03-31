package com.example.haltura.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.haltura.R
import com.example.haltura.Helpers.Validation
import com.example.haltura.Helpers.Validation.Companion.signUpValid
import com.example.haltura.Sql.Items.Address
import com.example.haltura.Sql.Items.User
import com.example.haltura.Sql.UserOpenHelper


class SignUpActivity : AppCompatActivity() {
    private val FILTER4 = arrayOf<InputFilter>(LengthFilter(4))
    private val FILTER3 = arrayOf<InputFilter>(LengthFilter(3))
    private val FILTER2 = arrayOf<InputFilter>(LengthFilter(2))
//    var city: String ? = null
//    var cities: Array<String> ? = null
//    var etUserName: EditText ? = null
//    var etFirstName: EditText ? = null
//    var etLastName: EditText ? = null
//    var spinnerCity: Spinner ? = null
//    var etApartment: EditText ? = null
//    var etStreetName: EditText ? = null
//    var etStreetNumber: EditText ? = null
//    var etFloor: EditText ? = null
//    var etEmail: EditText ? = null
//    var etPhone: EditText ? = null
//    var etPassword: EditText ? = null
//    var etConfirmPassword: EditText ? = null
//    var btnSignUp: Button ? = null
    private lateinit var city: String
    private lateinit var cities: Array<String>
    private lateinit var etUserName: EditText
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var spinnerCity: Spinner
    private lateinit var etApartment: EditText
    private lateinit var etStreetName: EditText
    private lateinit var etStreetNumber: EditText
    private lateinit var etFloor: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSignUp: Button
    var helper = UserOpenHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //helper = UserOpenHelper(this)
        cities = resources.getStringArray(R.array.cities)
        etUserName = findViewById<View>(R.id.et_UserName) as EditText
        etFirstName = findViewById<View>(R.id.et_FirstName) as EditText
        etLastName = findViewById<View>(R.id.et_LastName) as EditText
        etStreetName = findViewById<View>(R.id.et_StreetName) as EditText
        etStreetNumber = findViewById<View>(R.id.et_StreetNumber) as EditText
        etFloor = findViewById<View>(R.id.et_Floor) as EditText
        etEmail = findViewById<View>(R.id.et_Email) as EditText
        etPhone = findViewById<View>(R.id.et_Phone) as EditText
        etPassword = findViewById<View>(R.id.et_Password) as EditText
        etConfirmPassword = findViewById<View>(R.id.et_ConfirmPassword) as EditText
        etApartment = findViewById<View>(R.id.et_Apartment) as EditText
        btnSignUp = findViewById<View>(R.id.btn_SignUp) as Button
        spinnerCity = findViewById<View>(R.id.spinner_City) as Spinner
        spinnerCity!!.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                city = cities[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        fun SignUp(view: View) {
            if (signUpValid(etFirstName, etLastName, etUserName, spinnerCity, city, etStreetName,
                    etStreetNumber, etFloor, etApartment, etPhone, etPassword, etConfirmPassword)
            ) {
                //todo: check username and email existence - need to talk about that
                if (Validation.userNameExists(etUserName, this)) {
                    val user = User()
                    user.setUserName(etUserName.text.toString())
                    user.setUserFirstName(etFirstName!!.text.toString())
                    user.setUserLastName(etLastName!!.text.toString())
                    user.setPassword(etPassword!!.text.toString())
                    user.setAddress(
                        Address(
                            city,
                            etStreetName!!.text.toString(),
                            etStreetNumber!!.text.toString(),
                            etFloor!!.text.toString(),
                            etApartment!!.text.toString()
                        )
                    )
                    user.setUserPhone(etPhone!!.text.toString())
                    user.setIsAdmin(false)
                    helper.createUser(user)
                    //helper.open()
//                    if (helper.createUser(user).getId() !== -1) {
//                        Toast.makeText(
//                            this,
//                            "user register complete",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        startActivity(Intent(this, LoginActivity::class.java))
//                        finish()
//                    }
                    //helper.close()
                } else {
                    Toast.makeText(
                        this,
                        "user name already taken!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
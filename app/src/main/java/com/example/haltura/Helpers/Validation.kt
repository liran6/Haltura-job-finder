package com.example.haltura.Helpers

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.haltura.Sql.UserOpenHelper


class Validation {
    companion object {
        private val NAME_ERROR = "you must to insert %s or more characters!"
        private val PHONE_ERROR = "you must to insert a valid phone!"
        private val PASSWORD_ERROR = "you must to insert 5 or more characters!"
        private val CONF_PASSWORD_ERROR = "those passwords didn't match. try again!"
        private val NAME_EXISTS_ERROR = "username already taken"
        private val SPINNER_EXISTS_ERROR = "choose city"
        private val EMAIL_ERROR = "Please enter Valid Email!"

        private fun userNameValid(etName: EditText, limit: Int): Boolean {
            val text = etName.text.toString()
            if (text.length < limit) {
                etName.error = String.format(NAME_ERROR, "" + limit)
                return false
            }
            return true
        }

        private fun userPhoneValid(etPhone: EditText): Boolean {
            val text = etPhone.text.toString()
            //Patterns.PHONE
            val regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}".toRegex()
            if (text == "" || !text.matches(regex)) {
                etPhone.error = PHONE_ERROR
                return false
            }
            return true
        }

        private fun userPasswordValid(etPassword: EditText): Boolean {
            val text = etPassword.text.toString()
            if (text == "" || text.length < 5) {
                etPassword.error = PASSWORD_ERROR
                return false
            }
            return true
        }

        private fun isEmpty(check: EditText): Boolean { //todo:לא עובד?
            if (check.text.toString() == "") {
                check.error = "you need to insert a value"
                return false
            }
            return true
        }

        private fun spinnerCheck(spinner: Spinner): Boolean {
            if (spinner.selectedItem == "City") {
                (spinner.selectedView as TextView).error = SPINNER_EXISTS_ERROR
                return false
            }
            return true
        }

        private fun confirmPasswordValid(etPassword: EditText, etConfPassword: EditText): Boolean {
            val textPass = etPassword.text.toString()
            val textConfPass = etConfPassword.text.toString()
            if (textPass != textConfPass) {
                etConfPassword.error = CONF_PASSWORD_ERROR
                return false
            }
            return true
        }

        private fun userEmailValid(etEmail: EditText): Boolean {
            val text = etEmail.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                etEmail.error = EMAIL_ERROR
                etEmail.requestFocus()
                return false
            }
            return true
        }

        //todo: add username and or email check

        //    fun userNameExists(edName: EditText, context: Context?): Boolean {
        //        val helper = UserOpenHelper(context)
        //        helper.open()
        //        val text = edName.text.toString()
        //        if (helper.checkUserName(text)) {
        //            edName.error = NAME_EXISTS_ERROR
        //            return false
        //        }
        //        if (text.length < 5) {
        //            edName.error = NAME_ERROR
        //        }
        //        return true
        //    }


        fun resetPasswordValid(etPassword: EditText, etConfPassword: EditText): Boolean {
            userPasswordValid(etPassword)
            confirmPasswordValid(etPassword, etConfPassword)
            return userPasswordValid(etPassword) && confirmPasswordValid(etPassword, etConfPassword)
        }

        fun signInValid(
            etEmail: EditText,
            etPassword: EditText,
        ): Boolean {
            var bool = true
            bool = bool && userEmailValid(etEmail)
            bool = bool && userPasswordValid(etPassword)
            return bool
        }

        fun signUpValid(
            etName: EditText,
            etLastName: EditText,
            etUserName: EditText,
            spinner: Spinner,
            city: String?,
            etStreet: EditText,
            etStreetNumber: EditText,
            etFloor: EditText,
            etApartment: EditText,
            etPhone: EditText,
            etPassword: EditText,
            etConfPassword: EditText,
            etEmail: EditText
        ): Boolean {
            //NOTE: edName equ to private name
            userNameValid(etName, 2)
            userNameValid(etLastName, 2)
            userNameValid(etUserName, 5)
            spinnerCheck(spinner)
            isEmpty(etStreet)
            isEmpty(etStreetNumber)
            isEmpty(etFloor)
            isEmpty(etApartment)
            userPhoneValid(etPhone)
            userEmailValid(etEmail)
            userPasswordValid(etPassword)
            confirmPasswordValid(etPassword, etConfPassword)
            return userNameValid(etName, 2) &&
                    userNameValid(etLastName, 2) &&
                    userNameValid(etUserName, 5) &&
                    spinnerCheck(spinner) &&
                    isEmpty(etStreet) &&
                    isEmpty(etStreetNumber) &&
                    isEmpty(etFloor) &&
                    isEmpty(etApartment) &&
                    userPhoneValid(etPhone) &&
                    userEmailValid(etEmail) &&
                    userPasswordValid(etPassword) &&
                    confirmPasswordValid(etPassword, etConfPassword)
        }

        fun updateValid(
            upName: EditText,
            upLastName: EditText?,
            upSpinner: Spinner,
            upStreet: EditText,
            upStreetNumber: EditText,
            upFloor: EditText,
            upApartment: EditText,
            upPhone: EditText,
            upPassword: EditText,
            upConfPassword: EditText
        ): Boolean {
            //NOTE: edName equ to private name
            userNameValid(upName, 2)
            //userNameValid(upLastName,2);
            //userNameValid(upUserName,5);
            spinnerCheck(upSpinner)
            isEmpty(upStreet)
            isEmpty(upStreetNumber)
            isEmpty(upFloor)
            isEmpty(upApartment)
            userPhoneValid(upPhone)
            userPasswordValid(upPassword)
            confirmPasswordValid(upPassword, upConfPassword)
            return userNameValid(upName, 2) &&
                    spinnerCheck(upSpinner) &&
                    isEmpty(upStreet) &&
                    isEmpty(upStreetNumber) &&
                    isEmpty(upFloor) &&
                    isEmpty(upApartment) &&
                    userPhoneValid(upPhone) &&
                    userPasswordValid(upPassword) &&
                    confirmPasswordValid(upPassword, upConfPassword)
        }
    }
}

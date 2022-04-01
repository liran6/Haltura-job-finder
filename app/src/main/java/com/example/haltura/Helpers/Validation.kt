package com.example.haltura.Helpers

import android.content.Context
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


        private fun userNameValid(edName: EditText, limit: Int): Boolean {
            val text = edName.text.toString()
            if (text.length < limit) {
                edName.error = String.format(NAME_ERROR, "" + limit)
                return false
            }
            return true
        }

        private fun userPhoneValid(edPhone: EditText): Boolean {
            val text = edPhone.text.toString()
            val regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}".toRegex()
            if (text == "" || !text.matches(regex)) {
                edPhone.error = PHONE_ERROR
                return false
            }
            return true
        }

        private fun userPasswordValid(edPassword: EditText): Boolean {
            val text = edPassword.text.toString()
            if (text == "" || text.length < 5) {
                edPassword.error = PASSWORD_ERROR
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

        private fun confirmPasswordValid(edPassword: EditText, edConfPassword: EditText): Boolean {
            val textPass = edPassword.text.toString()
            val textConfPass = edConfPassword.text.toString()
            if (textPass != textConfPass) {
                edConfPassword.error = CONF_PASSWORD_ERROR
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


        fun resetPasswordValid(edPassword: EditText, edConfPassword: EditText): Boolean {
            userPasswordValid(edPassword)
            confirmPasswordValid(edPassword, edConfPassword)
            return userPasswordValid(edPassword) && confirmPasswordValid(edPassword, edConfPassword)
        }

        fun signUpValid(
            edName: EditText,
            edLastName: EditText,
            edUserName: EditText,
            spinner: Spinner,
            city: String?,
            edStreet: EditText,
            edStreetNumber: EditText,
            edFloor: EditText,
            edApartment: EditText,
            edPhone: EditText,
            edPassword: EditText,
            edConfPassword: EditText,
            etEmail: EditText
        ): Boolean {
            //NOTE: edName equ to private name
            userNameValid(edName, 2)
            userNameValid(edLastName, 2)
            userNameValid(edUserName, 5)
            spinnerCheck(spinner)
            isEmpty(edStreet)
            isEmpty(edStreetNumber)
            isEmpty(edFloor)
            isEmpty(edApartment)
            userPhoneValid(edPhone)
            userPasswordValid(edPassword)
            confirmPasswordValid(edPassword, edConfPassword)
            return userNameValid(edName, 2) &&
                    userNameValid(edLastName, 2) &&
                    userNameValid(edUserName, 5) &&
                    spinnerCheck(spinner) &&
                    isEmpty(edStreet) &&
                    isEmpty(edStreetNumber) &&
                    isEmpty(edFloor) &&
                    isEmpty(edApartment) &&
                    userPhoneValid(edPhone) &&
                    userPasswordValid(edPassword) &&
                    confirmPasswordValid(edPassword, edConfPassword)
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

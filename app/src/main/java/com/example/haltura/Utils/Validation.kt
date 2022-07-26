package com.example.haltura.Utils

import android.util.Patterns
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

//import com.example.haltura.Sql.UserOpenHelper

class Validation {
    companion object {
        private val NAME_ERROR = "you must to insert %s or more characters!"
        private val USER_NAME_ERROR = "Username must length must be between 4-16 characters!\n" +
                " start with a lower case letter and contain only\n" +
                " lower case letters a-z, digits 0-9, and hyphens \'-\' "


        //        private val USER_NAME_ERROR = "username must contain only lowercase letters! [a-z]\n" +
//                "username must start with a letter!\n" +
//                "username length must be between 4-16 characters!"
        private val PHONE_ERROR = "you must to insert a valid phone!"
        private val PASSWORD_ERROR = "you must to insert 5 or more characters!"
        private val CONF_PASSWORD_ERROR = "those passwords didn't match. try again!"
        private val NAME_EXISTS_ERROR = "username already taken"
        private val SPINNER_EXISTS_ERROR = "choose city"
        private val EMAIL_ERROR = "Please enter Valid Email!"

        //        private fun userNameValid(etName: EditText, limit: Int): Boolean {
//            val text = etName.text.toString()
//            if (text.length < limit) {
//                etName.error = String.format(NAME_ERROR, "" + limit)
//                return false
//            }
//            return true
//        }
        //  ^[a-z]([._-](?![._-])|[a-z0-9]){3,16}$
        private fun userNameValid(etUsername: EditText): Boolean {
            val text = etUsername.text.toString()
            val regex = "^[a-z]([._-](?![._-])|[a-z0-9]){3,16}\$".toRegex()
            if (text == "" || !text.matches(regex)) {
                etUsername.error = USER_NAME_ERROR
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


//        fun resetPasswordValid(etPassword: EditText, etConfPassword: EditText): Boolean {
//            userPasswordValid(etPassword)
//            confirmPasswordValid(etPassword, etConfPassword)
//            return userPasswordValid(etPassword) && confirmPasswordValid(etPassword, etConfPassword)
//        }

        fun resetPasswordValid(edEmail: EditText): Boolean {
            return userEmailValid(edEmail)
        }

        fun signInValid(
            etEmail: EditText,
            etPassword: EditText,
        ): Boolean {
            var bool = true
            bool = userEmailValid(etEmail) && bool
            bool = userPasswordValid(etPassword) && bool
            return bool
        }

        fun registerValid(
            etPassword: EditText,
            etConfPassword: EditText,
            etEmail: EditText,
            etUserName: EditText
        ): Boolean {
            var bool = true
            //NOTE: edName equ to private name
            bool = userEmailValid(etEmail) && bool
            bool = userNameValid(etUserName) && bool
            bool = userPasswordValid(etPassword) && bool
            bool = confirmPasswordValid(etPassword, etConfPassword) && bool
            return bool;
        }

        fun changePasswordValid( //TODO: check old password
            oldPassword: EditText,
            newPassword: EditText,
            ConfNewPassword: EditText
        ): Boolean {
            var bool = true
            bool = userPasswordValid(newPassword) && bool
            bool = confirmPasswordValid(newPassword, ConfNewPassword) && bool
            return bool;

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
            var bool = true
            //NOTE: edName equ to private name
//            bool = userNameValid(etName, 2) && bool
//            bool = userNameValid(etLastName, 2) && bool
//            bool = userNameValid(etUserName, 5) && bool
            bool = spinnerCheck(spinner) && bool
            bool = isEmpty(etStreet) && bool
            bool = isEmpty(etStreetNumber) && bool
            bool = isEmpty(etFloor) && bool
            bool = isEmpty(etApartment) && bool
            bool = userPhoneValid(etPhone) && bool
            bool = userEmailValid(etEmail) && bool
            bool = userPasswordValid(etPassword) && bool
            bool = confirmPasswordValid(etPassword, etConfPassword) && bool
            return bool;
//            return userNameValid(etName, 2) &&
//                    userNameValid(etLastName, 2) &&
//                    userNameValid(etUserName, 5) &&
//                    spinnerCheck(spinner) &&
//                    isEmpty(etStreet) &&
//                    isEmpty(etStreetNumber) &&
//                    isEmpty(etFloor) &&
//                    isEmpty(etApartment) &&
//                    userPhoneValid(etPhone) &&
//                    userEmailValid(etEmail) &&
//                    userPasswordValid(etPassword) &&
//                    confirmPasswordValid(etPassword, etConfPassword)
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
            var bool = true
            //NOTE: edName equ to private name
//            bool = userNameValid(upName, 2) && bool
            //userNameValid(upLastName,2);
            //userNameValid(upUserName,5);
            bool = spinnerCheck(upSpinner) && bool
            bool = isEmpty(upStreet) && bool
            bool = isEmpty(upStreetNumber) && bool
            bool = isEmpty(upFloor) && bool
            bool = isEmpty(upApartment) && bool
            bool = userPhoneValid(upPhone) && bool
            bool = userPasswordValid(upPassword) && bool
            bool = confirmPasswordValid(upPassword, upConfPassword) && bool
            return bool
//            return userNameValid(upName, 2) &&
//                    spinnerCheck(upSpinner) &&
//                    isEmpty(upStreet) &&
//                    isEmpty(upStreetNumber) &&
//                    isEmpty(upFloor) &&
//                    isEmpty(upApartment) &&
//                    userPhoneValid(upPhone) &&
//                    userPasswordValid(upPassword) &&
//                    confirmPasswordValid(upPassword, upConfPassword)
        }
    }
}

package com.example.haltura.activities

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.haltura.R
import com.example.haltura.Sql.UserOpenHelper
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.example.haltura.Helpers.Validation.Companion.signInValid
import com.example.haltura.Sql.Items.RetroUser
import com.example.haltura.Sql.Items.UserSerializable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.ktx.Firebase
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true
    private var TAG = "LoginActivity"

    companion object {
        private const val RC_SIGN_IN = 120
    }

    //private lateinit var login: Button
    private lateinit var helper: UserOpenHelper
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        helper = UserOpenHelper(this)
        etEmail = findViewById<View>(R.id.et_Email) as EditText
        etPassword = findViewById<View>(R.id.et_Password) as EditText
        oneTapClient = Identity.getSignInClient(this)


        //auth = Firebase.auth // todo: move to user open helper

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

    }


//    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
//        onStartLoading()
//        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//        mAuth.signInWithCredential(credential).addOnCompleteListener {
//            if (it.isSuccessful) {
//                val userId = it.result!!.user!!.uid
//                db.collection("users").document(userId)
//                    .get()
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val document = task.result
//
//                            if (document!!["email"] != null) {
//                                onFinishLoading()
//                                val userInfo = document.toObject(UserModel::class.java)
//                                MyApplication.currentUser = userInfo
//                                FirestoreUtil.updateUser(MyApplication.currentUser!!) {
//                                }
//                                startHomeNavigation()
//                            } else {
//                                createGoogleUser(account)
//                            }
//                        } else {
//                            onFinishLoading()
//                            _errorString.value = task.exception?.message
//                        }
//                    }
//
//            } else {
//                // If sign in fails, display a message to the user.
//                onFinishLoading()
//                _errorString.value = "Authentication failed."
//            }
//        }
//
//    }
//
//
//    fun handleGoogleSignInResult(data: Intent?) {
//        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//        try {
//            val account = task.getResult(ApiException::class.java)
//            firebaseAuthWithGoogle(account!!)
//        } catch (e: ApiException) {
//            e.printStackTrace()
//            _errorString.value = e.message
//        }
//    }


    fun googleSignIn(view: View) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("SignInActivity", "signInWithCredential:failure")
                }
            }
    }


    fun signIn(view: View) {
        if (signInValid(
                etEmail, etPassword
            )
        ) {
            val user = UserSerializable(
                etEmail.text.toString(),
                "",
                "",
                "",
                etPassword.text.toString()
            )// ,"0","0","0")
            helper.userSignIn(user)
        }
    }

    fun signUp(view: View) {
        //startActivity(Intent(this, AddWorkActivity::class.java))
//        startActivity(Intent(this, SignUpActivity::class.java))
        startActivity(Intent(this, RegisterActivity::class.java))
//        //todo: do this with user open helper - It is not the responsibility of the class
    }

    fun forgotPassword(view: View) {
        startActivity(Intent(this, ForgotYourPasswordActivity::class.java))
    }

//    fun googleSignIn(view: View) {
//        signInRequest = BeginSignInRequest.builder()
//        .setGoogleIdTokenRequestOptions(
//            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                .setSupported(true)
//                // Your server's client ID, not your Android client ID.
//                .setServerClientId(getString(R.string.your_web_client_id))
//                // Only show accounts previously used to sign in.
//                .setFilterByAuthorizedAccounts(true)
//                .build())
//        .build()
//    }
//
//
//
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (requestCode) {
//            REQ_ONE_TAP -> {
//                try {
//                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val idToken = credential.googleIdToken
//                    when {
//                        idToken != null -> {
//                            // Got an ID token from Google. Use it to authenticate
//                            // with Firebase.
//                            Log.d(TAG, "Got ID token.")
//                        }
//                        else -> {
//                            // Shouldn't happen.
//                            Log.d(TAG, "No ID token!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    // ...
//                }
//            }
//        }
//        val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
//        val idToken = googleCredential.googleIdToken
//        when {
//            idToken != null -> {
//                // Got an ID token from Google. Use it to authenticate
//                // with Firebase.
//                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                auth.signInWithCredential(firebaseCredential)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success")
//                            val user = auth.currentUser
//                            //updateUI(user) todo:
//                            Toast.makeText(baseContext, "google Login Successful!", Toast.LENGTH_SHORT).show()
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.exception)
//                            Toast.makeText(baseContext, "Login unSuccessful!", Toast.LENGTH_SHORT).show()
//                            //updateUI(null) todo:
//                        }
//                    }
//            }
//            else -> {
//                // Shouldn't happen.
//                Log.d(TAG, "No ID token!")
//            }
//        }
//    }

}

//    private fun updateUI(currentUser: FirebaseUser?) {
//        if(currentUser != null){
//            Toast.makeText(baseContext, "Login Successful!", Toast.LENGTH_SHORT).show()
//            startActivity(
//                Intent(this,
//                MainActivity::class.java)
//            )
//            finish()
//        }
//    }
//
//
//    fun signInWithCustomToken(){
////        customToken?.let {
////            auth.signInWithCustomToken(it)
////                .addOnCompleteListener(this) { task ->
////                    if (task.isSuccessful) {
////                        // Sign in success, update UI with the signed-in user's information
////                        Log.d(TAG, "signInWithCustomToken:success")
////                        val user = auth.currentUser
////                        updateUI(user)
////                    } else {
////                        // If sign in fails, display a message to the user.
////                        Log.w(TAG, "signInWithCustomToken:failure", task.exception)
////                        Toast.makeText(baseContext, "Authentication failed.",
////                            Toast.LENGTH_SHORT).show()
////                        updateUI(null)
////                    }
////                }
//        }

//    private fun loginUserValidation() {
//
//        if (etEmail.text.toString().isEmpty()) {
//            etEmail.error = "Please enter Email"
//            return
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
//            etEmail.error = "Please enter Valid Email"
//            etEmail.requestFocus()
//            return
//        }
//        if (etPassword.text.toString().isEmpty()) {
//            etPassword.error = "Please enter Password"
//            etPassword.requestFocus()
//            return
//        }
//        if (etPassword.length() < 6) {
//            etPassword.error = "Password must be > 6 characters"
//            return
//        }
//
//        auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val user: FirebaseUser? = auth.currentUser
//                    updateUI(user)
//                } else {
//                    Toast.makeText(baseContext, "Wrong email or password.", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
//                }
//            }
//    }


//    fun login(view: View)
//    {
//        if (helper.checkUser(etUserName.text.toString(), etPassword.text.toString())) {
//            UserData.currentUser = helper.getUserByUsername(etUserName.text.toString())
//            if (UserData.currentUser != null) {
//                //                     Toast.makeText(LogInActivity.this, UserData.currentUser.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "login complete!", Toast.LENGTH_SHORT).show()
//                val mainIntent: Intent
//                mainIntent = if (UserData.isAdmin()) {
//                    Intent(this, LoginActivity::class.java)
//                } else {
//                    Intent(this, MainActivity::class.java)
//                }
//                startActivity(mainIntent)
//                finish()
//            }
//        } else {
//            Toast.makeText(
//                this,
//                "user or password incorrect!",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//    fun register(view: View)
//    {
//        startActivity(Intent(this, SignUpActivity::class.java))
//    }
//    fun forgotPassword(view: View)
//    {
//        startActivity(Intent(this, ForgotYourPasswordActivity::class.java))
//    }



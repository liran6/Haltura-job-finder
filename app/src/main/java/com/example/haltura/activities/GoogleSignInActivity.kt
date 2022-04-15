package com.example.haltura.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haltura.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import com.google.firebase.quickstart.auth.R

import android.view.View
import android.widget.Toast

import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount


import com.google.firebase.firestore.FirebaseFirestore

class GoogleSignInActivity : AppCompatActivity() {
//    private lateinit var oneTapClient: SignInClient
//    private lateinit var signUpRequest: BeginSignInRequest
//    private lateinit var auth: FirebaseAuth.getInstance()
//    lateinit var googleSignInClient: GoogleSignInClient
lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController: NavController
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var drawerLayout: DrawerLayout
    //private lateinit var navViewBinding: DrawerHeaderLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)
        //todo(move to user open helper)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.my_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)



//        oneTapClient = Identity.getSignInClient(this)
//        signUpRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(
//                        getString(
//                            R.string.default_web_client_id
//                        )
//                    )
//                    // Show all accounts on the device.
//                    .setFilterByAuthorizedAccounts(false)
//                    .build()
//            )
//            .build()
    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val userId = it.result!!.user!!.uid
                db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result

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
                        }
                    }

            } else {
                // If sign in fails, display a message to the user.
//                onFinishLoading()
//                _errorString.value = "Authentication failed."
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

    }
}
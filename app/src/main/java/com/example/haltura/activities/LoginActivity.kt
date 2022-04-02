package com.example.haltura.activities
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.haltura.R
import com.example.haltura.Sql.UserOpenHelper
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.haltura.Helpers.Validation.Companion.signInValid
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    //private lateinit var login: Button
    private lateinit var helper: UserOpenHelper
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        helper = UserOpenHelper(this)
        etEmail = findViewById<View>(R.id.et_Email) as EditText
        etPassword = findViewById<View>(R.id.et_Password) as EditText
        oneTapClient = Identity.getSignInClient(this)

//        val currentUser:FirebaseUser? = auth.currentUser
//        updateUI(currentUser)


        //todo google signin by liran code below
//        signInRequest = BeginSignInRequest.builder()
//            .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
//                .setSupported(true)
//                .build())
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(getString(R.string.default_web_client_id))
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            // Automatically sign in when exactly one credential is retrieved.
//            .setAutoSelectEnabled(true)
//            .build()


    }

    public override fun onStart() {
        super.onStart()
        //todo: Check if user is signed in (non-null) and update UI accordingly.
        helper.isUserLoggedIn()
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            updateUI(currentUser);
//        }
    }

    fun signIn(view: View) {
        if (signInValid(
                etEmail, etPassword
            )
        ) {
            helper.userSignIn(etEmail, etPassword)
        }
    }

    fun signUp(view: View) {
        startActivity(Intent(this, SignUpActivity::class.java))
//        //todo: do this with user open helper - It is not the responsibility of the class
//        auth.createUserWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString())
    }

    fun forgotPassword(view: View)
    {
        startActivity(Intent(this, ForgotYourPasswordActivity::class.java))
    }
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

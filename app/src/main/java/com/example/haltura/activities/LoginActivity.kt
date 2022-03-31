package com.example.haltura.activities
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.haltura.R
import com.example.haltura.Sql.UserOpenHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var login: Button
    private lateinit var helper: UserOpenHelper
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        helper = UserOpenHelper()//this)
        etEmail = findViewById<View>(R.id.et_Email) as EditText
        etPassword = findViewById<View>(R.id.et_Password) as EditText
        login = findViewById<View>(R.id.btn_SignIn) as Button
    }
    public override fun onStart() {
        super.onStart()
        //todo: Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
    fun signIn(view: View){

    }
    fun signUp(view: View){
        auth.createUserWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString())
    }
    fun signInWithCustomToken(){
//        customToken?.let {
//            auth.signInWithCustomToken(it)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithCustomToken:success")
//                        val user = auth.currentUser
//                        updateUI(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithCustomToken:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
//                    }
//                }
        }

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
}
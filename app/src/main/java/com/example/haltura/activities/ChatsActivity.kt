package com.example.haltura.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.haltura.R
import com.example.haltura.Util.FirestoreUtil
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.Section
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haltura.Adapters.MessagesAdapter
import com.example.haltura.Adapters.MyButtonObserver
import com.example.haltura.Adapters.MyOpenDocumentContract
import com.example.haltura.Adapters.MyScrollToBottomObserver
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.*
import com.firebase.ui.database.BuildConfig
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.example.haltura.databinding.ActivityChatsBinding
import com.example.haltura.Sql.Items.Message
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage



class ChatsActivity : AppCompatActivity() {

//    private lateinit var userListenerRegistration: ListenerRegistration
//
//    private var shouldInitRecyclerView = true
//
//    private lateinit var peopleSection: Section
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chats)

//        userListenerRegistration =
//            FirestoreUtil.addUsersListener(this.activity!!, this::updateRecyclerView)
//    }



    private lateinit var binding: ActivityChatsBinding
    private lateinit var manager: LinearLayoutManager

    // Firebase instance variables
    private var auth =  FirebaseAuth.getInstance()
    //private lateinit var auth: FirebaseAuth.getInstance()
    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: MessagesAdapter

    private val openDocument = registerForActivityResult(MyOpenDocumentContract.MyOpenDocumentContract()) { uri ->
        onImageSelected(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This codelab uses View Binding
        // See: https://developer.android.com/topic/libraries/view-binding
        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // When running in debug mode, connect to the Firebase Emulator Suite
        // "10.0.2.2" is a special value which allows the Android emulator to
        // connect to "localhost" on the host computer. The port values are
        // defined in the firebase.json file.
        if (BuildConfig.DEBUG) {
//            Firebase.database.useEmulator("10.0.2.2", 9000)
//            Firebase.auth.useEmulator("10.0.2.2", 9099)
//            Firebase.storage.useEmulator("10.0.2.2", 9199)
        }

        // Initialize Firebase Auth and check if the user is signed in
//        auth = Firebase.auth
//        if (auth.currentUser == null) {
//            // Not signed in, launch the Sign In activity
//            startActivity(Intent(this, SignInActivity::class.java))
//            finish()
//            return
//        }

        // Initialize Realtime Database
        db = Firebase.database
        val messagesRef = db.reference.child(MESSAGES_CHILD)

        // The FirebaseRecyclerAdapter class and options come from the FirebaseUI library
        // See: https://github.com/firebase/FirebaseUI-Android
        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, Message::class.java)
            .build()
        adapter = MessagesAdapter(options, getUserName())
        binding.progressBar.visibility = ProgressBar.INVISIBLE
        manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager
        binding.messageRecyclerView.adapter = adapter

        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver for details
        adapter.registerAdapterDataObserver(
            MyScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
        )

        // Disable the send button when there's no text in the input field
        // See MyButtonObserver for details
        binding.messageEditText.addTextChangedListener(MyButtonObserver(binding.sendButton))

        // When the send button is clicked, send a text message
        binding.sendButton.setOnClickListener {
            val friendlyMessage = Message(
                binding.messageEditText.text.toString(),
                getUserName(),
                getPhotoUrl(),
                null
            )
            db.reference.child(MESSAGES_CHILD).push().setValue(friendlyMessage)
            binding.messageEditText.setText("")
        }

        // When the image button is clicked, launch the image picker
        binding.addMessageImageView.setOnClickListener {
            openDocument.launch(arrayOf("image/*"))
        }
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in.
//        if (auth.currentUser == null) {
//            // Not signed in, launch the Sign In activity
//            startActivity(Intent(this, SignInActivity::class.java))
//            finish()
//            return
//        }
//    }

    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.main_menu, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.sign_out_menu -> {
//                signOut()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun onImageSelected(uri: Uri) {
        Log.d(TAG, "Uri: $uri")
        val user = auth.currentUser
        val tempMessage = Message(null, getUserName(), getPhotoUrl(), LOADING_IMAGE_URL)
        db.reference
            .child(MESSAGES_CHILD)
            .push()
            .setValue(
                tempMessage,
                DatabaseReference.CompletionListener { databaseError, databaseReference ->
                    if (databaseError != null) {
                        Log.w(
                            TAG, "Unable to write message to database.",
                            databaseError.toException()
                        )
                        return@CompletionListener
                    }

                    // Build a StorageReference and then upload the file
                    val key = databaseReference.key
                    val storageReference = Firebase.storage
                        .getReference(user!!.uid)
                        .child(key!!)
                        .child(uri.lastPathSegment!!)
                    putImageInStorage(storageReference, uri, key)
                })
    }

    private fun putImageInStorage(storageReference: StorageReference, uri: Uri, key: String?) {
        // First upload the image to Cloud Storage
        storageReference.putFile(uri)
            .addOnSuccessListener(
                this
            ) { taskSnapshot -> // After the image loads, get a public downloadUrl for the image
                // and add it to the message.
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        val friendlyMessage =
                            Message(null, getUserName(), getPhotoUrl(), uri.toString())
                        db.reference
                            .child(MESSAGES_CHILD)
                            .child(key!!)
                            .setValue(friendlyMessage)
                    }
            }
            .addOnFailureListener(this) { e ->
                Log.w(
                    TAG,
                    "Image upload task was unsuccessful.",
                    e
                )
            }
    }

//    private fun signOut() {
//        AuthUI.getInstance().signOut(this)
//        startActivity(Intent(this, SignInActivity::class.java))
//        finish()
//    }

    private fun getPhotoUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }

    private fun getUserName(): String? {
        val user = auth.currentUser
        return if (user != null) {
            user.displayName
        } else ANONYMOUS
    }

    companion object {
        private const val TAG = "MainActivity"
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}
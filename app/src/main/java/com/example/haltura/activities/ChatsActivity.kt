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
    private lateinit var binding: ActivityChatsBinding
    private lateinit var manager: LinearLayoutManager
    private var adapter: MessagesAdapter = MessagesAdapter(this)
    private val openDocument = registerForActivityResult(MyOpenDocumentContract.MyOpenDocumentContract())
    {
        uri -> adapter.onImageSelected(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This codelab uses View Binding
        // See: https://developer.android.com/topic/libraries/view-binding
        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // The FirebaseRecyclerAdapter class and options come from the FirebaseUI library
        // See: https://github.com/firebase/FirebaseUI-Android
        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(adapter.getMessagesRef(), Message::class.java)
            .build()

        //adapter = MessagesAdapter(this)
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
                adapter.getUserName(),
                adapter.getPhotoUrl(),
                null
            )
            //db.reference.child(MESSAGES_CHILD).push().setValue(friendlyMessage)
            adapter.sendingMessage(friendlyMessage)
            binding.messageEditText.setText("")
        }

        // When the image button is clicked, launch the image picker
        binding.addMessageImageView.setOnClickListener {
            openDocument.launch(arrayOf("image/*"))
        }
    }


    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }


    companion object {
        const val TAG = "ChatsActivity"
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}
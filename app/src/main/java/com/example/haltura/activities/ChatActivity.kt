package com.example.haltura.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.*
import com.example.haltura.R
import com.example.haltura.Sql.Items.ImageMessage
import com.example.haltura.Sql.Items.Message
import com.example.haltura.Sql.Items.TextMessage
import com.example.haltura.Sql.Items.Time
import com.example.haltura.Sql.UserOpenHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.image
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ChatActivity : AppCompatActivity() {

    private var auth =  FirebaseAuth.getInstance() //todo delete(move to open helper)
    private lateinit var helper: UserOpenHelper

    var context: MyAdapter.OnWorkListener? = null

    private lateinit var dbref : DatabaseReference
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageArrayList: ArrayList<Message>
    private lateinit var sendButton: ImageView
    private lateinit var textMessage: EditText
    private lateinit var manager: LinearLayoutManager
    private lateinit var adapt: ChatAdapter
    private lateinit var activity: Activity
    private  var isFirstTime = true
    //private lateinit var userImage: String

    //private var adapter: MessagesAdapter = ChatAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        activity = this
        sendButton = findViewById(R.id.sendButton)
        textMessage = findViewById(R.id.messageEditText)
        messageRecyclerView = findViewById(R.id.messageRecyclerView)
        manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        messageRecyclerView.layoutManager = manager

        messageArrayList = arrayListOf<Message>()
        // This codelab uses View Binding
        // See: https://developer.android.com/topic/libraries/view-binding

        getMessageData()

        //todo:
        // Scroll down when a new message arrives
        // See MyScrollToBottomObserver for details
        // adapter.registerAdapterDataObserver(
        //     MyScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
        // )
        sendButton.setEnabled(false)
        textMessage.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().trim { it <= ' ' }.length == 0) {
                    sendButton.setEnabled(false)
                    sendButton.setImageDrawable(getResources().getDrawable(R.drawable.outline_send_gray_24))
                } else {
                    sendButton.setEnabled(true)
                    sendButton.setImageDrawable(getResources().getDrawable(R.drawable.outline_send_24));
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
    }

    private fun getMessageData() {
        //todo pick specific chat
        dbref = FirebaseDatabase.getInstance().getReference("Messages")


        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //messageArrayList.clear()
                if (snapshot.exists())
                {
                    for(messageSnapshot in snapshot.children)
                    {
                        //todo check if we can make it specific
                        val w = messageSnapshot.getValue(Message::class.java)
                        when (w?.getType()) {
                            0 -> {
                                //var w = messageSnapshot.getValue(TextMessage::class.java)
                                messageArrayList.add(messageSnapshot.getValue(TextMessage::class.java)!!)
                            }
                            1 -> {
                                //var w = messageSnapshot.getValue(ImageMessage::class.java)
                                messageArrayList.add(messageSnapshot.getValue(ImageMessage::class.java)!!)
                            }
                            else -> {
                                //todo err
                            }
                        }
                    }
//                    adapt = ChatAdapter(messageArrayList)
//                    //messageRecyclerView.adapter = ChatAdapter(messageArrayList)
//                    messageRecyclerView.adapter = adapt
//                    adapt.notifyDataSetChanged()
                    //todo: onOnChatOpenHelper create the List and add func to notify when new message arrives
                }
                adapt = ChatAdapter(messageArrayList, activity)
                //messageRecyclerView.adapter = ChatAdapter(messageArrayList)
                messageRecyclerView.adapter = adapt
                //adapt.notifyDataSetChanged()
                isFirstTime = false
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        dbref.addChildEventListener(object : ChildEventListener
        {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (isFirstTime)
                {
                    return
                }
                var w = snapshot.getValue(Message::class.java)
                when (w?.getType()) {
                    0 ->
                    {
                        messageArrayList.add(snapshot.getValue(TextMessage::class.java)!!)
                    }
                    1 ->
                    {
                        messageArrayList.add(snapshot.getValue(ImageMessage::class.java)!!)
                    }
                    else ->
                    {
                        //todo err
                    }
                }
                //adapt.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    fun sendMessage(view: View)
    {
        //todo: send the text message

        val msg = TextMessage(
            textMessage.text.toString(),
            auth.getCurrentUser()?.getUid()!!,
            "test",
            getPhotoUrl()!!,
            Time(1,1)
            )
        //adapter.sendMessage(msg)
        //var helper =  ChatOpenHelper(this) //todo: move to global
        dbref = FirebaseDatabase.getInstance().getReference("Messages")
        dbref.push().setValue(msg)
        textMessage.setText("")
    }



    fun getPhotoUrl(): String? {
        //todo: make it work (can upload the image and use current method)
        //val user = auth.currentUser
        //user?.photoUrl
        //sendButton.setImageURI(user?.photoUrl)
        //var bitmap =
        var bitmap = getResources().getDrawable(R.drawable.haltura_icon2).toBitmap()
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        return Base64.encodeToString(data, Base64.DEFAULT)

        //val user = auth.currentUser
//        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, user?.photoUrl)
//        val baos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val data = baos.toByteArray()
//        return Base64.encodeToString(data, Base64.DEFAULT)
        //todo: take image from user (not google login)
    }

    fun getUserName(): String? {
        val user = auth.currentUser
        return if (user != null) {
            user.displayName
        } else "ANONYMOUS"
        //todo: take name from user (not google login)
    }

    fun uploadImage(view: View)
    {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }
    fun takeCameraImage(view: View)
    {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) {
            if (data != null) {
//                var bm = data.extras!!["data"] as Bitmap?
//                bm.toString()
//                val msg = TextMessage()
//                adapter.sendMessage(msg)
//                binding.messageEditText.setText("")
            }
            //todo: toast err
        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            //todo:check if it is work
            if (data != null) {
//                var bm = data.extras!!["data"] as Bitmap?
//                bm.toString()
//                val msg = TextMessage()
//                adapter.sendMessage(msg)
//                binding.messageEditText.setText("")
            }
            //todo: toast err
        }
    }

    companion object {
        const val TAG = "ChatActivity"
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
        private val REQ_CAMERA = 1
        private  val PICK_IMAGE = 2
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}
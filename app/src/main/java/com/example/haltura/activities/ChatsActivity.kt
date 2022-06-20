package com.example.haltura.activities
//
//import android.app.Activity
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.example.haltura.R
//import android.content.Intent
//import android.text.Editable
//import android.text.TextWatcher
//import android.widget.EditText
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.haltura.Adapters.*
//import com.example.haltura.Sql.Items.Chat
////import com.example.haltura.Adapters.MessagesAdapter
//import com.google.firebase.auth.FirebaseAuth
//import com.example.haltura.Sql.Items.Message
//import com.example.haltura.Sql.Items.Time
//import com.example.haltura.Sql.UserOpenHelper
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.firebase.database.*
//
//
//class ChatsActivity : AppCompatActivity() {
//    private var auth =  FirebaseAuth.getInstance() //todo delete(move to open helper)
//    private lateinit var helper: UserOpenHelper
//
//    //var context: MyAdapter.OnWorkListener? = null
//
//    private lateinit var dbref : DatabaseReference
//    private lateinit var chatsRecyclerView: RecyclerView //
//    private lateinit var chatsArrayList: ArrayList<Chat>
//    private lateinit var searchToolbar: EditText //toolbar_search
//    private lateinit var manager: LinearLayoutManager
//    private lateinit var adapt: ChatsAdapter
//    private lateinit var activity: Activity
//
//
//    override fun onRestart() {
//        super.onRestart()
//        adapt.notifyDataSetChanged()
//        //todo reset adapter
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chats)
//        //init()
//        activity = this
//        searchToolbar = findViewById(R.id.toolbar_search)
//        chatsRecyclerView = findViewById(R.id.chatsRecyclerView)
//        manager = LinearLayoutManager(this)
//        manager.stackFromEnd = false
//        chatsRecyclerView.layoutManager = manager
//
//        chatsArrayList = arrayListOf<Chat>()
//
//        getChatsData()
//
//        searchToolbar.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.toString().trim { it <= ' ' }.length == 0) {
//                    //todo: filter
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                // TODO Auto-generated method stub
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                // TODO Auto-generated method stub
//            }
//        })
//    }
//
//    fun init()
//    {
//        val members = ArrayList<String>()
//        members.add("0RQ6xNR5mvZ1WByrJGDZusszFPj2")
//        members.add("C8AKORnTi7QKL5yxqwwxcHpP2O03")
//        val messages = ArrayList<Message>()
//        messages.add(Message(
//            "test1",
//            null,
//            getUserid(),
//            getUserName(),
//            Time(1,1)
//        ))
//        messages.add(Message(
//            "test2",
//            null,
//            getUserid(),
//            getUserName(),
//            Time(1,1)
//        ))
//        val chat = Chat(
//            members,
//            messages
//        )
//        var dbrefChat = FirebaseDatabase.getInstance().getReference("Chats")
//        val key = dbrefChat.push().key
//        chat.setChatId(key)
//        if (key != null) {
//            dbrefChat.child(key).setValue(chat)
//        }
//        //todo: add to list of chats of each members
//        var dbrefUsers = FirebaseDatabase.getInstance().getReference("/Users/0RQ6xNR5mvZ1WByrJGDZusszFPj2/").child("chats")
//        dbrefUsers.child("1").setValue(key)
//        dbrefUsers = FirebaseDatabase.getInstance().getReference("/Users/0RQ6xNR5mvZ1WByrJGDZusszFPj2/").child("contacts")
//        dbrefUsers.child("C8AKORnTi7QKL5yxqwwxcHpP2O03").setValue("C8AKORnTi7QKL5yxqwwxcHpP2O03")
//        dbrefUsers = FirebaseDatabase.getInstance().getReference("/Users/C8AKORnTi7QKL5yxqwwxcHpP2O03/").child("chats")
//        dbrefUsers.child("1").setValue(key)
//        dbrefUsers = FirebaseDatabase.getInstance().getReference("/Users/C8AKORnTi7QKL5yxqwwxcHpP2O03/").child("contacts")
//        dbrefUsers.child("0RQ6xNR5mvZ1WByrJGDZusszFPj2").setValue("0RQ6xNR5mvZ1WByrJGDZusszFPj2")
//    }
//
//    private fun getChatsData() {
//        //todo pick specific chat
//        dbref = FirebaseDatabase.getInstance().getReference("Users/"+getUserid()+"/chats/")
////        adapt =
////            ChatsAdapter(chatsArrayList, getUserid(), _clickOnItemListener = { onClickChat(it) })
////        chatsRecyclerView.adapter = adapt
//
//
//        dbref.addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists())
//                {
//                    adapt = ChatsAdapter(chatsArrayList, getUserid(), _clickOnItemListener = { onClickChat(it) })
//                    chatsRecyclerView.adapter = adapt
//                    var dbrefChats = FirebaseDatabase.getInstance().getReference("/Chats/")
////                    var chatsArrayList = chatsArrayList
////                    var adapt = adapt
//                    for(chatSnap in snapshot.children)
//                    {
//                        //mDatabase.child("users").child(userId).get().addOnSuccessListener {
//                        //    Log.i("firebase", "Got value ${it.value}")
//                        //}
//                        //chatSnap.value
//                        dbrefChats.child(chatSnap.value.toString()).get().addOnCompleteListener(
//                            OnCompleteListener { task ->
//                                var chatsArrayList = chatsArrayList
//                                var adapt = adapt
//                                if (task.isSuccessful) {
//                                val chat = task.result.getValue(Chat::class.java)
//                                chatsArrayList.add(chat!!)
//                                adapt.notifyDataSetChanged()
//                            }
//
////                            {
////                            val chat = it.getValue(Chat::class.java)
////                            rChatsArrayList.add(chat!!)
////                            rAdapt.notifyDataSetChanged()
////                        }.addOnFailureListener{
////                            Log.e("firebase", "Error getting data", it)
////                        }
//
////                        val chat = chatSnap.getValue(Chat::class.java)
////                        chatsArrayList.add(chat!!)
//                            })
//                    }
//                    //Thread.sleep(10_000)//todo: make it wait for the data
////                    adapt = ChatsAdapter(chatsArrayList, getUserid(), _clickOnItemListener = { onClickChat(it) })
////                    chatsRecyclerView.adapter = adapt
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//
//
////        dbref.addChildEventListener(object : ChildEventListener {
////            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
////                var chat = snapshot.getValue(Chat::class.java)
////                if (chat != null) {
////                    chatsArrayList.add(chat)
////                    adapt.notifyDataSetChanged()
////                }
////                else {
////                    //todo err (error text message)
////                }
////                //adapt.notifyDataSetChanged() //todo: delete?
////            }
////
////            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
////                TODO("Not yet implemented")
////            }
////
////            override fun onChildRemoved(snapshot: DataSnapshot) {
////                TODO("Not yet implemented")
////            }
////
////            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
////                TODO("Not yet implemented")
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////                TODO("Not yet implemented")
////            }
////
////        })
//    }
//
////    fun sendMessage(view: View)
////    {
////        //todo: send the text message
////
////        val msg = Message(
////            textMessage.text.toString(),
////            null,
////            getUserid(),
////            getUserName(),
////            Time(1,1)
////        )
////        //adapter.sendMessage(msg)
////        //var helper =  ChatOpenHelper(this) //todo: move to global
////        dbref = FirebaseDatabase.getInstance().getReference("Messages")
////        dbref.push().setValue(msg)
////        textMessage.setText("")
////    }
////
////    fun sendImageMessage(image: Bitmap)
////    {
////        val baos = ByteArrayOutputStream()
////        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
////        val data = baos.toByteArray()
////        var txt :String? = null
////        if(textMessage.text.toString() != "")
////        {
////            txt = textMessage.text.toString()
////        }
////        val msg = Message(
////            txt,
////            Base64.encodeToString(data, Base64.DEFAULT),
////            getUserid(),
////            getUserName(),
////            Time(1,1)
////        )
////        //adapter.sendMessage(msg)
////        //var helper =  ChatOpenHelper(this) //todo: move to global
////        dbref = FirebaseDatabase.getInstance().getReference("Messages")
////        dbref.push().setValue(msg)
////        textMessage.setText("")
////    }
//
//    private fun onClickChat(chat: Chat)
//    {
//        var i = Intent(this, ChatActivity::class.java)
//        i.putExtra("chatId",chat.getChatId())
//        this.startActivity(i)
//        //todo: open and set priority in chat order (will be first when we open this activity again)
//    }
//
//    fun getUserid(): String {
//        val user = auth.currentUser
//        if (user != null) {
//            return user.getUid()!!!!
//        }
//        else {
//            return "err"
//        }
//        //todo: take userid from user (not google login)
//    }
//
//    fun getUserName(): String {
//        val user = auth.currentUser
//        if (user != null) {
//            return user.displayName!!
//        }
//        else {
//            return "ANONYMOUS"
//        }
//        //todo: take name from user (not google login)
//    }
//
//    companion object {
//        const val TAG = "ChatsActivity"
//        const val MESSAGES_CHILD = "messages"
//        const val ANONYMOUS = "anonymous"
//        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
//    }
//}


















///old


//    private lateinit var binding: ActivityChatsBinding
//    private lateinit var manager: LinearLayoutManager
//    private var adapter: MessagesAdapter = MessagesAdapter(this)
//
//    private val openDocument = registerForActivityResult(MyOpenDocumentContract.MyOpenDocumentContract())
//    {
//        uri -> adapter.onImageSelected(uri)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // This codelab uses View Binding
//        // See: https://developer.android.com/topic/libraries/view-binding
//        binding = ActivityChatsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        // The FirebaseRecyclerAdapter class and options come from the FirebaseUI library
//        // See: https://github.com/firebase/FirebaseUI-Android
//        val options = FirebaseRecyclerOptions.Builder<Message>()
//            .setQuery(adapter.getMessagesRef(), Message::class.java)
//            .build()
//
//        //adapter = MessagesAdapter(this)
//        binding.progressBar.visibility = ProgressBar.INVISIBLE
//        manager = LinearLayoutManager(this)
//        manager.stackFromEnd = true
//        binding.messageRecyclerView.layoutManager = manager
//        binding.messageRecyclerView.adapter = adapter
//
//        // Scroll down when a new message arrives
//        // See MyScrollToBottomObserver for details
//        adapter.registerAdapterDataObserver(
//            MyScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
//        )
//
//        // Disable the send button when there's no text in the input field
//        // See MyButtonObserver for details
//        binding.messageEditText.addTextChangedListener(MyButtonObserver(binding.sendButton))
//
//        // When the send button is clicked, send a text message
//        binding.sendButton.setOnClickListener {
//            val friendlyMessage = Message(
//                binding.messageEditText.text.toString(),
//                adapter.getUserName(),
//                adapter.getPhotoUrl(),
//                null
//            )
//            //db.reference.child(MESSAGES_CHILD).push().setValue(friendlyMessage)
//            adapter.sendingMessage(friendlyMessage)
//            binding.messageEditText.setText("")
//        }
//
//        // When the image button is clicked, launch the image picker
//        binding.addMessageImageView.setOnClickListener {
//            openDocument.launch(arrayOf("image/*"))
//        }
//    }
//
//
//    public override fun onPause() {
//        adapter.stopListening()
//        super.onPause()
//    }
//
//    public override fun onResume() {
//        super.onResume()
//        adapter.startListening()
//    }
//
//
//    companion object {
//        const val TAG = "ChatsActivity"
//        const val MESSAGES_CHILD = "messages"
//        const val ANONYMOUS = "anonymous"
//        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
//    }
//}
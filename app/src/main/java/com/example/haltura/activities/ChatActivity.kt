package com.example.haltura.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.R
import com.example.haltura.Sql.Items.ChatSerializable
import com.example.haltura.ViewModels.ChatViewModel
import com.example.haltura.Adapters.ChatAdapter2
import com.example.haltura.Fragments.ChatFragments.ChatFragment


//todo:
// Scroll down when a new message arrives
// See MyScrollToBottomObserver for details
// adapter.registerAdapterDataObserver(
//     MyScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
// )

class ChatActivity : AppCompatActivity() {

    private lateinit var _viewModel: ChatViewModel
    private lateinit var _messageRecycle: RecyclerView
    private lateinit var _sendButton: ImageView
    private lateinit var _cameraButton: ImageView
    private lateinit var _galleryButton: ImageView
    private lateinit var _topBar: LinearLayout
    private lateinit var _chatImage: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var _backButton: ImageView
    private lateinit var _chatName: TextView
    private lateinit var _members: TextView
    private lateinit var _textMessage: EditText
    private lateinit var _manager: LinearLayoutManager
    private lateinit var _chatAdapter: ChatAdapter2
    private lateinit var _chat: ChatSerializable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        if (savedInstanceState == null) {
            //val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            supportFragmentManager.beginTransaction()
                .add(R.id.chat_fragment, ChatFragment(), "ChatFragment")
                .addToBackStack("ChatFragment")
                .setReorderingAllowed(true)
                .commit()
        }
//        initViewModel()
//        setChat()
//        initViews()
//        setValues()
//        initViewModelData()
//        initObservers()
//        initButtons()
//        initTextListener()
//        initRecyclersAndAdapters()
//        initTopBar()
//        startLive()
    }

//    private fun initTopBar() {
//        _topBar.setOnClickListener {
//            //showChatInfo(_chat.id!!)
//            _viewModel.getChatInfo(_chat.id!!)
//        }
//    }
//
//    private fun showChatInfo(chatInfo: InfoChatSerializable) {
//        var dialog = ShowChatInfoDialog(chatInfo)
//        this.supportFragmentManager?.let {
//            dialog.show(it, "ShowChatInfoDialog")
//        }
//    }
//
//    private fun initTextListener() {
//        _textMessage.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.toString().trim { it <= ' ' }.length == 0) {
//                    _sendButton.setEnabled(false)
//                    _sendButton.setImageDrawable(getResources().getDrawable(R.drawable.outline_send_gray_24))
//                } else {
//                    _sendButton.setEnabled(true)
//                    _sendButton.setImageDrawable(getResources().getDrawable(R.drawable.outline_send_24));
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
//    private fun startLive() {
//        _viewModel.startLive(_chat.id!!,this)
//    }
//
//    private fun setValues() {
//        //todo: check if not group set just name in the middle
//        if(_chat?.chatName == null)
//        {
//            if (_chat.members[0] == UserData.currentUser!!.userId)
//            {
//                _chatName.setText(_chat!!.mapUsernames[_chat.members[1]])
//            }
//            else
//            {
//                _chatName.setText(_chat!!.mapUsernames[_chat.members[0]])
//            }
//        }
//        else
//        {
//            _chatName.setText(_chat?.chatName)
//        }
//        var members = ""
//        _chat.members.sorted().forEach{
//            members += _chat.mapUsernames[it] + ", "
//        }
//        if (members.length > 2) {
//            members = members.substring(0, members.length - 2)
//        }
//        if (members.length > 30)
//        {
//            members = members.substring(0,30) + "..."
//        }
//        _members.setText(members)
//
//        if (_chat?.chatImage != null)
//        {
//            var bm = Base64.decode(_chat?.chatImage, Base64.DEFAULT)
//            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
//            _chatImage.setImageBitmap(data)
//        }
//        else
//        {
//            //todo: add profile picture
//        }
//    }
//
//    private fun initViewModel() {
//        _viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
//    }
//
//    private fun initButtons() {
//        _cameraButton.setOnClickListener {
//            takeCameraImage()
//        }
//
//        _galleryButton.setOnClickListener {
//            uploadImage()
//        }
//
//        _sendButton.setOnClickListener {
//            sendMessage()
//        }
//        _sendButton.setEnabled(false)
//    }
//
//    private fun initRecyclersAndAdapters() {
//        val chatsList = _viewModel.mutableMessagesList.value!!
//        //_messageRecycle.layoutManager = LinearLayoutManager(context)
//        _chatAdapter = ChatAdapter2(
//            chatsList,
//            _clickOnItemListener = { moveToChat(it) },
//            _chat.mapUsernames
//        )
//        _messageRecycle.adapter = _chatAdapter
//    }
//
//    private fun moveToChat(it: MessageSerializable) {
//        //todo?
//    }
//
//    private fun initObservers() {
//        //todo: check if "viewLifecycleOwner" -> "this"
//        _viewModel.mutableMessagesList.observe(
//            this,
//            Observer { messagesList ->
//                messagesList?.let {
//                    updateRecyclersAndAdapters()
//                }
//            }
//        )
//        _viewModel.mutableChatInfo.observe(
//            this,
//            Observer { ChatInfo ->
//                ChatInfo?.let {
//                    if (it != null) {
//                        showChatInfo(it)
//                    }
//                }
//            }
//        )
//    }
//
//    private fun updateRecyclersAndAdapters() {
//        _chatAdapter.setData(_viewModel.mutableMessagesList.value!!)
//        _chatAdapter.notifyDataSetChanged()
//        //recyclerView.scrollToPosition(items.size());
//        _messageRecycle.smoothScrollToPosition(_chatAdapter.getItemCount());
//    }
//
//    private fun initViewModelData() {
//        _viewModel.getAllMessages(_chat.id!!) //todo: check if _chat.id!! null
//    }
//
//    private fun setChat() {
//        _chat = ChatData.currentChat!!
//        ChatData.currentChat = null
//    }
//
//    private fun initViews() {
//        _topBar = findViewById(R.id.topBar)
//        _chatImage = findViewById(R.id.image_chat)
//        _backButton = findViewById(R.id.back_button)
//        _chatName = findViewById(R.id.name_chat)
//        _members = findViewById(R.id.members)
//        _cameraButton = findViewById(R.id.camera)
//        _galleryButton = findViewById(R.id.gallery)
//        _sendButton = findViewById(R.id.sendButton)
//        _textMessage = findViewById(R.id.messageEditText)
//        _messageRecycle = findViewById(R.id.messageRecyclerView)
//        _manager = LinearLayoutManager(this)
//        _manager.stackFromEnd = true
//        _messageRecycle.layoutManager = _manager
//        _messageRecycle.addItemDecoration(VerticalSpaceItemDecoration(10))
//    }
//
//    fun sendMessage(image: Bitmap? = null)//view: View)
//    {
//        var imageString : String? = null
//        if(image != null)
//        {
//            imageString = convertImageToString(image)
//        }
//        var txt = _textMessage.text.toString()
//
//        if (txt.trim { it <= ' ' }.length == 0)
//        {
//            txt == null
//        }
//
//        if(txt != null || image != null)
//        {
//            _viewModel.SendMessage(MessageSerializable(UserData.currentUser?.userId!!,_textMessage.text.toString(), imageString))
//        }
//
//        _textMessage.setText("")
//    }
//
//    private fun convertImageToString(image: Bitmap): String {
//        val baos = ByteArrayOutputStream()
//        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
//        val data = baos.toByteArray()
//        return  Base64.encodeToString(data, Base64.DEFAULT)
//    }
//
//
//    private fun onClickMessage(message: Message) {
//
//    }
//
//    fun getUserid(): String {
//        //todo: take userid from user (not google login)
//        return null!!
//    }
//
//    fun getUserName(): String {
//        return "ANONYMOUS"
//        //todo: take name from user (not google login)
//    }
//
//    fun uploadImage()
//    {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
//    }
//
//    fun takeCameraImage()
//    {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        //intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//        startActivityForResult(intent, REQ_CAMERA)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) {
//            if (data != null) {
//                sendMessage(data.extras!!["data"] as Bitmap)
//            }
//            //todo: toast err
//        }
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
//            //todo:check if it is work
//            if (data != null) {
//                val uri = data.getData();
//                val imageBitMap =MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
//                sendMessage(imageBitMap)
//            }
//            //todo: toast err
//        }
//    }
//
//    companion object {
//        const val TAG = "ChatActivity"
//        private val REQ_CAMERA = 1
//        private  val PICK_IMAGE = 2
//    }
}





























































///old

//import android.app.Activity
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Base64
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.EditText
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.graphics.drawable.toBitmap
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.haltura.Adapters.*
//import com.example.haltura.R
////import com.example.haltura.Sql.Items.ImageMessage
//import com.example.haltura.Sql.Items.Message
////import com.example.haltura.Sql.Items.TextMessage
//import com.example.haltura.Sql.Items.Time
//import com.example.haltura.Sql.UserOpenHelper
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.*
//import org.jetbrains.anko.image
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import java.io.InputStream
//import java.net.HttpURLConnection
//import java.net.URL


//class ChatActivity : AppCompatActivity() {
//
//    private var auth =  FirebaseAuth.getInstance() //todo delete(move to open helper)
//    private lateinit var helper: UserOpenHelper
//
//    //var context: MyAdapter.OnWorkListener? = null
//
//    private lateinit var dbref : DatabaseReference
//    private lateinit var messageRecyclerView: RecyclerView
//    private lateinit var messageArrayList: ArrayList<Message>
//    private lateinit var sendButton: ImageView
//    private lateinit var textMessage: EditText
//    private lateinit var manager: LinearLayoutManager
//    private lateinit var adapt: ChatAdapter
//    private lateinit var activity: Activity
//    private var isFirstTime = true
//    private var chatId :String? = null
//    private var numOfMessages = 0
//    //private lateinit var userImage: String
//
//    //private var adapter: MessagesAdapter = ChatAdapter(this)
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chat)
//        var extras = getIntent().getExtras()
//        if (extras != null)
//        {
//            this.chatId = extras.getString("chatId")
//            if (this.chatId == null)
//            {
//                //todo: err
//            }
//        }
//        else
//        {
//            //todo: err
//        }
//        activity = this
//        sendButton = findViewById(R.id.sendButton)
//        textMessage = findViewById(R.id.messageEditText)
//        messageRecyclerView = findViewById(R.id.messageRecyclerView)
//        manager = LinearLayoutManager(this)
//        manager.stackFromEnd = true
//        messageRecyclerView.layoutManager = manager
//
//        messageArrayList = arrayListOf<Message>()
//        // This codelab uses View Binding
//        // See: https://developer.android.com/topic/libraries/view-binding
//
//        getMessageData()
//
//        //todo:
//        // Scroll down when a new message arrives
//        // See MyScrollToBottomObserver for details
//        // adapter.registerAdapterDataObserver(
//        //     MyScrollToBottomObserver(binding.messageRecyclerView, adapter, manager)
//        // )
//        sendButton.setEnabled(false)
//        textMessage.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.toString().trim { it <= ' ' }.length == 0) {
//                    sendButton.setEnabled(false)
//                    sendButton.setImageDrawable(getResources().getDrawable(R.drawable.outline_send_gray_24))
//                } else {
//                    sendButton.setEnabled(true)
//                    sendButton.setImageDrawable(getResources().getDrawable(R.drawable.outline_send_24));
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
//    private fun getMessageData() {
//        //todo pick specific chat
//        dbref = FirebaseDatabase.getInstance().getReference("Chats/"+chatId+"/messages")
//
//
//        adapt = ChatAdapter(messageArrayList, getUserid(),_clickOnItemListener = { onClickMessage(it) })
//        messageRecyclerView.adapter = adapt
//
//        dbref.addChildEventListener(object : ChildEventListener
//        {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
////                if (isFirstTime)
////                {
////                    return
////                }
//                var w = snapshot.getValue(Message::class.java)
//                if (w != null) {
//                    numOfMessages += 1
//                    messageArrayList.add(w)
//                    adapt.notifyDataSetChanged()
//                }
//                else
//                {
//                    //todo err (error text message)
//                }
////                when (w?.getType()) {
////                    0 ->
////                    {
////                        messageArrayList.add(snapshot.getValue(TextMessage::class.java)!!)
////                    }
////                    1 ->
////                    {
////                        messageArrayList.add(snapshot.getValue(ImageMessage::class.java)!!)
////                    }
////                    else ->
////                    {
////                        //todo err
////                    }
////                }
//                adapt.notifyDataSetChanged()
//                messageRecyclerView.smoothScrollToPosition(adapt.getItemCount() - 1);
//
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })





//super old



//        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //messageArrayList.clear()
//                if (snapshot.exists())
//                {
//                    for(messageSnapshot in snapshot.children)
//                    {
//                        //todo check if we can make it specific
//                        val w = messageSnapshot.getValue(Message::class.java)
//                        if (w != null) {
//                            messageArrayList.add(w)
//                        }
//                        else
//                        {
//                            //todo err (error text message)
//                        }
////                        when (w?.getType()) {
////                            0 -> {
////                                //var w = messageSnapshot.getValue(TextMessage::class.java)
////                                messageArrayList.add(messageSnapshot.getValue(TextMessage::class.java)!!)
////                            }
////                            1 -> {
////                                //var w = messageSnapshot.getValue(ImageMessage::class.java)
////                                messageArrayList.add(messageSnapshot.getValue(ImageMessage::class.java)!!)
////                            }
////                            else -> {
////                                //todo err
////                            }
////                        }
//                    }
////                    adapt = ChatAdapter(messageArrayList)
////                    //messageRecyclerView.adapter = ChatAdapter(messageArrayList)
////                    messageRecyclerView.adapter = adapt
////                    adapt.notifyDataSetChanged()
//                    //todo: onOnChatOpenHelper create the List and add func to notify when new message arrives
//                }
//                adapt = ChatAdapter(messageArrayList, getUserid(),_clickOnItemListener = { onClickMessage(it) })
//                //messageRecyclerView.adapter = ChatAdapter(messageArrayList)
//                messageRecyclerView.adapter = adapt
//                //adapt.notifyDataSetChanged()
//                isFirstTime = false
//                //initChildEventListener()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })

//        dbref.addChildEventListener(object : ChildEventListener
//        {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                if (isFirstTime)
//                {
//                    return
//                }
//                var w = snapshot.getValue(Message::class.java)
//                if (w != null) {
//                    messageArrayList.add(w)
//                    adapt.notifyDataSetChanged()
//                }
//                else
//                {
//                    //todo err (error text message)
//                }
////                when (w?.getType()) {
////                    0 ->
////                    {
////                        messageArrayList.add(snapshot.getValue(TextMessage::class.java)!!)
////                    }
////                    1 ->
////                    {
////                        messageArrayList.add(snapshot.getValue(ImageMessage::class.java)!!)
////                    }
////                    else ->
////                    {
////                        //todo err
////                    }
////                }
//                adapt.notifyDataSetChanged()
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
    //}

//    fun initChildEventListener()
//    {
//        dbref.addChildEventListener(object : ChildEventListener
//        {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                if (isFirstTime)
//                {
//                    return
//                }
//                var w = snapshot.getValue(Message::class.java)
//                if (w != null) {
//                    messageArrayList.add(w)
//                    adapt.notifyDataSetChanged()
//                }
//                else
//                {
//                    //todo err (error text message)
//                }
////                when (w?.getType()) {
////                    0 ->
////                    {
////                        messageArrayList.add(snapshot.getValue(TextMessage::class.java)!!)
////                    }
////                    1 ->
////                    {
////                        messageArrayList.add(snapshot.getValue(ImageMessage::class.java)!!)
////                    }
////                    else ->
////                    {
////                        //todo err
////                    }
////                }
//                adapt.notifyDataSetChanged()
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }


//    fun sendMessage(view: View)
//    {
//        //todo: send the text message
//
//        val msg = Message(
//            textMessage.text.toString(),
//            null,
//            getUserid(),
//            getUserName(),
//            Time(1,1)
//            )
//        //adapter.sendMessage(msg)
//        //var helper =  ChatOpenHelper(this) //todo: move to global
//        //dbref = FirebaseDatabase.getInstance().getReference("Messages")
//        dbref.child(numOfMessages.toString()).setValue(msg)
//        textMessage.setText("")
//    }
//
//    fun sendImageMessage(image: Bitmap)
//    {
//        val baos = ByteArrayOutputStream()
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val data = baos.toByteArray()
//        var txt :String? = null
//        if(textMessage.text.toString() != "")
//        {
//            txt = textMessage.text.toString()
//        }
//        val msg = Message(
//            txt,
//            Base64.encodeToString(data, Base64.DEFAULT),
//            getUserid(),
//            getUserName(),
//            Time(1,1)
//        )
//        //adapter.sendMessage(msg)
//        //var helper =  ChatOpenHelper(this) //todo: move to global
//        //dbref = FirebaseDatabase.getInstance().getReference("Messages")
//        dbref.child(numOfMessages.toString()).setValue(msg)
//        textMessage.setText("")
//    }
//
//
//
////    fun getPhotoUrl(): String? {
////        //todo: make it work (can upload the image and use current method)
////        //val user = auth.currentUser
////        //user?.photoUrl
////        //sendButton.setImageURI(user?.photoUrl)
////        //var bitmap =
////        var bitmap = getResources().getDrawable(R.drawable.haltura_icon2).toBitmap()
////        val baos = ByteArrayOutputStream()
////        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
////        val data = baos.toByteArray()
////        return Base64.encodeToString(data, Base64.DEFAULT)
////
////        //val user = auth.currentUser
//////        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, user?.photoUrl)
//////        val baos = ByteArrayOutputStream()
//////        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//////        val data = baos.toByteArray()
//////        return Base64.encodeToString(data, Base64.DEFAULT)
////        //todo: take image from user (not google login)
////    }
//
//
//
//    private fun onClickMessage(message: Message) {
//
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
//    fun uploadImage(view: View)
//    {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
//    }
//    fun takeCameraImage(view: View)
//    {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, REQ_CAMERA)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) {
//            if (data != null) {
//                sendImageMessage(data.extras!!["data"] as Bitmap)
//            }
//            //todo: toast err
//        }
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
//            //todo:check if it is work
//            if (data != null) {
//                val uri = data.getData();
//                val imageBitMap =MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
//                sendImageMessage(imageBitMap)
//            }
//            //todo: toast err
//        }
//    }
//
//    companion object {
//        const val TAG = "ChatActivity"
//        const val MESSAGES_CHILD = "messages"
//        const val ANONYMOUS = "anonymous"
//        private val REQ_CAMERA = 1
//        private  val PICK_IMAGE = 2
//        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
//    }
//}
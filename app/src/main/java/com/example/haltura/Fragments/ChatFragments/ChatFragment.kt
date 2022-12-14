package com.example.haltura.Fragments.ChatFragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.example.haltura.Adapters.ChatAdapter2
import com.example.haltura.R
import com.example.haltura.Sql.Items.ChatSerializable
import com.example.haltura.Sql.Items.MessageSerializable
import com.example.haltura.Utils.ChatData
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.ViewModels.ChatViewModel
import com.example.haltura.databinding.FragmentChatBinding
import com.kizitonwose.calendarview.model.DayOwner
import java.io.ByteArrayOutputStream

class ChatFragment : Fragment() {

    private val _viewModel: ChatViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _messageRecycle: RecyclerView
    private lateinit var _sendButton: ImageView
    private lateinit var _cameraButton: ImageView
    private lateinit var _galleryButton: ImageView
    private lateinit var _topBar: LinearLayout
    private lateinit var _chatImage: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var _chatName: TextView
    private lateinit var _members: TextView
    private lateinit var _textMessage: EditText
    private lateinit var _manager: LinearLayoutManager
    private lateinit var _chatAdapter: ChatAdapter2
    private lateinit var _chat: ChatSerializable

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        _fragmentView = binding.root

        setChat()
        initViews()
        setValues()
        initViewModelData()
        initObservers()
        initButtons()
        initTextListener()
        initRecyclersAndAdapters()
        initTopBar()
        startLive()
        initBackPressed()

        return _fragmentView
    }

    private fun initBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            this.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
    }

    private fun initTopBar() {
        _topBar.setOnClickListener {
            showChatInfo()
        }
    }

    private fun showChatInfo() {
        if (_chat.adminID != null) {
            switchFragment(ShowChatInfoDialog(_chat.id!!), Const.group_info)
        } else {
            var userId = _chat.members[0]
            if (userId == UserData.currentUser!!.userId) {
                userId = _chat.members[1]
            }
            switchFragment(ShowProfileInfo(userId), Const.profile_info)
        }
    }

    private fun switchFragment(fragment: Fragment, fragmentId: String) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.chat_fragment, fragment, fragmentId)
            transaction.addToBackStack(null)
            transaction.setReorderingAllowed(true)
            transaction.commit()
        }
    }

    private fun initTextListener() {
        _textMessage.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().trim { it <= ' ' }.length == 0) {
                    _sendButton.setEnabled(false)
                    _sendButton.setImageDrawable(getResources().getDrawable(R.drawable.outline_send_gray_24))
                } else {
                    _sendButton.setEnabled(true)
                    _sendButton.setImageDrawable(getResources().getDrawable(R.drawable.outline_send_24));
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun startLive() {
        activity?.let { _viewModel.startLive(_chat.id!!, it) }
    }

    private fun setValues() {
        if (_chat?.chatName == null) {
            if (_chat.members[0] == UserData.currentUser!!.userId) {
                _chatName.setText(_chat!!.mapUsernames[_chat.members[1]])
            } else {
                _chatName.setText(_chat!!.mapUsernames[_chat.members[0]])
            }
        } else {
            _chatName.setText(_chat?.chatName)
        }
        var members = ""
        _chat.members.sorted().forEach {
            members += _chat.mapUsernames[it] + ", "
        }
        if (members.length > 2) {
            members = members.substring(0, members.length - 2)
        }
        if (members.length > 30) {
            members = members.substring(0, 30) + "..."
        }
        _members.setText(members)

        if (_chat?.chatImage != null) {
            var bm = Base64.decode(_chat?.chatImage, Base64.DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            _chatImage.setImageBitmap(data)
        } else {
        }
    }

    private fun initButtons() {
        _cameraButton.setOnClickListener {
            takeCameraImage()
        }

        _galleryButton.setOnClickListener {
            uploadImage()
        }

        _sendButton.setOnClickListener {
            sendMessage()
        }
        _sendButton.setEnabled(false)
    }

    private fun initRecyclersAndAdapters() {
        val chatsList = _viewModel.mutableMessagesList.value!!
        _chatAdapter = ChatAdapter2(
            chatsList,
            _clickOnItemListener = { moveToChat(it) },
            _chat.mapUsernames
        )
        _messageRecycle.adapter = _chatAdapter
    }

    private fun moveToChat(it: MessageSerializable) {
    }

    private fun initObservers() {
        _viewModel.mutableMessagesList.observe(
            viewLifecycleOwner,
            Observer { messagesList ->
                messagesList?.let {
                    updateRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateRecyclersAndAdapters() {
        _chatAdapter.setData(_viewModel.mutableMessagesList.value!!)
        _chatAdapter.notifyDataSetChanged()
        _messageRecycle.smoothScrollToPosition(_chatAdapter.getItemCount());
    }

    private fun initViewModelData() {
        _viewModel.getAllMessages(_chat.id!!)
    }

    private fun setChat() {

        _chat = ChatData.currentChat!!
//        ChatData.currentChat = null

    }

    private fun initViews() {
        _topBar = _binding!!.topBar// findViewById(R.id.topBar)
        _chatImage = _binding!!.imageChat
        _chatName = _binding!!.nameChat
        _members = _binding!!.members
        _cameraButton = _binding!!.camera
        _galleryButton = _binding!!.gallery
        _sendButton = _binding!!.sendButton
        _textMessage = _binding!!.messageEditText
        _messageRecycle = _binding!!.messageRecyclerView
        _manager = LinearLayoutManager(context)
        _manager.stackFromEnd = true
        _messageRecycle.layoutManager = _manager
        _messageRecycle.addItemDecoration(VerticalSpaceItemDecoration(10))
    }

    fun sendMessage(image: Bitmap? = null)//view: View)
    {
        var imageString: String? = null
        if (image != null) {
            imageString = convertImageToString(image)
        }
        var txt = _textMessage.text.toString()

        if (txt.trim { it <= ' ' }.length == 0) {
            txt == null
        }

        if (txt != null || image != null) {
            _viewModel.SendMessage(
                MessageSerializable(
                    UserData.currentUser?.userId!!,
                    _textMessage.text.toString(),
                    imageString
                )
            )
        }

        _textMessage.setText("")
    }

    private fun convertImageToString(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        return Base64.encodeToString(data, Base64.DEFAULT)
    }

    fun uploadImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    fun takeCameraImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAMERA && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                sendMessage(data.extras!!["data"] as Bitmap)
            }
        }
        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                val uri = data.getData();
                val imageBitMap =
                    MediaStore.Images.Media.getBitmap(activity!!.getContentResolver(), uri)
                sendMessage(imageBitMap)
            }
        }
    }

    companion object {
        const val TAG = "ChatActivity"
        private val REQ_CAMERA = 1
        private val PICK_IMAGE = 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
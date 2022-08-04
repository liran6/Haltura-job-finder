package com.example.haltura.Fragments.ChatFragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.ViewModels.ShowChatInfoDialogViewModel
import com.example.haltura.databinding.ShowChatInfoDialogBinding
import androidx.lifecycle.Observer
import com.example.haltura.Adapters.ProfileAdapter
import com.example.haltura.Models.ChatSerializable
import com.example.haltura.Models.InfoChatSerializable
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.HorizontalSpaceItemDecoration
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.ViewModels.ShowProfileInfoViewModel
import com.example.haltura.activities.AddWorkActivity
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import java.io.ByteArrayOutputStream

class ShowChatInfoDialog : Fragment {

    private lateinit var _chatId: String
    private lateinit var _chatInfo: InfoChatSerializable
    private val _viewModel: ShowChatInfoDialogViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _image: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var _nameOfChat: TextView
    private lateinit var _membersRecycle: RecyclerView
    private lateinit var _membersAdapter: ProfileAdapter

    var nameChanged = false
    var imageChanged = false

    private var _binding: ShowChatInfoDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    constructor(chatId: String):super()
    {
        this._chatId = chatId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShowChatInfoDialogBinding.inflate(inflater, container, false)
        //get chat id
        initBinding()
        initViews()
        initButtons()
        setViews()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun initButtons() {
        binding.updateChat.setOnClickListener {
            _viewModel.updateChat(_chatId,_nameOfChat.text.toString(),
                convertImageToString(_image.drawable.toBitmap()))
        }
        binding.updateChat.isEnabled = false
    }

    private fun setViews() {
    }

    private fun initBinding() {
        _fragmentView = binding.root
    }

    private fun initViews() {
        _image = binding.chatImage
        _nameOfChat = binding.chatName
    }

    private fun initViewModelData() {
        _viewModel.getChatInfo(_chatId)
    }

    private fun initObservers() {
        // members
        _viewModel.mutableMembersList.observe(
            viewLifecycleOwner,
            Observer { membersList ->
                membersList?.let {
                    updateRecyclersAndAdapters()
                }
            }
        )
            _viewModel.mutableChatInfo.observe(
                viewLifecycleOwner,
            Observer { ChatInfo ->
                ChatInfo?.let {
                    if (it != null) {
                        _chatInfo = it
                        setAdminFunction()
                        putValues()
                    }
                }
            }
        )
    }

    private fun setAdminFunction() {
        if (_chatInfo.adminID != UserData.currentUser!!.userId)
        {
            removeEdit()
        }
        else
        {
            setEditClick()
        }
    }

    private fun putValues() {
        _nameOfChat.setText(_chatInfo.chatName)
        if (_chatInfo.chatImage != null)
        {
            //image
            var bm = Base64.decode(_chatInfo.chatImage, Base64.DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            _image.setImageBitmap(data)
        }
    }

    private fun setEditClick() {
        _binding!!.chatName.setOnClickListener {
            editChatName()
        }
        _binding!!.chatImage.setOnClickListener {
            editChatImage()
        }
    }

    private fun editChatName() {
        val customView: View =
            layoutInflater.inflate(R.layout.change_chat_name_popup, null)
        val popup = PopupWindow(
            customView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val cancel = customView.findViewById<TextView>(R.id.cancel)
        val update = customView.findViewById<TextView>(R.id.update)
        val name = customView.findViewById<EditText>(R.id.name)
        var oldName = _chatInfo.chatName
        name.setText(oldName)

        cancel.setOnClickListener {
            popup.dismiss()
            removeBackground(true)
        }

        update.setOnClickListener {
            val newName = name.text.toString()
            if (newName != oldName)
            {
                setNewName(newName)
            }
            popup.dismiss()
            removeBackground(true)
        }

        removeBackground(false)
        popup.isFocusable = true
        popup.update()
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }

    private fun setNewName(newName: String) {
        _nameOfChat.setText(newName)
        nameChanged = true
        enableChange()
    }

    private fun enableChange() {
            binding.updateChat.setTextColor(Color.BLUE)
            binding.updateChat.isEnabled = true
    }

    private fun convertImageToString(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        return  Base64.encodeToString(data, Base64.DEFAULT)
    }

    private fun editChatImage() {
        val imagePopup: View = layoutInflater.inflate(R.layout.camera_or_gallery_popup, null)

        val popup = PopupWindow(
            imagePopup,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val camera = imagePopup.findViewById(R.id.camera) as ImageView
        val gallery = imagePopup.findViewById(R.id.gallery) as ImageView


        camera.setOnClickListener {
            setCameraImage()
            popup.dismiss()
            removeBackground(true)
        }

        gallery.setOnClickListener {
            setGalleryImage()
            popup.dismiss()
            removeBackground(true)
        }
        removeBackground(false)
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0) //popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }

    private fun setCameraImage()
    {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }

    private fun setGalleryImage()
    {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE
        )
    }



    private fun removeBackground(show: Boolean) {
        if (show) {
            binding.chatInfoDialogFragment.visibility = View.VISIBLE

        } else {
            binding.chatInfoDialogFragment.visibility = View.GONE
        }
    }

    private fun removeEdit() {
        _binding!!.chatInfoDialogFragment.removeView(_binding!!.updateChat)
    }

    private fun updateRecyclersAndAdapters() {
        _membersAdapter.setData(_viewModel.mutableMembersList.value!!)
        _membersAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _membersRecycle = binding.membersRecyclerview
        val workList = _viewModel.mutableMembersList.value!!
        val layoutManager = LinearLayoutManager(getContext(),
            LinearLayoutManager.VERTICAL,false)
        _membersRecycle.addItemDecoration(VerticalSpaceItemDecoration(20))
        _membersRecycle.layoutManager = layoutManager
        _membersAdapter = ProfileAdapter(
            workList,
            _clickOnItemListener = { showProfile(it) },
            _clickOnLongItemListener = { removeUser(it) },
            true
        )
        _membersRecycle.adapter = _membersAdapter
    }

    private fun showProfile(profile: ProfileSerializable) {
        switchFragment(ShowProfileInfo(profile),Const.profile_info)
    }

    private fun removeUser(profile: ProfileSerializable) {
        if (profile.userId == UserData.currentUser!!.userId){return}
        val removeUserView: View = layoutInflater.inflate(R.layout.remove_from_chat_popup, null)
        val popup = PopupWindow(
            removeUserView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val cancel = removeUserView.findViewById(R.id.cancel) as TextView
        val remove =
            removeUserView.findViewById(R.id.remove) as TextView
        val workInfo = removeUserView.findViewById(R.id.user_info)as TextView
        workInfo.text = "Are you sure you want to remove "+ profile.username +"?"

        cancel.setOnClickListener {
            popup.dismiss()
            removeBackground(true)
        }

        remove.setOnClickListener {
            _viewModel.removeUser(_chatId, profile.userId!!)
            popup.dismiss()
            removeBackground(true)
        }
        removeBackground(false)
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)

    }

    private fun switchFragment(fragment: Fragment,fragmentId: String) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.chat_info_dialog_fragment, fragment, Const.profile_info)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAMERA && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                var bm = data.extras!!["data"] as Bitmap?
                bm.toString()
                _image.setImageBitmap(bm)
                enableChange()
            }
        }
        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                val uri = data.getData();
                val bm = MediaStore.Images.Media.getBitmap(activity!!.getContentResolver(), uri)
                _image.setImageBitmap(bm)
                enableChange()
            }
        }
    }

    companion object
    {
        private val REQ_CAMERA = 1
        private  val PICK_IMAGE = 2
    }
}
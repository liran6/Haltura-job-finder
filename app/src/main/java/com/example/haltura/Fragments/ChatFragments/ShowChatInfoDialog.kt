package com.example.haltura.Fragments.ChatFragments

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.ViewModels.ShowChatInfoDialogViewModel
import com.example.haltura.databinding.ShowChatInfoDialogBinding
import androidx.lifecycle.Observer
import com.example.haltura.Adapters.ProfileAdapter
import com.example.haltura.Models.InfoChatSerializable
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.HorizontalSpaceItemDecoration
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.ViewModels.ShowProfileInfoViewModel

class ShowChatInfoDialog : Fragment {

    private lateinit var _chatId: String

    private val _viewModel: ShowChatInfoDialogViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _image: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var _nameOfChat: TextView
    //RecyclerView and Adapter
    private lateinit var _membersRecycle: RecyclerView
    private lateinit var _membersAdapter: ProfileAdapter

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
        setViews()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun setViews() {
        //_nameOfChat.setText(_chatInfo.chatName)
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
                        putValues(it)
                    }
                }
            }
        )
    }

    private fun putValues(chatInfo: InfoChatSerializable) {
        _nameOfChat.setText(chatInfo.chatName)
        if (chatInfo.chatImage != null)
        {
            //image
            var bm = Base64.decode(chatInfo.chatImage, Base64.DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            _image.setImageBitmap(data)
        }
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
            _clickOnItemListener = { showProfile(it) }
        )
        _membersRecycle.adapter = _membersAdapter
    }

    private fun showProfile(profile: ProfileSerializable) {
        switchFragment(ShowProfileInfo(profile),Const.profile_info)
    }


    private fun switchFragment(fragment: Fragment,fragmentId: String) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.chat_info_dialog_fragment, fragment, Const.profile_info)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        homeActivityToolbar.makeVisible()
//        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.calendar_toolbar_color))
//        requireActivity().window.statusBarColor =
//            requireContext().getColorCompat(R.color.calendar_statusbar_color)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.colorPrimary))
//        requireActivity().window.statusBarColor = requireContext().getColorCompat(R.color.colorPrimaryDark)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
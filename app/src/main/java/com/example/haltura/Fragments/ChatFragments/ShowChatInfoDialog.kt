package com.example.haltura.Fragments.ChatFragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.example.haltura.Sql.Items.WorkSerializable

class ShowChatInfoDialog : Fragment {

    private lateinit var _chatId: String

    private val _viewModel: ShowChatInfoDialogViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _image: ImageView
    private lateinit var _nameOfChat: TextView
    //RecyclerView and Adapter
    private lateinit var _membersRecycle: RecyclerView
    private lateinit var _membersAdapter: ProfileAdapter

    private var _binding: ShowChatInfoDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    constructor(chatId: InfoChatSerializable):super()
//    {
//        this._chatId = chatId
//    }

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
    }

    private fun updateRecyclersAndAdapters() {
        _membersAdapter.setData(_viewModel.mutableMembersList.value!!)
        _membersAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _membersRecycle = binding.membersRecyclerview
        val workList = _viewModel.mutableMembersList.value!!
        _membersRecycle.layoutManager = LinearLayoutManager(context)
        _membersAdapter = ProfileAdapter(
            workList,
            _clickOnItemListener = { showProfile(it) }
        )
        _membersRecycle.adapter = _membersAdapter
    }

    private fun showProfile(profile: ProfileSerializable) {

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
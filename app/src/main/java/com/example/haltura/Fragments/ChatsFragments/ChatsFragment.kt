package com.example.haltura.Fragments.ChatsFragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.ViewModels.ChatsViewModel
import androidx.lifecycle.Observer
import com.example.haltura.Adapters.ChatsAdapter
import com.example.haltura.Fragments.BackButton
import com.example.haltura.Fragments.BaseFragment
import com.example.haltura.Fragments.ProfileSettingsButton
import com.example.haltura.R
import com.example.haltura.Sql.Items.ChatSerializable
import com.example.haltura.Utils.ChatData
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.WorkData
import com.example.haltura.activities.AddWorkActivity
import com.example.haltura.activities.ChatActivity
import com.example.haltura.databinding.FragmentChatsBinding

class ChatsFragment : BaseFragment(R.layout.fragment_chats), BackButton, ProfileSettingsButton {
    override val titleRes: String = "Chats :"//+ UserData.currentUser?.username

    private val _viewModel: ChatsViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _chatsRecycle: RecyclerView
    private lateinit var _chatsAdapter: ChatsAdapter
    private lateinit var _searchText : EditText
    private lateinit var _searchButton : ImageButton
    private var _binding: FragmentChatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        _fragmentView = binding.root

        initViews()
        initButtons()
        initTextListener()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun initTextListener() {
        _searchText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                filter()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
    }

    private fun initButtons() {
        _searchButton.setOnClickListener {
            filter() //todo: make the search btn to open search text instead
        }
    }

    private fun filter() {
        _viewModel.filter(_searchText.text.toString())//
    }

    private fun initViews() {
        _chatsRecycle = binding.chatsRecyclerView //todo remove? (move to initadapterand re)
        _chatsRecycle.addItemDecoration(VerticalSpaceItemDecoration(10))
        _searchText = binding.searchText
        _searchButton = binding.searchButton
    }

    private fun initViewModelData() {
        _viewModel.getAllOfYourChats()
    }

    private fun initObservers() {
        _viewModel.mutableChatsList.observe(
            viewLifecycleOwner,
            Observer { chatsList ->
                chatsList?.let {
                    updateRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateRecyclersAndAdapters() {
        _chatsAdapter.setData(_viewModel.mutableChatsList.value!!)
        _chatsAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _chatsRecycle = binding.chatsRecyclerView
        val chatsList = _viewModel.mutableChatsList.value!!
        _chatsRecycle.layoutManager = LinearLayoutManager(context)
        _chatsAdapter = ChatsAdapter(
            chatsList,
            _clickOnItemListener = { moveToChat(it) })
        _chatsRecycle.adapter = _chatsAdapter
    }

    private fun moveToChat(chat: ChatSerializable) {
        val intent = Intent(activity, ChatActivity::class.java)
        ChatData.currentChat = chat
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        initViewModelData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.haltura.Fragments.ChatsFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.ViewModels.ChatsViewModel
import androidx.lifecycle.Observer
import com.example.haltura.Adapters.ChatsAdapter
import com.example.haltura.Sql.Items.ChatSerializable
import com.example.haltura.databinding.FragmentChatsBinding

class ChatsFragment : Fragment() {

    private val _viewModel: ChatsViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _chatsRecycle: RecyclerView
    private lateinit var _chatsAdapter: ChatsAdapter
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
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun initViews() {
        _chatsRecycle = binding.chatsRecyclerView
        _chatsRecycle.addItemDecoration(VerticalSpaceItemDecoration(10))
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
        //todo: intent to chat activity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
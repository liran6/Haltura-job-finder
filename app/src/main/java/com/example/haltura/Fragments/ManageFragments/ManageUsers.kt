package com.example.haltura.Fragments.ManageFragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.R
import com.example.haltura.ViewModels.HomeViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haltura.Adapters.WorkAdapter
import com.example.haltura.Fragments.*
//import com.example.haltura.Dialogs.WatchWorkDialog
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.HorizontalSpaceItemDecoration
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.activities.LoginActivity
import com.example.haltura.activities.MainActivity2
import com.example.haltura.activities.WorkHistoryActivity
import com.example.haltura.databinding.FragmentHomeBinding
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.haltura.Adapters.ProfileAdapter
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.ViewModels.ManageUsersViewModel
import com.example.haltura.databinding.FragmentManageUsersBinding
import java.text.SimpleDateFormat
import java.util.*


class ManageUsers : Fragment() {


    private val _viewModel: ManageUsersViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    //All
    private lateinit var _usersRecycle: RecyclerView
    private lateinit var _usersAdapter: ProfileAdapter

    private var _binding: FragmentManageUsersBinding? = null
    //EditText
    private lateinit var _searchText : EditText
    //Button
    private lateinit var _searchButton : ImageButton


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageUsersBinding.inflate(inflater, container, false)
        initBinding()
//        initViews()
//        initButtons()
//        initTextListener()
//        initViewModelData()
//        initObservers()
//        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun initButtons() {
        _searchButton.setOnClickListener {
            filter() //todo: make the search btn to open search text instead
        }
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

    private fun filter() {
        _viewModel.filter(_searchText.text.toString())//
    }

    private fun initBinding() {
        _fragmentView = binding.root
    }

    private fun initViews() {
        _searchText = binding.searchText
        _searchButton = binding.searchButton
    }

    private fun initViewModelData() {
        _viewModel.getUsers()
    }

    private fun initObservers() {
        // All Works
        _viewModel.mutableUserList.observe(
            viewLifecycleOwner,
            Observer { userList ->
                userList?.let {
                    updateAllWorksRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateAllWorksRecyclersAndAdapters() {
        _usersAdapter.setData(_viewModel.mutableUserList.value!!)
        _usersAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _usersRecycle = binding.usersList
        val userList = _viewModel.mutableUserList.value!!
        val layoutManager = LinearLayoutManager(getContext(),
            LinearLayoutManager.HORIZONTAL,false)
        _usersRecycle.addItemDecoration(HorizontalSpaceItemDecoration(20))
        _usersRecycle.layoutManager = layoutManager
        _usersAdapter = ProfileAdapter(
            userList,
            _clickOnItemListener = { showWorkDetails(it) },
            _clickOnLongItemListener = { showWorkDetails(it) },
            _enableOnClick = true
        )
        _usersRecycle.adapter = _usersAdapter
    }

    private fun showWorkDetails(profile: ProfileSerializable) {
//        var dialog = WatchWorkDialog(work)
//        activity?.supportFragmentManager?.let {
//            dialog.show(it, "WatchWorkDialog")
//        }
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
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
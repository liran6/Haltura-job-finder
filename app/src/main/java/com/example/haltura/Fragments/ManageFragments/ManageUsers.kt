package com.example.haltura.Fragments.ManageFragments

//import com.example.haltura.Dialogs.WatchWorkDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.ProfileAdapter
import com.example.haltura.Fragments.*
import com.example.haltura.Fragments.ChatFragments.ShowProfileInfo
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.R
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.ViewModels.ManageUsersViewModel
import com.example.haltura.databinding.FragmentManageUsersBinding
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
        initViews()
        initButtons()
        initTextListener()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

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
            LinearLayoutManager.VERTICAL,false)
        _usersRecycle.addItemDecoration(VerticalSpaceItemDecoration(20))
        _usersRecycle.layoutManager = layoutManager
        _usersAdapter = ProfileAdapter(
            userList,
            _clickOnItemListener = { showProfile(it) },
            _clickOnLongItemListener = { removeUser(it) },
            _enableOnClick = true
        )
        _usersRecycle.adapter = _usersAdapter
    }

    private fun showProfile(profile: ProfileSerializable) {
        switchFragment(ShowProfileInfo(profile),Const.profile_info)
    }

    private fun switchFragment(fragment: Fragment,fragmentId: String) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.fragment_manage_users_view, fragment, Const.profile_info)
            transaction.addToBackStack(null)
            transaction.commit()
        }
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
        workInfo.text = "Are you sure you want to remove "+ profile.username +"?" //todo take just the date

        cancel.setOnClickListener {
            popup.dismiss()
            removeBackground(true)
        }

        remove.setOnClickListener {
            _viewModel.deleteUser(profile)
            popup.dismiss()
            removeBackground(true)
        }
        removeBackground(false)
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)

    }

    private fun removeBackground(show: Boolean) {
        if (show) {
            binding.fragmentManageUsersView.visibility = View.VISIBLE

        } else {
            binding.fragmentManageUsersView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.haltura.Fragments.HomeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import com.example.haltura.Fragments.CalendarFragments.BaseFragment
import com.example.haltura.Fragments.CalendarFragments.HasBackButton
import com.example.haltura.Fragments.CalendarFragments.getColorCompat
import com.example.haltura.Fragments.CalendarFragments.makeVisible
//import com.example.haltura.Dialogs.WatchWorkDialog
import com.example.haltura.Fragments.FragmentWithUserObject
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.HorizontalSpaceItemDecoration
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.activities.MainActivity2
import com.example.haltura.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment(R.layout.fragment_work), HasBackButton {

    override val titleRes: String = "Welcome back "+UserData.currentUser?.email
    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2Mjk2NDg1ZDQ0NzBhZGE1YzBmYWJlOGYiLCJpYXQiOjE2NTQ3MTc3MjksImV4cCI6MTY1NTMyMjUyOX0.kINx9at8G7aZkJUWfghCojlYk3DHKqgpt2gZJTHd5s4"
    private val userId: String = "6296485d4470ada5c0fabe8f"


    private val _viewModel: HomeViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _allWorksRecycle: RecyclerView
    private lateinit var _allWorksAdapter: WorkAdapter
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initBinding()
        initViews()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun initBinding() {
        _fragmentView = binding.root
    }

    private fun initViews() {
        _allWorksRecycle = binding.allWorksRecyclerView //_fragmentView.findViewById(R.id.workRecyclerView)
//        _allWorksRecycle.layoutManager = LinearLayoutManager(activity,
//            LinearLayoutManager.HORIZONTAL, false)
//        _allWorksRecycle.setHasFixedSize(true);
//        val layoutManager = LinearLayoutManager(getContext(),
//            LinearLayoutManager.HORIZONTAL,false)
//        _allWorksRecycle.layoutManager = layoutManager
        //_allWorksRecycle.addItemDecoration(VerticalSpaceItemDecoration(20))
    }

    private fun initViewModelData() {
        _viewModel.getAllWorks()
    }

    private fun initObservers() {
        _viewModel.mutableWorkList.observe(
            viewLifecycleOwner,
            Observer { workList ->
                workList?.let {
                    updateRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateRecyclersAndAdapters() {
        _allWorksAdapter.setData(_viewModel.mutableWorkList.value!!)
        _allWorksAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _allWorksRecycle = binding.allWorksRecyclerView //_fragmentView.findViewById(R.id.workRecyclerView)
        val workList = _viewModel.mutableWorkList.value!!
        //_allWorksRecycle.layoutManager = LinearLayoutManager(context)
        //_allWorksRecycle.setHasFixedSize(true);
        val layoutManager = LinearLayoutManager(getContext(),
            LinearLayoutManager.HORIZONTAL,false)
        _allWorksRecycle.addItemDecoration(HorizontalSpaceItemDecoration(20))
        _allWorksRecycle.layoutManager = layoutManager
        _allWorksAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkDetails(it) })
        _allWorksRecycle.adapter = _allWorksAdapter
    }

    private fun showWorkDetails(work: WorkSerializable) {
        var dialog = WatchWorkDialog(work)
        activity?.supportFragmentManager?.let {
            dialog.show(it, "WatchWorkDialog")
        }
        //dialog.show(supportFragmentManager,"WatchWorkDialog")
        //showWorkDetailsPopup(supervisorEmail)
        // todo: in here or view model?
        //todo: do it from the beginning
        //var dialog = WatchWorkDialog(work,this)
        //dialog.show(supportFragmentManager,"WatchWorkDialog")
    }
    override fun onStart() {
        super.onStart()
        homeActivityToolbar.makeVisible()
        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.calendar_toolbar_color))
        requireActivity().window.statusBarColor =
            requireContext().getColorCompat(R.color.calendar_statusbar_color)
    }

    override fun onStop() {
        super.onStop()
        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.colorPrimary))
        requireActivity().window.statusBarColor = requireContext().getColorCompat(R.color.colorPrimaryDark)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

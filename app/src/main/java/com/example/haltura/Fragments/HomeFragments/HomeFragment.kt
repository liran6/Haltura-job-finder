package com.example.haltura.Fragments.HomeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.R
import com.example.haltura.ViewModels.HomeViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haltura.Adapters.WorkAdapter
//import com.example.haltura.Dialogs.WatchWorkDialog
import com.example.haltura.Fragments.FragmentWithUserObject
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2Mjk2NDg1ZDQ0NzBhZGE1YzBmYWJlOGYiLCJpYXQiOjE2NTQ3MTc3MjksImV4cCI6MTY1NTMyMjUyOX0.kINx9at8G7aZkJUWfghCojlYk3DHKqgpt2gZJTHd5s4"
    private val userId: String = "6296485d4470ada5c0fabe8f"


    private val _viewModel: HomeViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _workRecycle: RecyclerView
    private lateinit var _worksAdapter: WorkAdapter
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //val root: View = binding.root
        _fragmentView = binding.root

        initViews()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun initViews() {
        _workRecycle = binding.workRecyclerView //_fragmentView.findViewById(R.id.workRecyclerView)
        _workRecycle.addItemDecoration(VerticalSpaceItemDecoration(20))
    }

    private fun initViewModelData() {
        _viewModel.getAllWorks(UserData.currentUser?.token!!)
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
        _worksAdapter.setData(_viewModel.mutableWorkList.value!!)
        _worksAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _workRecycle = binding.workRecyclerView //_fragmentView.findViewById(R.id.workRecyclerView)
        val workList = _viewModel.mutableWorkList.value!!
        _workRecycle.layoutManager = LinearLayoutManager(context)
        _worksAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkDetails(it) })
        _workRecycle.adapter = _worksAdapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
////        _fragmentView = inflater.inflate(R.layout.fragment_home, container, false)
//        initViews()
//        initViewModelData()
//        initObservers()
//        initRecyclersAndAdapters()
//        return _fragmentView
//    }
//
//    private fun initViews() {
//        //_workRecycle = _fragmentView.findViewById(R.id.workRecyclerView)
//    }
//
//    private fun initViewModelData() {
//        _viewModel.getAllWorks(userObject.token)
//    }
//
//    private fun initObservers() {
//        _viewModel.mutableWorkList.observe(
//            viewLifecycleOwner,
//            Observer { workList ->
//                workList?.let {
//                    updateRecyclersAndAdapters()
//                }
//            }
//        )
//    }
//
//    private fun updateRecyclersAndAdapters() {
//        _worksAdapter.setData(_viewModel.mutableWorkList.value!!)
//        _worksAdapter.notifyDataSetChanged()
//    }
//
//    private fun initRecyclersAndAdapters() {
//        _workRecycle = _fragmentView.findViewById(R.id.workRecyclerView)
//        // initiate list of profiles with recyclers and adapters
//        val supervisorList = _viewModel.mutableWorkList.value!!
//        _workRecycle.layoutManager = LinearLayoutManager(context)
//        _worksAdapter = WorkAdapter(
//            supervisorList,
//            _clickOnItemListener = { showWorkDetails(it) })
//        _workRecycle.adapter = _worksAdapter
//    }
//
//    private fun showWorkDetails(work: WorkSerializable) {
//        //showWorkDetailsPopup(supervisorEmail)
//        // todo: in here or view model?
//        //todo: do it from the beginning
//        //var dialog = WatchWorkDialog(work,this)
//        //dialog.show(supportFragmentManager,"WatchWorkDialog")
//    }
//
//}



//class HomeFragment : FragmentWithUserObject() {
//
//    private val _viewModel: HomeViewModel by activityViewModels()
//    private lateinit var _fragmentView: View
//    private lateinit var _workRecycle: RecyclerView
//    private lateinit var _worksAdapter: WorkAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _fragmentView = inflater.inflate(R.layout.fragment_home, container, false)
//        initViews()
//        initViewModelData()
//        initObservers()
//        initRecyclersAndAdapters()
//        return _fragmentView
//    }
//
//    private fun initViews() {
//        //_workRecycle = _fragmentView.findViewById(R.id.workRecyclerView)
//    }
//
//    private fun initViewModelData() {
//        _viewModel.getAllWorks(userObject.token)
//    }
//
//    private fun initObservers() {
//        _viewModel.mutableWorkList.observe(
//            viewLifecycleOwner,
//            Observer { workList ->
//                workList?.let {
//                    updateRecyclersAndAdapters()
//                }
//            }
//        )
//    }
//
//    private fun updateRecyclersAndAdapters() {
//        _worksAdapter.setData(_viewModel.mutableWorkList.value!!)
//        _worksAdapter.notifyDataSetChanged()
//    }
//
//    private fun initRecyclersAndAdapters() {
//        _workRecycle = _fragmentView.findViewById(R.id.workRecyclerView)
//        // initiate list of profiles with recyclers and adapters
//        val supervisorList = _viewModel.mutableWorkList.value!!
//        _workRecycle.layoutManager = LinearLayoutManager(context)
//        _worksAdapter = WorkAdapter(
//            supervisorList,
//            _clickOnItemListener = { showWorkDetails(it) })
//        _workRecycle.adapter = _worksAdapter
//    }
//
//    private fun showWorkDetails(work: WorkSerializable) {
//        //showWorkDetailsPopup(supervisorEmail)
//        // todo: in here or view model?
//        //todo: do it from the beginning
//        //var dialog = WatchWorkDialog(work,this)
//        //dialog.show(supportFragmentManager,"WatchWorkDialog")
//    }
//
//}
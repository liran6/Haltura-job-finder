package com.example.haltura.Fragments.HomeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.R
import com.example.haltura.ViewModels.HomeViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.haltura.Adapters.WorkAdapter
import com.example.haltura.Dialogs.WatchWorkDialog
import com.example.haltura.Fragments.FragmentWithUserObject
import com.example.haltura.Sql.Items.WorkSerializable

class HomeFragment : FragmentWithUserObject() {

    private val _viewModel: HomeViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _workRecycle: RecyclerView
    private lateinit var _worksAdapter: WorkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _fragmentView = inflater.inflate(R.layout.fragment_home, container, false)
        initViews()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()
        return _fragmentView
    }

    private fun initViews() {
        //_workRecycle = _fragmentView.findViewById(R.id.workRecyclerView)
    }

    private fun initViewModelData() {
        _viewModel.getAllWorks(userObject.token)
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
        _workRecycle = _fragmentView.findViewById(R.id.workRecyclerView)
        // initiate list of profiles with recyclers and adapters
        val supervisorList = _viewModel.mutableWorkList.value!!
        _workRecycle.layoutManager = LinearLayoutManager(context)
        _worksAdapter = WorkAdapter(
            supervisorList,
            _clickOnItemListener = { showWorkDetails(it) })
        _workRecycle.adapter = _worksAdapter
    }

    private fun showWorkDetails(work: WorkSerializable) {
        //showWorkDetailsPopup(supervisorEmail)
        // todo: in here or view model?
        //todo: do it from the beginning
        //var dialog = WatchWorkDialog(work,this)
        //dialog.show(supportFragmentManager,"WatchWorkDialog")
    }

}
package com.example.haltura.Fragments.WorkFragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.ManageWorkAdapter
import androidx.lifecycle.Observer
import com.example.haltura.Adapters.WorkAdapter
import com.example.haltura.Fragments.HomeFragments.WatchWorkDialog
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.HomeViewModel
import com.example.haltura.ViewModels.WorkViewModel
import com.example.haltura.databinding.FragmentHomeBinding
import com.example.haltura.databinding.FragmentWorkBinding

class WorkFragment : Fragment() {
    private val _viewModel: WorkViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _manageWorkRecycle: RecyclerView
    private lateinit var _manageWorksAdapter: ManageWorkAdapter
    private var _binding: FragmentWorkBinding? = null

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
        _binding = FragmentWorkBinding.inflate(inflater, container, false)
        //val root: View = binding.root
        _fragmentView = binding.root

        initViews()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun initViews() {
        _manageWorkRecycle = binding.manageWorkRecyclerView //_fragmentView.findViewById(R.id.workRecyclerView)
    }

    private fun initViewModelData() {
        _viewModel.getAllOfYourWorks()
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
        _manageWorksAdapter.setData(_viewModel.mutableWorkList.value!!)
        _manageWorksAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _manageWorkRecycle = binding.manageWorkRecyclerView //_fragmentView.findViewById(R.id.workRecyclerView)
        val workList = _viewModel.mutableWorkList.value!!
        _manageWorkRecycle.layoutManager = LinearLayoutManager(context)
        _manageWorksAdapter = ManageWorkAdapter(
            workList,
            _clickOnItemListener = { openWorkEditMode(it) },
            _clickDeleteItemListener = { deleteWork(it) }
        )
        _manageWorkRecycle.adapter = _manageWorksAdapter
    }

    private fun openWorkEditMode(work: WorkSerializable) {

    }

    private fun deleteWork(work: WorkSerializable) {
        val removeWorkView: View = layoutInflater.inflate(R.layout.work_remove_popup, null)
        val popup = PopupWindow(
            removeWorkView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val cancel = removeWorkView.findViewById(R.id.cancel) as TextView
        val delete =
            removeWorkView.findViewById(R.id.delete) as TextView
        val workInfo = removeWorkView.findViewById(R.id.work_info)as TextView
        workInfo.text = work.task + " " + work.startTime //todo take just the date

        cancel.setOnClickListener {
            popup.dismiss()
        }

        delete.setOnClickListener {
            _viewModel.deleteWork(work)
            popup.dismiss()
        }
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
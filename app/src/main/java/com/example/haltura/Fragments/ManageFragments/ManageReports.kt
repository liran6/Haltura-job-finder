package com.example.haltura.Fragments.ManageFragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.ManageWorkAdapter
import com.example.haltura.Adapters.ReportAdapter
import com.example.haltura.Models.ReportSerializable
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.Utils.WorkData
import com.example.haltura.ViewModels.ManageReportsViewModel
import com.example.haltura.ViewModels.ManageWorksViewModel
import com.example.haltura.activities.AddWorkActivity
import com.example.haltura.databinding.FragmentManageReportsBinding
import com.example.haltura.databinding.FragmentManageWorksBinding

class ManageReports : Fragment() {

    //override val titleRes: String = "Manage Works"

    private val _viewModel: ManageReportsViewModel by activityViewModels()
    private lateinit var _fragmentView: View

    //All
    private lateinit var _reportRecycle: RecyclerView
    private lateinit var _reportAdapter: ReportAdapter

    private var _binding: FragmentManageReportsBinding? = null

    //EditText
    private lateinit var _searchText: EditText

    //Button
    private lateinit var _searchButton: ImageButton


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageReportsBinding.inflate(inflater, container, false)
        initBinding()
        initViewModelData()
        initObservers()
        initRecyclersAndAdapters()

        return _fragmentView
    }

    private fun initBinding() {
        _fragmentView = binding.root
    }

    private fun initViewModelData() {
        _viewModel.getReports()
    }

    private fun initObservers() {
        // All Works
        _viewModel.mutableReportList.observe(
            viewLifecycleOwner,
            Observer { reportList ->
                reportList?.let {
                    updateAllWorksRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateAllWorksRecyclersAndAdapters() {
        _reportAdapter.setData(_viewModel.mutableReportList.value!!)
        _reportAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _reportRecycle = binding.reportList
        val reportList = _viewModel.mutableReportList.value!!
        val layoutManager = LinearLayoutManager(
            getContext(),
            LinearLayoutManager.VERTICAL, false
        )
        _reportRecycle.addItemDecoration(VerticalSpaceItemDecoration(20))
        _reportRecycle.layoutManager = layoutManager
        _reportAdapter = ReportAdapter(
            reportList,
            _clickOnItemListener = { showReport(it) },
        )
        _reportRecycle.adapter = _reportAdapter
    }

    private fun showReport(report: ReportSerializable) {
        val reportPopup: View = layoutInflater.inflate(R.layout.report_manage_popup, null)

        val popup = PopupWindow(
            reportPopup,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val userId = reportPopup.findViewById(R.id.reporter) as TextView
        val workId = reportPopup.findViewById(R.id.work) as TextView
        val info = reportPopup.findViewById(R.id.info) as TextView
        var markAsDone = reportPopup.findViewById(R.id.done) as TextView
        var cancel = reportPopup.findViewById(R.id.cancel) as TextView

        userId.setText("reporter: " + report.userId)
        workId.setText("work: " + report.workId)
        info.setText("info: " + report.reportText)

        markAsDone.setOnClickListener {
            _viewModel.MarkAsDone(report)
            popup.dismiss()
            removeBackground(true)
        }

        cancel.setOnClickListener {
            popup.dismiss()
            removeBackground(true)
        }
        removeBackground(false)
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0) //popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }

    private fun removeBackground(show: Boolean) {
        if (show) {
            binding.fragmentManageReportView.visibility = View.VISIBLE

        } else {
            binding.fragmentManageReportView.visibility = View.GONE
        }
    }

//    private fun deleteWork(work: WorkSerializable) {
//        val removeWorkView: View = layoutInflater.inflate(R.layout.work_remove_popup, null)
//        val popup = PopupWindow(
//            removeWorkView,
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        popup.elevation = 3.0f
//
//        val cancel = removeWorkView.findViewById(R.id.cancel) as TextView
//        val delete =
//            removeWorkView.findViewById(R.id.delete) as TextView
//        val workInfo = removeWorkView.findViewById(R.id.work_info)as TextView
//        workInfo.text = work.task + " " + work.startTime //todo take just the date
//
//        cancel.setOnClickListener {
//            popup.dismiss()
//            removeBackground(true)
//        }
//
//        delete.setOnClickListener {
//            _viewModel.deleteWork(work)
//            popup.dismiss()
//            removeBackground(true)
//        }
//        removeBackground(false)
//        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
//    }
//    private fun removeBackground(show: Boolean) {
//        if (show) {
//            _fragmentView.visibility = View.VISIBLE
//
//        } else {
//            _fragmentView.visibility = View.GONE
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
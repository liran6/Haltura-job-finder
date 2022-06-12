package com.example.haltura.Fragments.WorkFragments

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.ManageWorkAdapter
import com.example.haltura.R
import com.example.haltura.Sql.Items.UserObject
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.Utils.WorkData
import com.example.haltura.ViewModels.WorkViewModel
import com.example.haltura.activities.AddWorkActivity
import com.example.haltura.activities.MainActivity2
import com.example.haltura.databinding.FragmentWorkBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WorkFragment : Fragment() {
    private val _viewModel: WorkViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _manageWorkRecycle: RecyclerView
    private lateinit var _manageWorksAdapter: ManageWorkAdapter
    private lateinit var _addWorkFloatingButton: FloatingActionButton
    private var _binding: FragmentWorkBinding? = null
    private lateinit var _layout: ConstraintLayout

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
        initButtons()

        return _fragmentView
    }

    private fun initButtons() {
        _addWorkFloatingButton.setOnClickListener{
            moveToAddWorkActivity()
        }
    }

    private fun moveToAddWorkActivity() {
        val intent = Intent(activity, AddWorkActivity::class.java)
        WorkData.currentWork = null
        startActivity(intent)
    }

    private fun initViews() {
        _manageWorkRecycle = binding.manageWorkRecyclerView //_fragmentView.findViewById(R.id.workRecyclerView)
        _manageWorkRecycle.addItemDecoration(VerticalSpaceItemDecoration(15))
        _layout = binding.manageWorkLayout
        _addWorkFloatingButton = binding.addWorkFloatingButton
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
        val intent = Intent(activity, AddWorkActivity::class.java)
//        val bundle = Bundle()
//        bundle.putParcelable(Const.WORK_OBJECT, work)
//        intent.putExtras(bundle)
        WorkData.currentWork = work
        startActivity(intent)
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
            removeBackground(true)
        }

        delete.setOnClickListener {
            _viewModel.deleteWork(work)
            popup.dismiss()
            removeBackground(true)
        }
        removeBackground(false)
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }

    private fun removeBackground(show: Boolean) {
        if (show) {
            _layout.visibility = View.VISIBLE

        } else {
            _layout.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.ManageWorkAdapter
import com.example.haltura.Adapters.WorkAdapter
import com.example.haltura.Fragments.BackButton
import com.example.haltura.Fragments.BaseFragment
import com.example.haltura.Fragments.getColorCompat
import com.example.haltura.Fragments.makeVisible
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.HorizontalSpaceItemDecoration
import com.example.haltura.Utils.UserData
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.Utils.WorkData
import com.example.haltura.ViewModels.HomeViewModel
import com.example.haltura.ViewModels.ManageWorksViewModel
import com.example.haltura.activities.AddWorkActivity
import com.example.haltura.databinding.FragmentManageUsersBinding
import com.example.haltura.databinding.FragmentManageWorksBinding

class ManageWorks : Fragment() {

    //override val titleRes: String = "Manage Works"

    private val _viewModel: ManageWorksViewModel by activityViewModels()
    private lateinit var _fragmentView: View

    //All
    private lateinit var _allWorksRecycle: RecyclerView
    private lateinit var _allWorksAdapter: ManageWorkAdapter

    private var _binding: FragmentManageWorksBinding? = null

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
        _binding = FragmentManageWorksBinding.inflate(inflater, container, false)
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
        _viewModel.getWorks()
    }

    private fun initObservers() {
        // All Works
        _viewModel.mutableWorkList.observe(
            viewLifecycleOwner,
            Observer { workList ->
                workList?.let {
                    updateAllWorksRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateAllWorksRecyclersAndAdapters() {
        _allWorksAdapter.setData(_viewModel.mutableWorkList.value!!)
        _allWorksAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        _allWorksRecycle = binding.worksList
        val workList = _viewModel.mutableWorkList.value!!
        val layoutManager = LinearLayoutManager(
            getContext(),
            LinearLayoutManager.VERTICAL, false
        )
        _allWorksRecycle.addItemDecoration(VerticalSpaceItemDecoration(20))
        _allWorksRecycle.layoutManager = layoutManager
//        _allWorksAdapter = WorkAdapter(
//            workList,
//            _clickOnItemListener = { showWorkDetails(it) })
        _allWorksAdapter = ManageWorkAdapter(
            workList,
            _clickOnItemListener = { openWorkEditMode(it) },
            _clickDeleteItemListener = { deleteWork(it) }
        )
        _allWorksRecycle.adapter = _allWorksAdapter
    }

    private fun openWorkEditMode(work: WorkSerializable) {
        val intent = Intent(activity, AddWorkActivity::class.java)
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
            _fragmentView.visibility = View.VISIBLE

        } else {
            _fragmentView.visibility = View.GONE
        }
    }

//    private fun showWorkDetails(work: WorkSerializable) {
////        var dialog = WatchWorkDialog(work)
////        activity?.supportFragmentManager?.let {
////            dialog.show(it, "WatchWorkDialog")
////        }
//    }

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
//        requireActivity().window.statusBarColor =
//            requireContext().getColorCompat(R.color.colorPrimaryDark)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
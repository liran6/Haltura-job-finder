package com.example.haltura.Fragments.ManageFragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.example.haltura.ViewModels.HomeViewModel
import com.example.haltura.ViewModels.ManageWorksViewModel
import com.example.haltura.databinding.FragmentManageUsersBinding
import com.example.haltura.databinding.FragmentManageWorksBinding

class ManageWorks : Fragment() {

    //override val titleRes: String = "Manage Works"

    private val _viewModel: ManageWorksViewModel by activityViewModels()
    private lateinit var _fragmentView: View

    //All
    private lateinit var _allWorksRecycle: RecyclerView
    private lateinit var _allWorksAdapter: WorkAdapter

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
        _allWorksAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkDetails(it) })
        _allWorksRecycle.adapter = _allWorksAdapter
    }

    private fun showWorkDetails(work: WorkSerializable) {
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
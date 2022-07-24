package com.example.haltura.Fragments.HomeFragments

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
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : BaseFragment(R.layout.fragment_work), BackButton {

    override val titleRes: String = "Welcome back "+UserData.currentUser?.username

    private val _viewModel: HomeViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    //All
    private lateinit var _allWorksRecycle: RecyclerView
    private lateinit var _allWorksAdapter: WorkAdapter
    //Close
    private lateinit var _closeWorksRecycle: RecyclerView
    private lateinit var _closeWorksAdapter: WorkAdapter
    //Recommended
    private lateinit var _recommendedWorksRecycle: RecyclerView
    private lateinit var _recommendedWorksAdapter: WorkAdapter
    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        //_allWorksRecycle = binding.allWorksRecyclerView
        _searchText = binding.searchText
        _searchButton = binding.searchButton

        //todo: delete:
        _binding!!.button.setOnClickListener{
            startActivity(Intent(activity, WorkHistoryActivity::class.java))
        }
        _binding!!.locationButton.setOnClickListener{
            showDataRangePicker()
        }
    }

    private fun initViewModelData() {
        _viewModel.getAllWorks()
        _viewModel.getCloseWorks()
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
        // Close Works
        _viewModel.mutableCloseWorksList.observe(
            viewLifecycleOwner,
            Observer { workList ->
                workList?.let {
                    updateCloseWorksRecyclersAndAdapters()
                }
            }
        )
        // Recommended Works
        _viewModel.mutableRecommendedWorksList.observe(
            viewLifecycleOwner,
            Observer { workList ->
                workList?.let {
                    updateRecommendedWorksRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateAllWorksRecyclersAndAdapters() {
        _allWorksAdapter.setData(_viewModel.mutableWorkList.value!!)
        _allWorksAdapter.notifyDataSetChanged()
    }

    private fun updateCloseWorksRecyclersAndAdapters() {
        _closeWorksAdapter.setData(_viewModel.mutableCloseWorksList.value!!)
        _closeWorksAdapter.notifyDataSetChanged()
    }

    private fun updateRecommendedWorksRecyclersAndAdapters() {
        _closeWorksAdapter.setData(_viewModel.mutableRecommendedWorksList.value!!)
        _closeWorksAdapter.notifyDataSetChanged()
    }

    private fun initRecyclersAndAdapters() {
        initCloseWorks()
        initRecommendedWorks()
        initAllWorks()
    }

    private fun initCloseWorks() {
        // Close Works
        _closeWorksRecycle = binding.closeWorksRecyclerView
        val workList = _viewModel.mutableCloseWorksList.value!!
        val layoutManager = LinearLayoutManager(getContext(),
            LinearLayoutManager.HORIZONTAL,false)
        _closeWorksRecycle.addItemDecoration(HorizontalSpaceItemDecoration(20))
        _closeWorksRecycle.layoutManager = layoutManager
        _closeWorksAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkDetails(it) })
        _closeWorksRecycle.adapter = _closeWorksAdapter
    }

    private fun initRecommendedWorks() {
        // Recommended Works
        _recommendedWorksRecycle = binding.recommendedWorksRecyclerView
        val workList = _viewModel.mutableRecommendedWorksList.value!!
        val layoutManager = LinearLayoutManager(getContext(),
            LinearLayoutManager.HORIZONTAL,false)
        _recommendedWorksRecycle.addItemDecoration(HorizontalSpaceItemDecoration(20))
        _recommendedWorksRecycle.layoutManager = layoutManager
        _recommendedWorksAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkDetails(it) })
        _recommendedWorksRecycle.adapter = _recommendedWorksAdapter
    }

    private fun initAllWorks() {
        // All Works
        _allWorksRecycle = binding.allWorksRecyclerView
        val workList = _viewModel.mutableWorkList.value!!
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
    }
    override fun onStart() {
        super.onStart()
        homeActivityToolbar.makeVisible()
        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.calendar_toolbar_color))
        requireActivity().window.statusBarColor =
            requireContext().getColorCompat(R.color.calendar_statusbar_color)
    }

    //

    private fun showDataRangePicker() {

        val dateRangePicker =
            MaterialDatePicker
                .Builder.dateRangePicker()
                .setTitleText("Select Date")
                .build()


        //TODO SET PREV DATE LIKE CREATE WORK TIME AND DATE

        dateRangePicker.show(
            activity!!.supportFragmentManager,
            "date_range_picker"
        )

        dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->
            val startDateLong = dateSelected.first
            val endDateLong = dateSelected.second
            val startDateString = convertLongToTime(startDateLong)
            val endDateString = convertLongToTime(endDateLong)

//            if (startDate != null && endDate != null) {
//                binding.tvRangeDate.text =
//                    "Reserved\nStartDate: ${convertLongToTime(startDate)}\n" +
//                            "EndDate: ${convertLongToTime(endDate)}"
//            }
        }
        //todo: notify change

    }


    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault())
        return format.format(date)
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("dd.MM.yyyy")
        return df.parse(date).time
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

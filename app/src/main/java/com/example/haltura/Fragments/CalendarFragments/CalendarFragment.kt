package com.example.haltura.Fragments.CalendarFragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.MyCalendarAdapter
import com.example.haltura.AppNotifications
import com.example.haltura.Fragments.*
import com.example.haltura.Fragments.getColorCompat
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.Utils.WorkData
import com.example.haltura.ViewModels.CalendarViewModel
import com.example.haltura.activities.AddWorkActivity
import com.example.haltura.databinding.*
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
}

class CalendarFragment : BaseFragment(R.layout.fragment_calendar), BackButton {

    private val _viewModel: CalendarViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _worksCreatedRecycler: RecyclerView //worksCreatedRecyclerView
    private lateinit var _worksRegisteredRecycler: RecyclerView//worksRegisteredRecyclerView
    private lateinit var _createdWorkCalendarAdapter: MyCalendarAdapter
    private lateinit var _registeredWorkCalendarAdapter: MyCalendarAdapter
    private lateinit var _layout: LinearLayout

    //private var _binding: FragmentWorkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!


    override val titleRes: String = Const.Calendar
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()
    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var createdWorksMap: MutableMap<LocalDate, MutableList<WorkSerializable>>
    private lateinit var registeredWorksMap: MutableMap<LocalDate, MutableList<WorkSerializable>>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true) // todo : in here?
        binding = FragmentCalendarBinding.bind(view)

        initViews()
        initObservers()
        initViewModelData()
        initRecyclersAndAdapters()
        initCalendarView(savedInstanceState)
        initBackPressed()
//        events = _viewModel.mutableWorksByDateMap.value!!
//        if (savedInstanceState == null) {
//            binding.calendarView.post {
//                // Show today's events initially.
//                selectDate(today)
//            }
//        }

    }

    private fun initBackPressed() {
        //todo manage back press
        activity?.onBackPressedDispatcher?.addCallback(
            this.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (selectedDate!!.monthValue != today.monthValue) {
                        binding.calendarView.smoothScrollToDate(today, DayOwner.THIS_MONTH)
                    } else if (selectedDate != today) {
                        //selectDate(today)
                        binding.calendarView.post {
                            // Show today's events initially.
                            selectDate(today)
                        }
                    } else {
                        isEnabled = false
                        activity?.onBackPressed()
                    }
                }
            })
    }

    private fun initCalendarView(savedInstanceState: Bundle?) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }

        val currentMonth = YearMonth.now() //todo: global?
        val daysOfWeek = daysOfWeekFromLocale()//todo: global?
        binding.calendarView.apply {
            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }
        if (savedInstanceState == null) {
            binding.calendarView.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        binding.calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.dayText
                val dotView = container.binding.dotView
                textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    dotView.makeInVisible()
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.calendar_white)
                            textView.setBackgroundResource(R.drawable.calendar_today_date)
                            dotView.makeInVisible()
                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.calendar_blue)
                            textView.setBackgroundResource(R.drawable.calendar_selected_date)
                            dotView.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.calendar_black)
                            textView.background = null
                            if (createdWorksMap.get(day.date) != null|| registeredWorksMap.get(day.date) != null) {
                                dotView.makeVisible()
                            }
                            //dotView.isVisible = workList.orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }
            }

        }

        binding.calendarView.monthScrollListener = {
            homeActivityToolbar.title = if (it.year == today.year) {
                titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }
            if (it.month == today.monthValue) {
                selectDate(today)
            } else {
                // Select the first day of the month when
                // we scroll to a new month.
                selectDate(it.yearMonth.atDay(1))
            }
        }

        binding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = month.yearMonth
                        container.legendLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].name.first().toString()
                                tv.setTextColorRes(R.color.calendar_black)
                            }
                    }
                }
            }
    }

    private fun initRecyclersAndAdapters() {
        //worksCreated
        _worksCreatedRecycler = binding.worksCreatedRecyclerView
        //_worksRegisteredRecycler = binding.worksRegisteredRecyclerView
        _worksCreatedRecycler.addItemDecoration(VerticalSpaceItemDecoration(10))
        //val initData = mutableListOf<WorkSerializable>()//val chatsList = _viewModel.mutableChatsList.value!!
        _worksCreatedRecycler.layoutManager = LinearLayoutManager(context)
        _createdWorkCalendarAdapter = MyCalendarAdapter(//todo:switch adapter
            //events, //todo change--------------------------------------------------------------------------------------------------------------------------------------------------------------
            _clickOnItemListener = { openWorkEditMode(it, null) }
        )
        _worksCreatedRecycler.adapter = _createdWorkCalendarAdapter

        //worksRegistered
        _worksRegisteredRecycler = binding.worksRegisteredRecyclerView
        _worksRegisteredRecycler.addItemDecoration(VerticalSpaceItemDecoration(10))
        //val chatsList = _viewModel.mutableChatsList.value!!
        _worksRegisteredRecycler.layoutManager = LinearLayoutManager(context)
        _registeredWorkCalendarAdapter = MyCalendarAdapter(//todo:switch adapter
            //events,//todo change----------------------------------------------------------------------------------------------------------------------------------------------------------------------
            _clickOnItemListener = { openWorkEditMode(it, null)}//TODO- Unsign from activity function(open dialog)
        )
        _worksRegisteredRecycler.adapter = _registeredWorkCalendarAdapter
    }

    private fun initViews() {
//        _worksCreatedRecycler = binding.worksCreatedRecyclerView
//        _worksCreatedRecycler.addItemDecoration(VerticalSpaceItemDecoration(10))
//        _worksRegisteredRecycler = binding.worksRegisteredRecyclerView
//        _worksRegisteredRecycler.addItemDecoration(VerticalSpaceItemDecoration(10))
    }


    private fun initViewModelData() {
        _viewModel.UserCreatedWorksByDate()
        _viewModel.UserRegisterdWorksByDate()
        //events = _viewModel.mutableWorksByDateMap.value!!
    }

    private fun initObservers() {

        _viewModel.mutableMessageToasting.observe(
            viewLifecycleOwner
        ) { message ->
            message.let {
                activity?.let { it1 -> AppNotifications.toastBar(it1, message) }
            }
        }
        _viewModel.mutableCreatedWorksByDateMap.observe(
            viewLifecycleOwner,
            Observer { workMap ->
                workMap?.let {
//                    events = _viewModel.mutableWorksByDateMap.value!! //todo:this is the map
                    //updateRecycleAndAdapterForDate(null)
                    createdWorksMap = workMap
                    _createdWorkCalendarAdapter.updateDataSet(createdWorksMap)

                    binding.calendarView.notifyCalendarChanged()
                    selectDate(today)

//                    initRecyclersAndAdapters()
//                    initCalendarView(savedInstanceState)
//                    binding.calendarView.notifyCalendarChanged()

                }
            }
        )
        _viewModel.mutableRegisteredWorksByDateMap.observe(
            viewLifecycleOwner,
            Observer { workMap ->
                workMap?.let {
//                    events = _viewModel.mutableWorksByDateMap.value!! //todo:this is the map
                    //updateRecycleAndAdapterForDate(null)
                    registeredWorksMap = workMap
                    _registeredWorkCalendarAdapter.updateDataSet(registeredWorksMap)

                    binding.calendarView.notifyCalendarChanged()
                    selectDate(today)

//                    initRecyclersAndAdapters()
//                    initCalendarView(savedInstanceState)
//                    binding.calendarView.notifyCalendarChanged()

                }
            }
        )
    }

    //TODO: place work preview in here!!!
    private fun openWorkEditMode(work: WorkSerializable, date: LocalDate?) {
        val intent = Intent(activity, AddWorkActivity::class.java)
        WorkData.currentWork = work
        startActivity(intent)
        updateRecycleAndAdapterForDate(date!!)
    }

    //todo need to put away from here
//    private fun deleteWork(work: WorkSerializable,date: LocalDate?) {
//        val removeWorkView: View = layoutInflater.inflate(R.layout.work_remove_popup, null)
//        val popup = PopupWindow(
//            removeWorkView,
//            WRAP_CONTENT,
//            WRAP_CONTENT
//        )
//
//        popup.elevation = 3.0f
//
//        val cancel = removeWorkView.findViewById(R.id.cancel) as TextView
//        val delete =
//            removeWorkView.findViewById(R.id.delete) as TextView
//        val workInfo = removeWorkView.findViewById(R.id.work_info) as TextView
//        workInfo.text = work.task + " " + work.startTime //todo take just the date
//
//        cancel.setOnClickListener {
//            popup.dismiss()
//            removeBackground(true)
//        }
//
//        delete.setOnClickListener {
//            _viewModel.deleteWork(work,date)
//            popup.dismiss()
//            removeBackground(true)
//        }
//        removeBackground(false)
//        popup.showAtLocation(view, Gravity.CENTER, 0, 0)
//        updateRecycleAndAdapterForDate(date)
//    }
//
//    //todo need to put away from here
//    private fun removeBackground(show: Boolean) {
//        if (show) {
//            worksCreatedRecyclerView.visibility = View.VISIBLE
//
//        } else {
//            worksCreatedRecyclerView.visibility = View.GONE
//        }
//    }

    fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            //todo here ___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
            oldDate?.let { binding.calendarView.notifyDateChanged(it) }
            updateRecycleAndAdapterForDate(date)
            binding.calendarView.notifyDateChanged(date)
//            binding.calendarView.notifyCalendarChanged()
            //updateRecyclersAndAdapters(null)
        }
        else{
            updateRecycleAndAdapterForDate(date)
            binding.calendarView.notifyDateChanged(date)
        }
//        binding.selectedDateText.text = selectionFormatter.format(date)
    }

    private fun updateRecycleAndAdapterForDate(date: LocalDate) {
//        events = _viewModel.mutableWorksByDateMap.value!! //todo:this is the map
        //if (events.get(date)!=null) {
//            _manageCalendarAdapter = CalendarAdapter(
//                events.get(date)!!,
//                _clickOnItemListener = { openWorkEditMode(it,date) }
//            )
        _createdWorkCalendarAdapter.setData(date)
        _registeredWorkCalendarAdapter.setData(date)
        binding.selectedDateText.text = selectionFormatter.format(date)
        //_manageCalendarAdapter.notifyDataSetChanged()
        //_worksCreatedRecycler.adapter = _manageCalendarAdapter
        //binding.calendarView.scrollToMonth(date!!.yearMonth)
//        binding.calendarView.notifyCalendarChanged()
//        binding.calendarView.notifyMonthChanged(date.yearMonth)
//        binding.calendarView.notifyDateChanged(date)



    }

//    private fun updateRecyclersAndAdapters(date: LocalDate?) {
//        _manageCalendarAdapter.setData(date!!)
//        _manageCalendarAdapter.notifyDataSetChanged()
//        binding.calendarView.notifyCalendarChanged()
//        if (date != null) {
//            binding.selectedDateText.text = selectionFormatter.format(date)
//        }
//    }


    override fun onStart() {
        super.onStart()
        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.calendar_toolbar_color))
        requireActivity().window.statusBarColor =
            requireContext().getColorCompat(R.color.calendar_statusbar_color)
    }

    override fun onStop() {
        super.onStop()
        homeActivityToolbar.setBackgroundColor(requireContext().getColorCompat(R.color.colorPrimary))
        requireActivity().window.statusBarColor =
            requireContext().getColorCompat(R.color.colorPrimaryDark)
    }
}

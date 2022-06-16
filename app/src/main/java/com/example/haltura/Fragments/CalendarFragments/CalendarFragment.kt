package com.example.haltura.Fragments.CalendarFragments

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.ManageWorkAdapter
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.Const
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
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


data class Event(val id: String, val text: String, val date: LocalDate)

//class Example3EventsAdapter(val onClick: (Event) -> Unit) :
//    RecyclerView.Adapter<Example3EventsAdapter.Example3EventsViewHolder>() {
//
//    val events = mutableListOf<Event>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Example3EventsViewHolder {
//        return Example3EventsViewHolder(
//            Example3EventItemViewBinding.inflate(parent.context.layoutInflater, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(viewHolder: Example3EventsViewHolder, position: Int) {
//        viewHolder.bind(events[position])
//    }
//
//    override fun getItemCount(): Int = events.size
//
//    inner class Example3EventsViewHolder(private val binding: Example3EventItemViewBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        init {
//            itemView.setOnClickListener {
//                onClick(events[bindingAdapterPosition])
//            }
//        }
//
//        fun bind(event: Event) {
//            binding.itemEventText.text = event.text
//        }
//    }
//}


//class DayViewContainer(view: View) : ViewContainer(view) {
//    lateinit var day: CalendarDay // Will be set when this container is bound.
//    private var selectedDate: LocalDate? = null
//    lateinit var cal:CalendarFragment
//    val binding = Example3CalendarDayBinding.bind(view)
//
//    init {
//        view.setOnClickListener {
//            if (day.owner == DayOwner.THIS_MONTH) {
//                cal.selectDate(day.date)
//            }
//        }
//    }
//    object DayViewContainer {
//        override fun create(view: View) = DayViewContainer(view)
//        override fun bind(container: DayViewContainer, day: CalendarDay) {
//            container.day = day
//            val textView = container.binding.exThreeDayText
//            val dotView = container.binding.exThreeDotView
//
//            textView.text = day.date.dayOfMonth.toString()
//
//            if (day.owner == DayOwner.THIS_MONTH) {
//                textView.makeVisible()
//                when (day.date) {
//                    today -> {
//                        textView.setTextColorRes(R.color.example_3_white)
//                        textView.setBackgroundResource(R.drawable.example_3_today_bg)
//                        dotView.makeInVisible()
//                    }
//                    selectedDate -> {
//                        textView.setTextColorRes(R.color.example_3_blue)
//                        textView.setBackgroundResource(R.drawable.example_3_selected_bg)
//                        dotView.makeInVisible()
//                    }
//                    else -> {
//                        textView.setTextColorRes(R.color.example_3_black)
//                        textView.background = null
//                        dotView.isVisible = workList.orEmpty().isNotEmpty()
//                    }
//                }
//            } else {
//                textView.makeInVisible()
//                dotView.makeInVisible()
//            }
//        }
//    }
////    private fun selectDate(date: LocalDate) {
////        if (selectedDate != date) {
////            val oldDate = selectedDate
////            selectedDate = date
////            oldDate?.let { binding.exThreeCalendar.notifyDateChanged(it) }
////            binding.exThreeCalendar.notifyDateChanged(date)
////            //updateAdapterForDate(date)
////            updateRecyclersAndAdapters()
////        }
////    }
//}
class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
}
class CalendarFragment : BaseFragment(R.layout.fragment_calendar), HasBackButton {
    //change to get access to work

//    private val eventsAdapter = Example3EventsAdapter {
//        AlertDialog.Builder(requireContext())
//            .setMessage(R.string.example_3_dialog_delete_confirmation)
//            .setPositiveButton(R.string.delete) { _, _ ->
//                deleteEvent(it)
//            }
//            .setNegativeButton(R.string.close, null)
//            .show()
//    }

    //    private val inputDialog by lazy {
//        val editText = AppCompatEditText(requireContext())
//
//        val layout = FrameLayout(requireContext()).apply {
//            // Setting the padding on the EditText only pads the input area
//            // not the entire EditText so we wrap it in a FrameLayout.
//            val padding = dpToPx(20, requireContext())
//            setPadding(padding, padding, padding, padding)
//            addView(editText, FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
//        }
//        AlertDialog.Builder(requireContext())
//            .setTitle(getString(R.string.example_3_input_dialog_title))
//            .setView(layout)
//            .setPositiveButton(R.string.save) { _, _ ->
//                saveEvent(editText.text.toString())
//                // Prepare EditText for reuse.
//                editText.setText("")
//            }
//            .setNegativeButton(R.string.close, null)
//            .create()
//            .apply {
//                setOnShowListener {
//                    // Show the keyboard
//                    editText.requestFocus()
//                    context.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//                }
//                setOnDismissListener {
//                    // Hide the keyboard
//                    context.inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
//                }
//            }
//    }
    private val _viewModel: CalendarViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _manageWorkRecycle: RecyclerView
    private lateinit var _manageWorksAdapter: ManageWorkAdapter
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
    private val events = mutableMapOf<LocalDate, List<Event>>()
    private lateinit var binding: FragmentCalendarBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViewModelData()
        initObservers()
        val workList = _viewModel.mutableWorkList.value!!
        _manageWorksAdapter = ManageWorkAdapter(
            workList,
            _clickOnItemListener = { openWorkEditMode(it) },
            _clickDeleteItemListener = { deleteWork(it) }
        )

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding = FragmentCalendarBinding.bind(view)

        binding.worksCreatedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = _manageWorksAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }



        //binding = FragmentCalendarBinding.bind(view)



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
        binding.calendarView.monthScrollListener = {
            homeActivityToolbar.title = if (it.year == today.year) {
                titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }
            if(it.month == today.monthValue){
                selectDate(today)
            }else {
                // Select the first day of the month when
                // we scroll to a new month.
                selectDate(it.yearMonth.atDay(1))
            }
        }
        setHasOptionsMenu(true)
        //todo manage back press
        activity?.onBackPressedDispatcher?.addCallback(
            this.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(selectedDate!!.monthValue != today.monthValue){
                        binding.calendarView.smoothScrollToDate(today,DayOwner.THIS_MONTH)
                    }
                    else if (selectedDate!=today) {
                        //selectDate(today)
                        binding.calendarView.post {
                            // Show today's events initially.
                            selectDate(today)
                        }

                        //binding.calendarView.scrollToMonth(currentMonth)

                        //this@CalendarFragment.onViewCreated(view,savedInstanceState)
                        //onStart()
                    } else {
                        isEnabled = false
                        activity?.onBackPressed()
                    }
                }
            })


        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = CalendarDayBinding.bind(view)

            init {
                //binding.dotView.makeInVisible()
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
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
                            if (workList.size > 0) {
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

//        binding.exThreeAddButton.setOnClickListener {
//            inputDialog.show()
//        }


        //binding.calendarView.scrollToMonth(currentMonth)

    }


    private fun initViewModelData() {
        _viewModel.getAllOfYourWorks()
    }

    private fun initObservers() {
        _viewModel.mutableWorkList.observe(
            viewLifecycleOwner,
            Observer { workList ->
                workList?.let {
                    updateRecyclersAndAdapters(null)

                }
            }
        )
    }

    //todo need to put away from here
    private fun openWorkEditMode(work: WorkSerializable) {
        val intent = Intent(activity, AddWorkActivity::class.java)
//        val bundle = Bundle()
//        bundle.putParcelable(Const.WORK_OBJECT, work)
//        intent.putExtras(bundle)
        WorkData.currentWork = work
        startActivity(intent)
    }

    //todo need to put away from here
    private fun deleteWork(work: WorkSerializable) {
        val removeWorkView: View = layoutInflater.inflate(R.layout.work_remove_popup, null)
        val popup = PopupWindow(
            removeWorkView,
            WRAP_CONTENT,
            WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val cancel = removeWorkView.findViewById(R.id.cancel) as TextView
        val delete =
            removeWorkView.findViewById(R.id.delete) as TextView
        val workInfo = removeWorkView.findViewById(R.id.work_info) as TextView
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
        popup.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    //todo need to put away from here
    private fun removeBackground(show: Boolean) {
        if (show) {
            worksCreatedRecyclerView.visibility = View.VISIBLE

        } else {
            worksCreatedRecyclerView.visibility = View.GONE
        }
    }

    fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendarView.notifyDateChanged(it) }
            binding.calendarView.notifyDateChanged(date)
            //updateAdapterForDate(date)
            updateRecyclersAndAdapters(null)
        }
    }

    private fun updateRecyclersAndAdapters(date: LocalDate?) {
        _manageWorksAdapter.setData(_viewModel.mutableWorkList.value!!)
        _manageWorksAdapter.notifyDataSetChanged()
        binding.calendarView.notifyCalendarChanged()
        if (date != null) {
            binding.selectedDateText.text = selectionFormatter.format(date)
        }
    }

//    private fun saveEvent(text: String) {
//        if (text.isBlank()) {
//            Toast.makeText(requireContext(), R.string.example_3_empty_input_text, Toast.LENGTH_LONG).show()
//        } else {
//            selectedDate?.let {
//                events[it] = events[it].orEmpty().plus(Event(UUID.randomUUID().toString(), text, it))
//                updateAdapterForDate(it)
//            }
//        }
//    }

//    private fun deleteEvent(event: Event) {
//        val date = event.date
//        events[date] = events[date].orEmpty().minus(event)
//        updateAdapterForDate(date)
//    }

//    private fun updateAdapterForDate(date: LocalDate) {
//        eventsAdapter.apply {
//            events.clear()
//            events.addAll(this@CalendarFragment.events[date].orEmpty())
//            notifyDataSetChanged()
//        }
//        binding.exThreeSelectedDateText.text = selectionFormatter.format(date)
//    }

    //todo:implementation of the actionbar back press!!!
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.getItemId() == android.R.id.home) {
//            if (activity != null) {
//                activity?.onBackPressed()
//            }
//            return true
//        }
//        return super.onOptionsItemSelected(item)
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
        requireActivity().window.statusBarColor = requireContext().getColorCompat(R.color.colorPrimaryDark)
    }
}

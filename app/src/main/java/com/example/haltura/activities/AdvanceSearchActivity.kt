package com.example.haltura.activities

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.WorkAdapter
import com.example.haltura.Fragments.HomeFragments.WatchWorkDialog
import com.example.haltura.Fragments.HomeFragments.WatchWorkHistoryEmployeeDialog
import com.example.haltura.R
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.ViewModels.AdvanceSearchActivityViewModel
import com.example.haltura.ViewModels.WorkHistoryViewModel
import com.example.haltura.databinding.ActivityNlpWorksBinding
import com.example.haltura.databinding.ActivityWorkHistoryBinding

class AdvanceSearchActivity : AppCompatActivity() {
    private lateinit var _viewModel: AdvanceSearchActivityViewModel

    private var _binding: ActivityNlpWorksBinding? = null
    private lateinit var _view : View
    //Work History
    private lateinit var _workNlpRecycle: RecyclerView
    private lateinit var _workNlpAdapter: WorkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)
        _binding = ActivityNlpWorksBinding.inflate(layoutInflater)
        _view = _binding!!.root
        setContentView(_view)
        initViewModel()
        //initViewModelData()
        initViews()
        initButtons()
        initObservers()
        initRecyclersAndAdapters()
    }

    private fun initButtons() {
        _binding!!.searchButton.setOnClickListener {
            var prompt = _binding!!.searchText.text.toString()
            _viewModel.getNlpWorks(prompt)
        }
    }

    private fun initViewModel() {
        _viewModel = ViewModelProvider(this).get(AdvanceSearchActivityViewModel::class.java)
    }

//    private fun initViewModelData() {
//        _viewModel.getWorkHistory()
//    }

    private fun initRecyclersAndAdapters() {
        _workNlpRecycle = _binding!!.workHistoryRecyclerView
        val workList = _viewModel.mutableNlpWorkList.value!!
        val layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        _workNlpRecycle.addItemDecoration(VerticalSpaceItemDecoration(20))
        _workNlpRecycle.layoutManager = layoutManager
        //_workHistoryRecycle.layoutManager = LinearLayoutManager(this)
        _workNlpAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkInfo(it) })
        _workNlpRecycle.adapter = _workNlpAdapter
    }

    private fun showWorkInfo(work: WorkSerializable) {
        val dialog = WatchWorkDialog(work)
        this.supportFragmentManager.let {
            dialog.show(it, "WatchWorkDialog")
        }
    }

    private fun initObservers() {
        _viewModel.mutableNlpWorkList.observe(
            this,
            Observer { workHistory ->
                workHistory?.let {
                    updateRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateRecyclersAndAdapters() {
        _workNlpAdapter.setData(_viewModel.mutableNlpWorkList.value!!)
        _workNlpAdapter.notifyDataSetChanged()
    }


    fun initViews() {

    }

    // this event will enable the back
    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //finish()
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //remove focus from edit texts
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    companion object
    {

    }
}
//: AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_advance_search)
//        val actionBar = getSupportActionBar()
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//    }
//    // this event will enable the back
//    // function to the button on press
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                //finish()
//                onBackPressed()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//
//
//
//
//
//    //remove focus from edit texts
//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        if (event.action == MotionEvent.ACTION_DOWN) {
//            val v = currentFocus
//            if (v is EditText) {
//                val outRect = Rect()
//                v.getGlobalVisibleRect(outRect)
//                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
//                    v.clearFocus()
//                    val imm: InputMethodManager =
//                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
//                }
//            }
//        }
//        return super.dispatchTouchEvent(event)
//    }
//}
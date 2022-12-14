package com.example.haltura.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haltura.Adapters.WorkAdapter
import com.example.haltura.Fragments.HomeFragments.WatchWorkHistoryEmployeeDialog
import com.example.haltura.Sql.Items.WorkSerializable
import com.example.haltura.Utils.VerticalSpaceItemDecoration
import com.example.haltura.ViewModels.WorkHistoryViewModel
import com.example.haltura.databinding.ActivityWorkHistoryBinding


class WorkHistoryActivity : AppCompatActivity() {
    private lateinit var _viewModel: WorkHistoryViewModel

    private var _binding: ActivityWorkHistoryBinding? = null
    private lateinit var _view : View
    //Work History
    private lateinit var _workHistoryRecycle: RecyclerView
    private lateinit var _workHistoryAdapter: WorkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWorkHistoryBinding.inflate(layoutInflater)
        _view = _binding!!.root
        setContentView(_view)
        initViewModel()
        initViewModelData()
        initViews()
        initObservers()
        initRecyclersAndAdapters()
        val actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)
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

    private fun initViewModel() {
        _viewModel = ViewModelProvider(this).get(WorkHistoryViewModel::class.java)
    }

    private fun initViewModelData() {
        _viewModel.getWorkHistory()
    }

    private fun initRecyclersAndAdapters() {
        _workHistoryRecycle = _binding!!.workHistoryRecyclerView
        val workList = _viewModel.mutableWorkHistoryList.value!!
        val layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        _workHistoryRecycle.addItemDecoration(VerticalSpaceItemDecoration(20))
        _workHistoryRecycle.layoutManager = layoutManager
        _workHistoryAdapter = WorkAdapter(
            workList,
            _clickOnItemListener = { showWorkInfo(it) })
        _workHistoryRecycle.adapter = _workHistoryAdapter
    }

    private fun showWorkInfo(work: WorkSerializable) {
        var dialog = WatchWorkHistoryEmployeeDialog(work)
        this?.supportFragmentManager?.let {
            dialog.show(it, "WatchWorkHistoryEmployeeDialog")
        }
    }

    private fun initObservers() {
        _viewModel.mutableWorkHistoryList.observe(
            this,
            Observer { workHistory ->
                workHistory?.let {
                    updateRecyclersAndAdapters()
                }
            }
        )
    }

    private fun updateRecyclersAndAdapters() {
        _workHistoryAdapter.setData(_viewModel.mutableWorkHistoryList.value!!)
        _workHistoryAdapter.notifyDataSetChanged()
    }


    fun initViews() {

    }

    companion object
    {

    }
}
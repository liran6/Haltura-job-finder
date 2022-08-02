package com.example.haltura.Fragments.ManageFragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haltura.R
import com.example.haltura.ViewModels.ManageReportsViewModel

class ManageReports : Fragment() {

    companion object {
        fun newInstance() = ManageReports()
    }

    private lateinit var viewModel: ManageReportsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manage_reports, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ManageReportsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
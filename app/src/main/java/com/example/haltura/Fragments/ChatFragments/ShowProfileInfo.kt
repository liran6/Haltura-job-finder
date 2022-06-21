package com.example.haltura.Fragments.ChatFragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haltura.R
import com.example.haltura.ViewModels.ShowProfileInfoViewModel

class ShowProfileInfo : Fragment() {

    companion object {
        fun newInstance() = ShowProfileInfo()
    }

    private lateinit var viewModel: ShowProfileInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_profile_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowProfileInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
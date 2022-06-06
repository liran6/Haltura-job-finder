package com.example.haltura.Fragments.WorkFragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.haltura.ViewModels.WorkViewModel
import com.example.haltura.databinding.FragmentWorkBinding

class WorkFragment : Fragment() {

    private var _binding: FragmentWorkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val workViewModel =
            ViewModelProvider(this).get(WorkViewModel::class.java)

        _binding = FragmentWorkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textWork
        workViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
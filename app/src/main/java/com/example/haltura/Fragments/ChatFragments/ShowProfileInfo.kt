package com.example.haltura.Fragments.ChatFragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.ViewModels.ShowProfileInfoViewModel
import com.example.haltura.databinding.FragmentShowProfileInfoBinding

class ShowProfileInfo : Fragment {

    private lateinit var _userId: String
    private var _createdFromUserId: Boolean = false
    private lateinit var _profile: ProfileSerializable

    private val _viewModel: ShowProfileInfoViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _image: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var _username: TextView
    private lateinit var _fullname: TextView

    private var _binding: FragmentShowProfileInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    constructor(userId: String):super()
    {
        this._userId = userId
        _createdFromUserId = true
    }

    constructor(profile: ProfileSerializable):super()
    {
        this._profile = profile
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowProfileInfoBinding.inflate(inflater, container, false)
        initBinding()
        initViews()
        if (_createdFromUserId) {
            onCreateViewFromUserId()
        } else {
            onCreateViewFromProfile()
        }
        return _fragmentView
    }

    private fun onCreateViewFromProfile() {
        putValues(_profile)
    }

    private fun onCreateViewFromUserId() {
        initViewModelData()
        initObservers()
    }

    private fun initBinding() {
        _fragmentView = binding.root
    }

    private fun initViews() {
        _image = binding.profileImage
        _username = binding.username
        _fullname = binding.fullName
    }

    private fun initViewModelData() {
        _viewModel.getProfileInfo(_userId)
    }

    private fun initObservers() {
        _viewModel.mutableProfileInfo.observe(
            viewLifecycleOwner,
            Observer { ChatInfo ->
                ChatInfo?.let {
                    if (it != null) {
                        putValues(it)
                    }
                }
            }
        )
    }

    private fun putValues(ProfileInfo: ProfileSerializable) {
        _username.setText(ProfileInfo.username)
        _fullname.setText(ProfileInfo.firstName + " " + ProfileInfo.lastName )
        if (ProfileInfo.profilePicture != null)
        {
            //image
            var bm = Base64.decode(ProfileInfo.profilePicture, Base64.DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            _image.setImageBitmap(data)
        }
        else
        {
            //todo: set Default
        }
    }
}
package com.example.haltura.Fragments.ProfileFragments


import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.haltura.R
import com.example.haltura.Utils.ProfileData
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.ProfileViewModel
import com.example.haltura.databinding.FragmentSettingsBinding
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment : Fragment() {



    private val _viewModel: ProfileViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _profileImage: CircleImageView
    private lateinit var _profileUserName: TextView
    private lateinit var _email: TextView
    private lateinit var _firstName: EditText
    private lateinit var _lastName: EditText
    private lateinit var _phone: EditText
    private lateinit var _address: EditText
    private lateinit var _saveButton: Button
    private lateinit var _textWatcher: TextWatcher

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentView = inflater.inflate(R.layout.fragment_profile, container, false)

        initViews()
        initListeners()
        initProfileData()
        //initButtons()


        return _fragmentView
    }

    private fun initViews() {
        _profileImage = _fragmentView.findViewById<View>(R.id.profileCircleImageView) as CircleImageView
        _profileUserName = _fragmentView.findViewById<View>(R.id.usernameTextView) as TextView
        _email = _fragmentView.findViewById<View>(R.id.userEmail) as TextView
        _firstName = _fragmentView.findViewById<View>(R.id.profileFirstName) as EditText
        _lastName = _fragmentView.findViewById<View>(R.id.profileLasstName) as EditText
        _phone = _fragmentView.findViewById<View>(R.id.profilePhone) as EditText
        _address = _fragmentView.findViewById<View>(R.id.profileAddress) as EditText
        _saveButton = _fragmentView.findViewById<View>(R.id.saveButton) as Button
        _saveButton.visibility = View.INVISIBLE
//        loadingScreen = (activity as SettingsActivity).loadingScreen

        //_settingsFragment = _fragmentView.findViewById<View>(R.id.btn_SignUp) as Button
//        _editProfile = binding.profilePicruteLayout
//        _profileImage = binding.profileCircleImageView
//        _profileUserName = binding.usernameTextView
//        _settingsFragment = binding.settingsFffragment
    }
    private fun initProfileData() {
        //from user info
        _profileUserName.text = UserData.currentUser?.username
        _email.text = UserData.currentUser!!.email
        var userImage = ProfileData.currentProfile?.profilePicture
        if (userImage != null) {
            var bm = Base64.decode(userImage, Base64.DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            _profileImage.setImageBitmap(data) //TODO set image profile to bit64
        }
        //from profile info
        var profileFirstName = ProfileData.currentProfile?.firstName
        var profileLastName = ProfileData.currentProfile?.lastName
        var profilePhone = ProfileData.currentProfile?.phone
        var profileAddress = ProfileData.currentProfile?.address
        _firstName.setText(profileFirstName)
        _firstName.addTextChangedListener(_textWatcher)
        _lastName.setText(profileLastName)
        _lastName.addTextChangedListener(_textWatcher)
        _phone.setText(profilePhone)
        _phone.addTextChangedListener(_textWatcher)
        _address.setText(profileAddress)
        _address.addTextChangedListener(_textWatcher)
    }
    private fun initListeners(){

        _textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if(!(s.equals(""))){
                    _saveButton.visibility = View.VISIBLE
                }

            }
        }



        _saveButton.setOnClickListener {
            if (_saveButton.visibility == View.VISIBLE){
                if (!(_firstName.equals(""))){
                    ProfileData.currentProfile?.firstName = _firstName.toString()
            }
                if (_lastName.text.toString() != ""){
                    ProfileData.currentProfile?.lastName = _lastName.toString()
                }
                if (_phone.text.toString() != ""){
                    ProfileData.currentProfile?.phone = _phone.toString()
                }
                if (_address.text.toString() != ""){
                    ProfileData.currentProfile?.address = _address.toString()
                }
                //todo here: update DB via viewModel
        }
//        _firstName.addTextChangedListener(object : TextWatcher{
//            override fun afterTextChanged(s: Editable) {
//                if (s.toString()!= ){
//                    _saveButton.visibility = View.VISIBLE
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//            }
//        })
        }
    }
//private class GenericTextWatcher private constructor(val view: View) :
//    TextWatcher {
//    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//    override fun afterTextChanged(editable: Editable) {
//        val text = editable.toString()
//        when (view.id) {
////                R.id.name -> model.setName(text)
////                R.id.email -> model.setEmail(text)
////                R.id.phone -> model.setPhone(text)
//        }
//    }
//}



//    companion object {
//        fun newInstance() = ProfileFragment()
//    }
//    @Deprecated("Deprecated in Java")
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}
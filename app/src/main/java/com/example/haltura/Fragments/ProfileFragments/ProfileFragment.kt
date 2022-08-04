package com.example.haltura.Fragments.ProfileFragments


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.haltura.Fragments.ChatFragments.ShowChatInfoDialog
import com.example.haltura.R
import com.example.haltura.Utils.ProfileData
import com.example.haltura.Utils.UserData
import com.example.haltura.ViewModels.SettingsViewModel
import com.example.haltura.databinding.FragmentProfileBinding
import com.example.haltura.databinding.FragmentSettingsBinding
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream


class ProfileFragment : Fragment() {

    private val _viewModel: SettingsViewModel by activityViewModels()
    private lateinit var _fragmentView: View
    private lateinit var _profileImage: CircleImageView
    private lateinit var _userName: TextView
    private lateinit var _email: TextView
    private lateinit var _firstName: EditText
    private lateinit var _lastName: EditText
    private lateinit var _phone: EditText
    private lateinit var _address: EditText
    private lateinit var _saveButton: Button
    private lateinit var _textWatcher: TextWatcher
    private var _isImageChanged : Boolean = false

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        _fragmentView = _binding!!.root
        //_fragmentView = inflater.inflate(R.layout.fragment_profile, container, false)

        initViews()
        initListeners()
        initProfileData()
        initButtons()
        //val xp = ProfileData.currentProfile

        return _fragmentView
    }

    private fun initButtons() {
        binding.profileCircleImageView.setOnClickListener{
            editProfilePicture()
        }
    }

    private fun editProfilePicture() {
        val imagePopup: View = layoutInflater.inflate(R.layout.camera_or_gallery_popup, null)

        val popup = PopupWindow(
            imagePopup,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val camera = imagePopup.findViewById(R.id.camera) as ImageView
        val gallery = imagePopup.findViewById(R.id.gallery) as ImageView


        camera.setOnClickListener {
            setCameraImage()
            popup.dismiss()
            removeBackground(true)
        }

        gallery.setOnClickListener {
            setGalleryImage()
            popup.dismiss()
            removeBackground(true)
        }
        removeBackground(false)
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0) //popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }

    private fun setCameraImage()
    {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, ProfileFragment.REQ_CAMERA)
    }

    private fun setGalleryImage()
    {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            ProfileFragment.PICK_IMAGE
        )
    }

    private fun removeBackground(show: Boolean) {
        if (show) {
            binding.profileFragment.visibility = View.VISIBLE

        } else {
            binding.profileFragment.visibility = View.GONE
        }
    }



    private fun initViews() {
        _profileImage = _fragmentView.findViewById<View>(R.id.profileCircleImageView) as CircleImageView
        _userName = _fragmentView.findViewById<View>(R.id.usernameTextView) as TextView
        _email = _fragmentView.findViewById<View>(R.id.userEmail) as TextView
        _firstName = _fragmentView.findViewById<View>(R.id.profileFirstName) as EditText
        _lastName = _fragmentView.findViewById<View>(R.id.profileLasstName) as EditText
        _phone = _fragmentView.findViewById<View>(R.id.profilePhone) as EditText
        _address = _fragmentView.findViewById<View>(R.id.profileAddress) as EditText
        _saveButton = _fragmentView.findViewById<View>(R.id.saveButton) as Button
        _saveButton.visibility = View.INVISIBLE
    }
    private fun initProfileData() {
        //from user info
        _userName.text = UserData.currentUser?.username
        _email.text = UserData.currentUser!!.email
        var userImage = ProfileData.currentProfile?.profilePicture
        if (userImage != null) {
            var bm = Base64.decode(userImage, Base64.DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            _profileImage.setImageBitmap(data)
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

    private fun convertImageToString(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        return  Base64.encodeToString(data, Base64.DEFAULT)
    }

    private fun initListeners(){

        _textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                _saveButton.visibility = View.VISIBLE
            }
            override fun afterTextChanged(s: Editable) {
                if((s.equals(""))) {
                    _saveButton.visibility = View.GONE

                }
            }
        }



        _saveButton.setOnClickListener {
            if (_saveButton.visibility == View.VISIBLE){
                if (_firstName.toString() != ("")){
                    ProfileData.currentProfile!!.firstName=_firstName.text.toString()
                    //_saveButton.visibility = View.GONE
                }
                if (_lastName.text.toString() != ("")){
                    ProfileData.currentProfile!!.lastName = _lastName.text.toString()
                   // _saveButton.visibility = View.GONE
                }
                if (_phone.text.toString() != ("")){
                    ProfileData.currentProfile!!.phone = _phone.text.toString()
                    //_saveButton.visibility = View.GONE
                }
                if ((_address.text.toString() != (""))){
                    ProfileData.currentProfile!!.address = _address.text.toString()
                    //_saveButton.visibility = View.GONE
                }
                if (_isImageChanged){
                    _isImageChanged = false
                    ProfileData.currentProfile!!.profilePicture = convertImageToString(
                        _binding!!.profileCircleImageView.drawable.toBitmap())
                    //_saveButton.visibility = View.GONE
                }
                _saveButton.visibility = View.GONE
                _viewModel.updateProfileData(ProfileData.currentProfile!!)

        }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAMERA && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                var bm = data.extras!!["data"] as Bitmap?
                bm.toString()
                _binding!!.profileCircleImageView.setImageBitmap(bm)
                _isImageChanged = true
                _saveButton.visibility = View.VISIBLE
                //_image.setImageBitmap(bm)
            }
        }
        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                val uri = data.getData();
                val bm = MediaStore.Images.Media.getBitmap(activity!!.getContentResolver(), uri)
                //_image.setImageBitmap(bm)//sendImageMessage(imageBitMap)
                _binding!!.profileCircleImageView.setImageBitmap(bm)
                _isImageChanged = true
                _saveButton.visibility = View.VISIBLE
            }
        }
    }

    companion object
    {
        private val REQ_CAMERA = 1
        private val PICK_IMAGE = 2
    }

}
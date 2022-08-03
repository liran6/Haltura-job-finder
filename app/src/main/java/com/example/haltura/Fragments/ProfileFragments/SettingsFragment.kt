package com.example.haltura.Fragments.ProfileFragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.InputType
import android.util.Base64
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.haltura.Adapters.SettingAdapter
import com.example.haltura.AppNotifications
import com.example.haltura.Models.ProfileSerializable
import com.example.haltura.R
import com.example.haltura.Utils.*
import com.example.haltura.ViewModels.SettingsViewModel
import com.example.haltura.activities.LoginActivity
import com.example.haltura.activities.WorkHistoryActivity
import com.example.haltura.databinding.FragmentSettingsBinding
import com.example.haltura.databinding.ShowChatInfoDialogBinding
import de.hdodenhof.circleimageview.CircleImageView


class SettingsFragment : Fragment() {
    // PreferenceFragmentCompat() {
    lateinit var loadingScreen: RelativeLayout
    private lateinit var preferences: SharedPreferences
    private val _viewModel: SettingsViewModel by activityViewModels()
    private lateinit var _fragmentView: View

    //private lateinit var _settingRecycle: RecyclerView
    private lateinit var _editProfile: RelativeLayout
    private lateinit var _profileImage: CircleImageView
    private lateinit var _profileUserName: TextView
    private lateinit var _logOut: TextView
    private lateinit var _changePassword: TextView
    private lateinit var _changeEmail: TextView
    private lateinit var _history: TextView
    private lateinit var _currentPassword: EditText
    private lateinit var _newPassword: EditText
    private lateinit var _ConfNewPassword: EditText
    private lateinit var _settingsFragment: FrameLayout

    private lateinit var _settingAdapter: SettingAdapter

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        _fragmentView = _binding!!.root
        //_fragmentView = inflater.inflate(R.layout.fragment_settings, container, false)
        // _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        // _fragmentView = binding.root
        _viewModel.getCurrentProfile()
        initViews()
        initUserData()
        initButtons()
//        initTextListener()
//        initViewModelData()
//        initObservers()
        //initRecyclersAndAdapters()

        return _fragmentView
    }
    private fun initViews() {
        _editProfile = _fragmentView.findViewById<View>(R.id.profilePicruteLayout) as RelativeLayout
        _profileImage =
            _fragmentView.findViewById<View>(R.id.profileCircleImageView) as CircleImageView
        _profileUserName = _fragmentView.findViewById<View>(R.id.usernameTextView) as TextView
        _logOut = _fragmentView.findViewById<View>(R.id.logout_button) as TextView
        _changePassword = _fragmentView.findViewById<View>(R.id.change_password_button) as TextView
        _changeEmail = _fragmentView.findViewById<View>(R.id.change_email_button) as TextView
//        loadingScreen = (activity as SettingsActivity).loadingScreen

        //_settingsFragment = _fragmentView.findViewById<View>(R.id.btn_SignUp) as Button
//        _editProfile = binding.profilePicruteLayout
//        _profileImage = binding.profileCircleImageView
//        _profileUserName = binding.usernameTextView
//        _settingsFragment = binding.settingsFffragment
    }

    private fun initUserData() {
        _profileUserName.text = UserData.currentUser!!.username
        var userImage = ProfileData.currentProfile?.profilePicture
        if (userImage != null) {
            var bm = Base64.decode(userImage, Base64.DEFAULT)
            var data = BitmapFactory.decodeByteArray(bm, 0, bm.size)
            _profileImage.setImageBitmap(data) //TODO set image profile to bit64
        }
    }

    private fun initButtons() {
        _editProfile.setOnClickListener {
            switchFragment(
                ProfileFragment(),
                Const.profile_fragment
            )
        }
        _logOut.setOnClickListener { logOut() }
        _changePassword.setOnClickListener { changePasswordDialog() }
        _changeEmail.setOnClickListener { changeEmail() }
        _binding!!.historyButton.setOnClickListener{
            startActivity(Intent(activity, WorkHistoryActivity::class.java))
        }
    }

    private fun changePassword() {
        if (Validation.changePasswordValid(
                _currentPassword, _newPassword,
                _ConfNewPassword
            )
        ) {
            //todo: Admin user
            var password = _newPassword!!.text.toString()
            _viewModel.updateUserPassword(password)
        }
        else{
            AppNotifications.toastBar(activity!!, Const.CONF_PASSWORD_ERROR)
        }
    }

    private fun changeEmail() {
        //if (profile.userId == UserData.currentUser!!.userId){return}

        val changeEmailView: View = layoutInflater.inflate(R.layout.change_email_popup, null)
        val popup = PopupWindow(
            changeEmailView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val cancel = changeEmailView.findViewById(R.id.cancel) as TextView
        val update = changeEmailView.findViewById(R.id.update) as TextView
        val email = changeEmailView.findViewById(R.id.email) as EditText

        email.setText(ProfileData.currentProfile!!.email)

        cancel.setOnClickListener {
            popup.dismiss()
            removeBackground(true)
        }

        update.setOnClickListener {
            //_viewModel.removeUser(_chatId, profile.userId!!)
            //todo: api call
            _viewModel.updateUserEmail(email.text.toString())
            popup.dismiss()
            removeBackground(true)
        }

        removeBackground(false)
        popup.isFocusable = true
        popup.update()
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }



    private fun logOut() {
        preferences = Preferences.customPrefs(activity!!, Const.loginPreferences)
        //val settings = context!!.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE)
        preferences.edit().clear().commit()
        //preferences.set(Const.IsLoggedIn, false)
        //val intent = Intent(activity!!, LoginActivity::class.java)
        val intent = Intent(activity!!, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("EXIT", true)
        startActivity(intent)
        //viewModel.logOut()
        activity!!.finish()
    }

//    fun changePasswordDialog() { //TODO: check old password validation + input password as ***
//        //todo: make popup with xml
//        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
//        builder.setTitle("Enter your old & new passwords here:")
//        val layout = LinearLayout(context)
//        layout.orientation = LinearLayout.VERTICAL
//// Set up the input
//        _currentPassword = EditText(context)
//        _currentPassword.setHint("Enter yor current password")
//        layout.addView(_currentPassword)
//
//        _newPassword = EditText(context)
//        _newPassword.setHint("Enter yor new password")
//        layout.addView(_newPassword)
//
//        _ConfNewPassword = EditText(context)
//        _ConfNewPassword.setHint("Confirm yor new password")
//        layout.addView(_ConfNewPassword)
//
//        _currentPassword.inputType = InputType.TYPE_CLASS_TEXT
//        _newPassword.inputType = InputType.TYPE_CLASS_TEXT
//        _ConfNewPassword.inputType = InputType.TYPE_CLASS_TEXT
//        builder.setView(layout)
//
//        // Set up the buttons
//        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
//            changePassword()
//        })
//        builder.setNegativeButton(
//            "Cancel",
//            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
//
//        builder.show()
//    }

    private fun changePasswordDialog(){//private fun removeUser(profile: ProfileSerializable) {
        //if (profile.userId == UserData.currentUser!!.userId){return}

        val changePasswordView: View = layoutInflater.inflate(R.layout.change_password_popup, null)
        val popup = PopupWindow(
            changePasswordView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        popup.elevation = 3.0f

        val cancel = changePasswordView.findViewById(R.id.cancel) as TextView
        val update = changePasswordView.findViewById(R.id.update) as TextView
        val currentPassword = changePasswordView.findViewById(R.id.current_password) as EditText
        val newPassword = changePasswordView.findViewById(R.id.new_password) as EditText
        val confirmNewPassword = changePasswordView.findViewById(R.id.confirm_password) as EditText

        cancel.setOnClickListener {
            popup.dismiss()
            removeBackground(true)
        }

        update.setOnClickListener {
            //_viewModel.removeUser(_chatId, profile.userId!!)
            _currentPassword = currentPassword
            _newPassword = newPassword
            _ConfNewPassword = confirmNewPassword
            changePassword()
            popup.dismiss()
            removeBackground(true)
        }

        removeBackground(false)
        popup.isFocusable = true
        popup.update()
        popup.showAtLocation(_fragmentView, Gravity.CENTER, 0, 0)
    }

    private fun removeBackground(show: Boolean) {
        if (show) {
            binding.settingsFragment.visibility = View.VISIBLE

        } else {
            binding.settingsFragment.visibility = View.GONE
        }
    }


    //    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        setPreferencesFromResource(R.xml.root_preferences, rootKey)
//        val logOutPreference: Preference? = findPreference("logOut")
//
//        logOutPreference?.setOnPreferenceClickListener {
//            preferences = Preferences.customPrefs(activity!!, Const.loginPreferences)
//            //val settings = context!!.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE)
//            preferences.edit().clear().commit()
//            //preferences.set(Const.IsLoggedIn, false)
//            //val intent = Intent(activity!!, LoginActivity::class.java)
//            val intent = Intent(activity!!, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            intent.putExtra("EXIT", true)
//            startActivity(intent)
//            //viewModel.logOut()
//            activity!!.finish()
//            true
//        }
//    }
    private fun initViewModelData() {
        // _viewModel.
        var x = 1
    }

    private fun initRecyclersAndAdapters() {
        _editProfile = binding.profilePicruteLayout
        //_editProfile.layoutManager = LinearLayoutManager(context)

        //_editProfile.adapter = _settingAdapter
    }

    private fun switchFragment(fragment: Fragment, fragmentId: String) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.settings_fragment, fragment, fragmentId)
            transaction.addToBackStack(null)//fragmentId
            //transaction.setReorderingAllowed(true)
            transaction.commit()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        initUserData()
    }
    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


//class SettingsFragment : Fragment() {
//
//    companion object {
//        fun newInstance() = SettingsFragment()
//    }
//
//    private lateinit var viewModel: SettingsViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_settings, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
//
//}
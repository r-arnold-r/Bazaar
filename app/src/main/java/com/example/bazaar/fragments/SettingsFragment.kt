package com.example.bazaar.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bazaar.MyApplication
import com.example.bazaar.R
import com.example.bazaar.api.model.UpdateUserDataRequest
import com.example.bazaar.api.model.User
import com.example.bazaar.databinding.FragmentProductDetailBinding
import com.example.bazaar.databinding.FragmentSettingsBinding
import com.example.bazaar.manager.SharedPreferencesManager
import com.example.bazaar.repository.Repository
import com.example.bazaar.viewmodels.*
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var getUserInfoViewModel : GetUserInfoViewModel
    private lateinit var updateUserDataViewModel : UpdateUserDataViewModel



    companion object
    {
        fun newInstance(): SettingsFragment
        {
            return SettingsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // creates LoginViewModel with factory
        val factory = GetUserInfoViewModelFactory(this.requireContext(), Repository())
        getUserInfoViewModel = ViewModelProvider(this, factory)[GetUserInfoViewModel::class.java]

        val factory2 = UpdateUserDataViewModelFactory(this.requireContext(), Repository())
        updateUserDataViewModel = ViewModelProvider(this, factory2)[UpdateUserDataViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        val username = arguments?.getString("username")

        resetErrorMessageOnTextInputLayouts()
        if(username == MyApplication.sharedPreferences.getUserValue(SharedPreferencesManager.KEY_USER, User()).username)
        {
            binding.emailEt.keyListener = null
            binding.publishBtn.setOnClickListener{
                tryToPublish()
            }
        }
        else
        {
            binding.emailEt.keyListener = null
            binding.usernameEt.keyListener = null
            binding.phoneEt.keyListener = null
            binding.publishBtn.text = "Send a chat message"
        }


        if (username != null) {
            tryToGetUserInfo(username)
        }
        tryToGetUserInfoSuccessful()
        updateUserDataSuccessful()

        // back arrow
        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow)

        binding.toolbar.setNavigationOnClickListener {
            // back button pressed
            findNavController().navigateUp()
        }

        return view
    }

    private fun resetErrorMessageOnTextInputLayouts()
    {
        binding.usernameEt.setOnClickListener{
            binding.usernameTil.error = null
            binding.usernameTil.isErrorEnabled = false
        }
        binding.phoneTil.setOnClickListener{
            binding.phoneTil.error = null
            binding.phoneTil.isErrorEnabled = false
        }
    }

    private fun tryToPublish()
    {
        // make register button not clickable
        binding.publishBtn.isClickable = false

        // resets error message on text input layouts
        binding.usernameTil.error = null
        binding.usernameTil.isErrorEnabled = false
        binding.phoneTil.error = null
        binding.phoneTil.isErrorEnabled = false


        // analyzes wrong inputs
        if(binding.usernameEt.text.trim().isEmpty()){
            binding.usernameTil.error = "Please input your username!"
            // make login button clickable
            binding.publishBtn.isClickable = true
            return
        }
        if(binding.usernameEt.text.trim().length < 3){
            binding.usernameTil.error = "Your username must contain at least 3 characters!"
            // make login button clickable
            binding.publishBtn.isClickable = true
            return
        }
        if(binding.phoneEt.text.trim().isEmpty()){
            binding.phoneTil.error = "Please input your phone number!"
            // make login button clickable
            binding.publishBtn.isClickable = true
            return
        }

        updateUserDataViewModel.updateUserDataRequest.value.let {
            if (it != null) {
                it.username = binding.usernameEt.text.toString()
            }
            if (it != null) {
                it.phone_number = binding.phoneEt.text.toString().toLong()
            }
        }

        // attempt to log in inside lifecycleScope
        lifecycleScope.launch {
            updateUserDataViewModel.updateUserData()
        }

    }

    /** Navigates to LoginFragment on successful register **/
    private fun updateUserDataSuccessful(){
        updateUserDataViewModel.updateUserDataRequest.observe(viewLifecycleOwner){
            // make login button clickable
            binding.publishBtn.isClickable = true
        }
    }

    private fun tryToGetUserInfo(username: String)
    {
        lifecycleScope.launch {
            getUserInfoViewModel.getUserInfo(username)
        }
    }

    private fun tryToGetUserInfoSuccessful(){
        getUserInfoViewModel.userResponse.observe(viewLifecycleOwner){
            binding.emailEt.setText(getUserInfoViewModel.userResponse.value?.data?.get(0)?.email.toString())
            binding.phoneEt.setText(getUserInfoViewModel.userResponse.value?.data?.get(0)?.phone_number.toString())
            binding.usernameEt.setText(getUserInfoViewModel.userResponse.value?.data?.get(0)?.username.toString())
            binding.profileNameTv.text = getUserInfoViewModel.userResponse.value?.data?.get(0)?.username.toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.app_bar_menu_settings)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_settings -> {
                    // Save profile changes
                    Toast.makeText(requireContext(), "Click Settings Icon.", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
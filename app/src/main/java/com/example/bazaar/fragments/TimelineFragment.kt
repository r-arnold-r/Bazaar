package com.example.bazaar.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bazaar.MyApplication
import com.example.bazaar.activities.MainActivity
import com.example.bazaar.api.model.User
import com.example.bazaar.databinding.FragmentTimelineBinding
import com.example.bazaar.manager.SharedPreferencesManager
import com.example.bazaar.viewmodels.LoginViewModel
import com.example.bazaar.viewmodels.MainActivityViewModel


class TimelineFragment : Fragment() {

    private var _binding: FragmentTimelineBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel

    companion object
    {
        fun newInstance(): TimelineFragment
        {
            return TimelineFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("xxx", "user = "  + MyApplication.sharedPreferences.getUserValue(
            SharedPreferencesManager.KEY_USER, User()
        ))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        val view = binding.root

        makeBottomNavigationVisible()

        return view
    }

    private fun makeBottomNavigationVisible(){
        (activity as MainActivity).getBinding().bottomNavigation.visibility = View.VISIBLE
    }

}
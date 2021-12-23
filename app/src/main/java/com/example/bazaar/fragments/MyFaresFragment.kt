package com.example.bazaar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bazaar.MyApplication
import com.example.bazaar.R
import com.example.bazaar.api.model.User
import com.example.bazaar.databinding.FragmentMyFaresBinding
import com.example.bazaar.databinding.FragmentMyMarketBinding
import com.example.bazaar.manager.SharedPreferencesManager

class MyFaresFragment : Fragment() {

    private var _binding: FragmentMyFaresBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object
    {
        fun newInstance(): MyFaresFragment
        {
            return MyFaresFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("xxx", "user = " + MyApplication.sharedPreferences.getUserValue(
            SharedPreferencesManager.KEY_USER, User()
        ))


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyFaresBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow)
        binding.toolbar.setNavigationOnClickListener {
            // back button pressed
            findNavController().navigateUp()
        }

        handleToggleButtons()

        return view
    }

    private fun handleToggleButtons()
    {
        binding.ongoingOrdersTbtn.isChecked = false
        binding.ongoingSalesTbtn.isClickable = false
        binding.ongoingOrdersTbtn.isClickable = true

        binding.ongoingSalesTbtn.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                if(binding.ongoingOrdersTbtn.isChecked){

                    Log.d("PPP", "ongoingSalesTbtn")

                    binding.ongoingOrdersTbtn.isChecked = false
                    binding.ongoingSalesTbtn.isClickable = false
                    binding.ongoingOrdersTbtn.isClickable = true
                }
            }
        }

        binding.ongoingOrdersTbtn.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                if(binding.ongoingSalesTbtn.isChecked){

                    Log.d("PPP", "ongoingOrdersTbtn")

                    binding.ongoingSalesTbtn.isChecked = false
                    binding.ongoingOrdersTbtn.isClickable = false
                    binding.ongoingSalesTbtn.isClickable = true
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.app_bar_menu_market)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_settings -> {
                    // Save profile changes
                    val bundle = Bundle()
                    bundle.putString("username",  MyApplication.sharedPreferences.getUserValue(
                        SharedPreferencesManager.KEY_USER, User()
                    ).username)
                    findNavController().navigate(R.id.action_myFaresFragment_to_settingsFragment, bundle)
                    true
                }
                R.id.nav_search -> {
                    // Save profile changes
                    if (binding.searchView.visibility == View.GONE) {
                        binding.searchView.visibility = View.VISIBLE
                    } else {
                        binding.searchView.visibility = View.GONE
                    }
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
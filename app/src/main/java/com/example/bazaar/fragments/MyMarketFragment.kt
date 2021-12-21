package com.example.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bazaar.R
import com.example.bazaar.databinding.FragmentForgotPasswordBinding
import com.example.bazaar.databinding.FragmentMyMarketBinding


class MyMarketFragment : Fragment() {

    private var _binding: FragmentMyMarketBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object
    {
        fun newInstance(): MyMarketFragment
        {
            return MyMarketFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyMarketBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow)
        binding.toolbar.setNavigationOnClickListener {
            // back button pressed
            findNavController().navigate(R.id.action_myMarketFragment_to_timelineFragment)
        }
        createYourFareFABHandler()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.app_bar_menu_market)

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.nav_settings -> {
                    // Save profile changes
                    Toast.makeText(requireContext(), "Click Settings Icon.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_myMarketFragment_to_settingsFragment)
                    true
                }
                R.id.nav_search -> {
                    // Save profile changes
                    Toast.makeText(requireContext(), "Click Search Icon.", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

    }

    fun createYourFareFABHandler(){
        binding.createYourFragmentFab.setOnClickListener{
            findNavController().navigate(R.id.action_myMarketFragment_to_createYourFareFragment)
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
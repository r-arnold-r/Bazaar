package com.example.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bazaar.R
import com.example.bazaar.databinding.FragmentCreateYourFareBinding
import com.example.bazaar.databinding.FragmentSettingsBinding

class CreateYourFareFragment : Fragment() {


    private var _binding: FragmentCreateYourFareBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object
    {
        fun newInstance(): CreateYourFareFragment
        {
            return CreateYourFareFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateYourFareBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow)
        binding.toolbar.setNavigationOnClickListener {
            // back button pressed
            findNavController().navigate(R.id.action_createYourFareFragment_to_myMarketFragment)
        }

        return view
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
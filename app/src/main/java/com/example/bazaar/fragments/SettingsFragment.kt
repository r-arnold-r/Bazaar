package com.example.bazaar.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bazaar.R
import com.example.bazaar.databinding.FragmentProductDetailBinding
import com.example.bazaar.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object
    {
        fun newInstance(): SettingsFragment
        {
            return SettingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow)
        binding.toolbar.setNavigationOnClickListener {
            // back button pressed
            findNavController().navigateUp()
        }

        return view
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
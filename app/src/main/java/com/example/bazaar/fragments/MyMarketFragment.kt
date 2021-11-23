package com.example.bazaar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return view
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}
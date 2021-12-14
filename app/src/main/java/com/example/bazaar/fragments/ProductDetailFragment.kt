package com.example.bazaar.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bazaar.R
import com.example.bazaar.databinding.FragmentProductDetailBinding


class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object
    {
        fun newInstance(): ProductDetailFragment
        {
            return ProductDetailFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        //vertical
        binding.descriptionTv.movementMethod = ScrollingMovementMethod()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
        binding.toolbar.setNavigationOnClickListener {
            // back button pressed
            findNavController().navigate(R.id.action_productDetailFragment_to_timelineFragment)
        }

    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}
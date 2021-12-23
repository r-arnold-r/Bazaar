package com.example.bazaar.fragments

import android.app.Dialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bazaar.MyApplication
import com.example.bazaar.R
import com.example.bazaar.api.model.ProductResponse
import com.example.bazaar.api.model.RemoveProductResponse
import com.example.bazaar.api.model.User
import com.example.bazaar.databinding.FragmentProductDetailBinding
import com.example.bazaar.manager.SharedPreferencesManager
import com.example.bazaar.repository.Repository
import com.example.bazaar.utils.ApiString
import com.example.bazaar.viewmodels.*
import kotlinx.coroutines.launch


class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var productResponse : ProductResponse
    private lateinit var removeProductViewModel: RemoveProductViewModel

    companion object
    {
        fun newInstance(): ProductDetailFragment
        {
            return ProductDetailFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val removeProductViewModelFactory =  RemoveProductViewModelFactory(this.requireContext(), Repository())
        removeProductViewModel = ViewModelProvider(this, removeProductViewModelFactory)[RemoveProductViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        productResponse = arguments!!.getParcelable<ProductResponse>("productResponse")!!

        viewSelection()
        //vertical
        //binding.descriptionTv.movementMethod = ScrollingMovementMethod()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
        binding.toolbar.setNavigationOnClickListener {
            // back button pressed
            findNavController().navigateUp()
        }

    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    private fun viewSelection()
    {
        if (productResponse != null) {
            setNeutralElements()

            if(productResponse.username == MyApplication.sharedPreferences.getUserValue(
                            SharedPreferencesManager.KEY_USER, User()).username){
                setOwnerView()
                Log.d("SSS", productResponse.toString())
            }
            else{
                setUserView()
            }
        }
        else{
            Log.d("ProductDetailFragment", "productResponse : NULL")
        }
    }

    private fun setOwnerView()
    {
        hideUserElements()
        setOwnerElements()
        editBtnHandler()
    }

    private fun setUserView()
    {
        hideOwnerElements()
        setUserElements()
    }

    private fun hideUserElements()
    {
        binding.mailCiv.visibility = View.GONE
        binding.cartCiv.visibility = View.GONE
        binding.phoneCiv.visibility = View.GONE
        binding.availableAmountTv.visibility = View.GONE
        binding.rateTv.visibility = View.GONE
    }

    private fun setUserElements(){
        binding.availableAmountTv.text = productResponse.units + " " + productResponse.amount_type
    }

    private fun setOwnerElements(){
        binding.totalQuantityEt.text = productResponse.units + " " + productResponse.amount_type + "."
        binding.priceEt.text = productResponse.price_per_unit + " " + productResponse.price_type
        binding.soldQuantityEt.text = "0" + " " + productResponse.amount_type + "."
        binding.revenueEt.text = "0" + " " + productResponse.price_type
    }

    private fun editBtnHandler(){
        binding.editIv.setOnClickListener{
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_product_detail)
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


            val aiSc: SwitchCompat = dialog.findViewById(R.id.ai_sc) as SwitchCompat
            val modifyBtn: Button = dialog.findViewById(R.id.modify_btn) as Button
            val deleteBtn: Button = dialog.findViewById(R.id.delete_btn) as Button
            val closeBtn: Button = dialog.findViewById(R.id.close_btn) as Button

            aiSc.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    aiSc.text = "Active"
                }
                else{
                    aiSc.text = "Inactive"
                }
            }

            modifyBtn.setOnClickListener{
                dialog.dismiss()
            }

            deleteBtn.setOnClickListener{

                removeProductObservable()

                lifecycleScope.launch {
                    removeProductViewModel.removeProduct(productResponse.product_id)
                }

                dialog.dismiss()
            }

            closeBtn.setOnClickListener{
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun removeProductObservable(){
        removeProductViewModel.removeProductResponse.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_productDetailFragment_to_timelineFragment)
        }
    }

    private fun setNeutralElements()
    {
        binding.profileNameTv.text = productResponse.username
        binding.productNameTv.text = productResponse.title
        binding.productPricePerQuantityTv.text = productResponse.price_per_unit + " " + productResponse.price_type + "/" + productResponse.amount_type
        binding.descriptionTv.text = productResponse.description
        if(productResponse.is_active){
            binding.inactiveTv.text = "Active"
            binding.inactiveIm.setImageResource(R.drawable.ic_checkmark)
        }
        else{
            binding.inactiveTv.text = "Inactive"
            binding.inactiveIm.setImageResource(R.drawable.ic_inactive)
        }

    }


    private fun hideOwnerElements()
    {
        binding.totalQuantityEt.visibility = View.GONE
        binding.totalQuantityTitleEt.visibility = View.GONE
        binding.priceEt.visibility = View.GONE
        binding.priceTitleEt.visibility = View.GONE
        binding.soldQuantityEt.visibility = View.GONE
        binding.soldQuantityTitleEt.visibility = View.GONE
        binding.revenueEt.visibility = View.GONE
        binding.revenueTitleEt.visibility = View.GONE
        binding.editIv.visibility = View.GONE
    }
}
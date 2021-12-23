package com.example.bazaar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.MarginItemDecoration
import com.example.bazaar.MyApplication
import com.example.bazaar.R
import com.example.bazaar.adapter.OrderAdapter
import com.example.bazaar.adapter.ProductAdapter
import com.example.bazaar.api.model.Orders
import com.example.bazaar.api.model.ProductResponse
import com.example.bazaar.api.model.User
import com.example.bazaar.databinding.FragmentMyFaresBinding
import com.example.bazaar.databinding.FragmentMyMarketBinding
import com.example.bazaar.manager.SharedPreferencesManager
import com.example.bazaar.repository.Repository
import com.example.bazaar.utils.ApiString
import com.example.bazaar.viewmodels.*
import kotlinx.coroutines.launch

class MyFaresFragment : Fragment() , OrderAdapter.ItemClickListener{

    private var _binding: FragmentMyFaresBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var getOrderViewModel: GetOrderViewModel
    private lateinit var updateOrderViewModel: UpdateOrderViewModel

    private var recyclerViewDecorated = false
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter

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

        val getOrderViewModelFactory =  GetOrderViewModelFactory(this.requireContext(), Repository())
        getOrderViewModel = ViewModelProvider(this, getOrderViewModelFactory)[GetOrderViewModel::class.java]

        val updateOrderViewModelFactory =  UpdateOrderViewModelFactory(this.requireContext(), Repository())
        updateOrderViewModel = ViewModelProvider(this, updateOrderViewModelFactory)[UpdateOrderViewModel::class.java]
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

        updateOrderSuccessful()
        getOrdersViewModelProductsObservable(view)
        getOngoingSales()
        ordersViewModelErrorObservable()
        ordersViewModelFilterObservable()
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
                    binding.ongoingOrdersTbtn.isChecked = false
                    binding.ongoingSalesTbtn.isClickable = false
                    binding.ongoingOrdersTbtn.isClickable = true
                    recyclerView.adapter = null
                    getOngoingSales()
                }
            }
        }

        binding.ongoingOrdersTbtn.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                if(binding.ongoingSalesTbtn.isChecked){
                    binding.ongoingSalesTbtn.isChecked = false
                    binding.ongoingOrdersTbtn.isClickable = false
                    binding.ongoingSalesTbtn.isClickable = true
                    recyclerView.adapter = null
                    getOngoingOrders()
                }
            }
        }

    }

    private fun getOngoingOrders()
    {
        val mapOfFilter = mutableMapOf<String, String>()

        mapOfFilter["username"] = MyApplication.sharedPreferences.getUserValue(
                SharedPreferencesManager.KEY_USER, User()).username

        val filter = ApiString.Builder()
                .map(mapOfFilter)
                .build()

        getOrderViewModel.filter.value = filter.getString()

    }

    private fun getOngoingSales()
    {
        val mapOfFilter = mutableMapOf<String, String>()

        mapOfFilter["owner_username"] = MyApplication.sharedPreferences.getUserValue(
                SharedPreferencesManager.KEY_USER, User()).username

        val filter = ApiString.Builder()
                .map(mapOfFilter)
                .build()

        getOrderViewModel.filter.value = filter.getString()
    }

    private fun ordersViewModelFilterObservable(){
        getOrderViewModel.filter.observe(viewLifecycleOwner){
            getOrders()
        }
    }

    private fun ordersViewModelErrorObservable(){
        updateOrderViewModel.error.observe(viewLifecycleOwner){
            getOrders()
        }
    }

    private fun getOrders()
    {
        lifecycleScope.launch {
            getOrderViewModel.getOrder()
        }
    }

    private fun getOrdersViewModelProductsObservable(view: View){
        getOrderViewModel.getOrderListResponse.observe(viewLifecycleOwner){
            recycleViewAndAdapterHandler(view, getOrderViewModel.getOrderListResponse.value?.orders!!.toMutableList())
        }
    }

    private fun recycleViewAndAdapterHandler(view: View, orders: MutableList<Orders>)
    {
        //creating and setting up adapter with recyclerView
        recyclerView = binding.recyclerViewProducts

        //creating and setting up adapter with recyclerView
        orderAdapter = OrderAdapter(view, this, orders, updateOrderViewModel, this) //setting the data and listener for adapter

        val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = orderAdapter

        if(!recyclerViewDecorated){
            recyclerView.addItemDecoration(
                    MarginItemDecoration(
                            resources.getDimensionPixelSize(R.dimen.dimen_margin_horizontal_in_dp),
                            resources.getDimensionPixelSize(R.dimen.dimen_margin_vertical_in_dp)
                    )
            )
            recyclerViewDecorated = true
        }

        orderAdapter.notifyDataSetChanged()
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

    private fun updateOrderSuccessful(){
        updateOrderViewModel.updateOrderResponse.observe(viewLifecycleOwner){
            Log.d("MyFaresFragment", "updateOrderSuccessful: YUHUU")
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        recyclerViewDecorated = false
        _binding = null
    }


    override fun onItemClick(position: Int) {

    }

}
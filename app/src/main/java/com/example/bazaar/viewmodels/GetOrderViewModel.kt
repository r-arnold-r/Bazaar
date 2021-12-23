package com.example.bazaar.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bazaar.MyApplication
import com.example.bazaar.SingleLiveEvent
import com.example.bazaar.api.model.GetOrdersListResponse
import com.example.bazaar.manager.SharedPreferencesManager
import com.example.bazaar.repository.Repository

class GetOrderViewModel (val context: Context, private val repository: Repository) : ViewModel() {
    var error: MutableLiveData<String> = MutableLiveData()
    var getOrderListResponse = SingleLiveEvent<GetOrdersListResponse>()

    suspend fun getOrder() {

        val token = MyApplication.sharedPreferences.getStringValue(
                SharedPreferencesManager.KEY_TOKEN,
                "Empty token!"
        )

        try {
            getOrderListResponse.value = repository.getOrders(token!!)

        } catch (e: Exception) {

            Log.d("GetOrderViewModel", "exception: ${e.toString()}")
            error.value = e.message.toString()

        }
    }

}
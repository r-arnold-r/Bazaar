package com.example.bazaar.viewmodels

import android.content.Context
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bazaar.MyApplication
import com.example.bazaar.SingleLiveEvent
import com.example.bazaar.api.model.Product
import com.example.bazaar.api.model.ProductsListResponse
import com.example.bazaar.manager.SharedPreferencesManager
import com.example.bazaar.repository.Repository
import retrofit2.HttpException

class ProductsViewModel (val context: Context, val repository: Repository) : ViewModel() {
    val products: SingleLiveEvent<ProductsListResponse> = SingleLiveEvent()
    var error: SingleLiveEvent<String> = SingleLiveEvent()
    var success: SingleLiveEvent<Boolean> = SingleLiveEvent()

    suspend fun getProducts() {


        val token = MyApplication.sharedPreferences.getStringValue(
                SharedPreferencesManager.KEY_TOKEN,
                "Empty token!"
        )
        try {
            products.value = repository.getProducts(token.toString())
            success.value = true

            Log.d("ProductsViewModel", "token = "  + MyApplication.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN, "Empty token!"))
        }
        catch (e: Exception) {
            when(e){
                is HttpException -> {
                    if(e.code() == 302) {
                        error.value = "302"
                        Log.d("ProductsViewModel", "Token expired: ${e.toString()}")
                    }
                    if(e.code() == 301) {
                        error.value = "301"
                        Log.d("ProductsViewModel", "Invalid token: ${e.toString()}")
                    }
                }
                else -> {
                    error.value = e.message.toString()
                    Log.d("ProductsViewModel", "exception: ${e.toString()}")
                }
            }
        }

    }
}

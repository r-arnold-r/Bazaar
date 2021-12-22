package com.example.bazaar.viewmodels

import androidx.lifecycle.ViewModel
import com.example.bazaar.api.model.ProductResponse
import com.example.bazaar.api.model.ProductsListResponse
import com.example.bazaar.api.model.User

class MainActivityViewModel : ViewModel()
{
    var products: ProductsListResponse? = null
}
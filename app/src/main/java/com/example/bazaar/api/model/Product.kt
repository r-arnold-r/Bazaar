package com.example.bazaar.api.model

import android.media.Image
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

data class Products(val item_count: Int, val products : List<ProductResponse>, val timestamp: Long)

data class Product(val rating : Double, val amount_type : String, val price_type : String, val product_id : String,
                   val username : String, val is_active : Boolean, val price_per_unit : Int, val units : Int,
                   val description : String, val title : String, val images : List<String>, val creation_time : Long, val messages : List<String>)

@JsonClass(generateAdapter = true)
    data class ProductResponse (
            val rating : Double,
            val amount_type : String,
            val price_type : String,
            val product_id : String,
            val username : String,
            val is_active : Boolean,
            val price_per_unit : Int,
            val units : Int,
            val description : String,
            val title : String,
            val images : List<String>,
            val creation_time : Long,
            val messages : List<String>
    )

    @JsonClass(generateAdapter = true)
    data class ProductsListResponse(
        val item_count : Int,
        val products : List<ProductResponse>,
        val timestamp : Long
    )

    @JsonClass(generateAdapter = true)
    data class RefreshTokenResponse(
        val token: String,
        val creation_time: Long,
        val refresh_time: Long
    )

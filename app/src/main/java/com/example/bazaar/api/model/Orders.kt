package com.example.bazaar.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Orders (
        val order_id : String,
        val username : String,
        val status : String,
        val owner_username : String,
        val price_per_unit : Int,
        val units : Int,
        val description : String,
        val title : String,
        val images : List<String>,
        val creation_time : Long,
        val messages : List<String>
)

@JsonClass(generateAdapter = true)
data class GetOrdersListResponse (
        val item_count : Int,
        val orders : List<Orders>,
        val timestamp : Long
)

@JsonClass(generateAdapter = true)
data class Images (

        val _id : String,
        val image_id : String,
        val image_name : String,
        val image_path : String
)

@JsonClass(generateAdapter = true)
data class AddOrderResponse (
        val creation : String,
        val order_id : String,
        val username : String,
        val status : String,
        val price_per_unit : Int,
        val units : Int,
        val description : String,
        val title : String,
        val images : List<Images>,
        val creation_time : Int
)

@JsonClass(generateAdapter = true)
data class AddOrderRequest (
        val title : String,
        val description: String,
        val price_per_unit : Int,
        val units: Int,
        val owner_username: String,
        val revolut_link: String
)

@JsonClass(generateAdapter = true)
data class RemoveOrderResponse (
        val message : String,
        val order_id: String,
        val deletion_time : Long
)

@JsonClass(generateAdapter = true)
data class UpdateOrderRequest (
        val price_per_unit: Int,
        val status: String,
        val title : String
)

@JsonClass(generateAdapter = true)
data class UpdateOrderResponse (
        val updated_item : Updated_order_item
)

@JsonClass(generateAdapter = true)
data class Updated_order_item (
        val _id : String,
        val order_id : String,
        val username : String,
        val price_per_unit : Int,
        val units : String,
        val description : String,
        val title : String,
        val images : List<String>,
        val creation_time : Int,
        val _v : Int,
        val status: String
)
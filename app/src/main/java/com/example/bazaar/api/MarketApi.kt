package com.example.bazaar.api

import com.example.bazaar.api.model.*
import com.example.bazaar.utils.Constants
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST(Constants.RESETPASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header(Constants.HEADER_TOKEN) token: String,
                            @Header(Constants.HEADER_FILTER) filter: String,
                            @Header(Constants.HEADER_SORT) sort: String,
                            @Header(Constants.HEADER_LIMIT) limit: Int): ProductsListResponse

    @GET(Constants.REFRESH_TOKEN_URL)
    suspend fun refreshToken(@Header(Constants.HEADER_TOKEN) token: String): RefreshTokenResponse

    @Multipart
    @POST(Constants.ADD_PRODUCT_URL)
    suspend fun addProduct(@Header(Constants.HEADER_TOKEN) token: String,
                           @Part("title") title: String,
                           @Part("description") description: String,
                           @Part("price_per_unit") price_per_unit: String,
                           @Part("units") units: String,
                           @Part("is_active") is_active: Boolean,
                           @Part("rating") rating: Double,
                           @Part("amount_type") amount_type: String,
                           @Part("price_type") price_type: String,): AddProductResponse
}
package com.example.bazaar.api

import com.example.bazaar.api.model.*
import com.example.bazaar.utils.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST(Constants.RESETPASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header(Constants.HEADER_TOKEN) token: String): ProductsListResponse

    @GET(Constants.REFRESH_TOKEN_URL)
    suspend fun refreshToken(@Header(Constants.HEADER_TOKEN) token: String): RefreshTokenResponse
}
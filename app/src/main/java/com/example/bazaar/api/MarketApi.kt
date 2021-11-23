package com.example.bazaar.api

import com.example.bazaar.api.model.*
import com.example.bazaar.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST(Constants.RESETPASSWORD_URL)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): ResetPasswordResponse
}
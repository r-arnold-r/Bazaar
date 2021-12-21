package com.example.bazaar.repository

import com.example.bazaar.api.RetrofitInstance
import com.example.bazaar.api.model.*
import retrofit2.Retrofit

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return RetrofitInstance.api.register(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse {
        return RetrofitInstance.api.resetPassword(request)
    }

    suspend fun getProducts(token: String): ProductsListResponse {
        return RetrofitInstance.api.getProducts(token)
    }

    suspend fun refreshToken(token: String): RefreshTokenResponse {
        return RetrofitInstance.api.refreshToken(token)
    }

    suspend fun addProduct(token: String, request: AddProductRequest) : AddProductResponse {
        return RetrofitInstance.api.addProduct(token, request.title, request.description, request.price_per_unit.toString(), request.units, request.is_active, request.rating.toDouble(), request.amount_type, request.price_type )
    }
}
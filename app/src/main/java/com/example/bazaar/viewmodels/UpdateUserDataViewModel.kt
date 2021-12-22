package com.example.bazaar.viewmodels

import com.example.bazaar.api.model.*

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bazaar.MyApplication
import com.example.bazaar.SingleLiveEvent
import com.example.bazaar.manager.SharedPreferencesManager
import com.example.bazaar.repository.Repository

class UpdateUserDataViewModel (val context: Context, private val repository: Repository) : ViewModel() {
    var error: MutableLiveData<String> = MutableLiveData()
    var updateUserDataListResponse = SingleLiveEvent<UpdateUserDataListResponse>()
    var updateUserDataRequest = SingleLiveEvent<UpdateUserDataRequest>()

    init{
        updateUserDataRequest.value = UpdateUserDataRequest("",0)
    }

    suspend fun updateUserData() {

        val request =
                UpdateUserDataRequest(
                        username = updateUserDataRequest.value!!.username,
                        phone_number = updateUserDataRequest.value!!.phone_number)

        try {

            val token = MyApplication.sharedPreferences.getStringValue(
                    SharedPreferencesManager.KEY_TOKEN,
                    "Empty token!"
            )

            updateUserDataListResponse.value = repository.updateUserData(token!!, request)

        } catch (e: Exception) {

            Log.d("UpdateUserDataViewModel", "exception: ${e.toString()}")
            error.value = e.message.toString()

        }
    }

}
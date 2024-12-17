package com.example.tdcat.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tdcat.catModel.CatResponse
import com.example.tdcat.networking.ApiConfig
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel(): ViewModel() {
    private val _catData = MutableLiveData<List<CatResponse>>()
    val catData: LiveData<List<CatResponse>> get() = _catData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    fun getCatData() {
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getCat()
        client.enqueue(object: Callback<List<CatResponse>> {
            override fun onResponse(
                call: Call<List<CatResponse>>,
                response: Response<List<CatResponse>>
            ) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                _isLoading.value = false
                _catData.postValue(responseBody)
            }

            override fun onFailure(
                call: Call<List<CatResponse>>,
                t: Throwable
            ) {
                onError(t.message)
                t.printStackTrace()
            }
        })
    }

    private fun onError(inputMessage: String?) {
        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error" else inputMessage
        errorMessage = StringBuilder("ERROR:").append("$message some data may not displayed properly").toString()

        _isError.value = true
        _isLoading.value = false
    }
}
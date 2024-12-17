package com.example.tdcat.networking

import com.example.tdcat.catModel.CatResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("images/search")
    fun getCat(): Call<List<CatResponse>>
}
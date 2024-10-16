package com.example.m203_listview_retrofit_sansadapter_3

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class Car(
    val id: Int,
    val name: String,
    val price: Double,
    val isFullOptions: Boolean,
    val image: String
)


interface ApiService {
    @GET("CarAPI/readAll.php")
    fun getCars(): Call<List<Car>>
}
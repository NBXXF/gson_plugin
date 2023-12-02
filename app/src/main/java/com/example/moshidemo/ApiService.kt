package com.example.moshidemo

import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    // I am normally using rxjava to handle remote requests, hence `Single`; change it to your way if needed
    @GET("/")
    fun getInfoObjects(): Single<List<InfoObject>>
}
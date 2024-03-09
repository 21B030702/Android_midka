package com.example.aviatickets.model.network

import com.example.aviatickets.model.entity.Offer
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface OfferApiService {
    @GET("offers_endpoint")
    suspend fun getOffers(): Response<List<Offer>>
}

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://my-json-server.typicode.com/estharossa/fake-api-demo/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val offerApiService: OfferApiService = retrofit.create(OfferApiService::class.java)
}

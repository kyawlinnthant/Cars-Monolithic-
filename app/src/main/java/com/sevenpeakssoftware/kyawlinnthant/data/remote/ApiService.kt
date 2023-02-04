package com.sevenpeakssoftware.kyawlinnthant.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val CAR_LIST = "carlist"
    }

    @GET(CAR_LIST)
    suspend fun fetchCars(): Response<CarsDto>
}
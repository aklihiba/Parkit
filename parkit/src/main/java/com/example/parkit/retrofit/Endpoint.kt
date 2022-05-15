package com.example.parkit.retrofit


import com.example.parkit.entity.Parking
import com.example.parkit.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Endpoint {

    @GET("parkings/getall")
    suspend fun getAllParkings(): Response<List<Parking>>


    companion object {
        @Volatile
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                synchronized(this) {
                   // TODO("mettre l'url publique du server")
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint::class.java)
                }
            }
            return endpoint!!

        }


    }

}

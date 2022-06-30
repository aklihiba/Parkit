package com.example.parkit.retrofit


import com.example.parkit.entity.Parking
import com.example.parkit.entity.Recherche
import com.example.parkit.entity.User
import com.example.parkit.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {

    @GET("Parkings")
    suspend fun getAllParkings(): Response<List<Parking>>

   @POST("Utilisateur/connexion/")
   suspend fun connexion(@Body user: User): Response<String>
   @POST("Utilisateur/")
   suspend fun inscription(@Body user:User): Response<String>

   @GET("Utilisateur/{pk}")
   suspend fun getUser(@Path("pk") pk:Int):Response<User>

   @POST("Parkings/Recherche/")
   suspend fun getNearbyParks(@Body recherche: Recherche) : Response<List<Parking>>

    companion object {
        @Volatile
        var endpoint: Endpoint? = null
        fun createEndpoint(): Endpoint {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint::class.java)
                }
            }
            return endpoint!!

        }


    }

}

package com.example.parkit.retrofit


import com.example.parkit.entity.Parking
import com.example.parkit.entity.User
import com.example.parkit.entity.rating
import com.example.parkit.url
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface Endpoint {

    @GET("Parkings")
    suspend fun getAllParkings(): Response<List<Parking>>

   @POST("Utilisateur/connexion/")
   suspend fun connexion(@Body user: User): Response<String>
   @POST("Utilisateur/")
   suspend fun inscription(@Body user:User): Response<String>

   @GET("Utilisateur/{pk}")
   suspend fun getUser(@Path("pk") pk:Int):Response<User>

   @PUT("Utilisateur/{pk}/")
   suspend fun updateProfil(@Path("pk") pk:Int, @Body profil:User):Response<User>

   @POST("Rating/rateParking/")
   suspend fun rateParking(@Body rating:rating): Response<String>

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

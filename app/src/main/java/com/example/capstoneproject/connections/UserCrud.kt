package com.example.capstoneproject.connections


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserCrud {

    @GET("/users/user")
    suspend fun getUser(
        @Query("userName") username: String,
        @Query("password") password: String
    ):Response<Int>

//    @GET("/users")
//    suspend fun getAllUsers(): Response<Users>
    @POST("/users")
    suspend fun createUser(@Body app : Users): Response<Users>

}
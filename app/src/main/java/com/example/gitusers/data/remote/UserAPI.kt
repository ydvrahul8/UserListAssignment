package com.example.gitusers.data.remote

import com.example.gitusers.data.model.UserListItem
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAPI {

    @GET("users")
    suspend fun getUserList(@Query("page") page: Int, @Query("per_page") perPage: Int): List<UserListItem>
}
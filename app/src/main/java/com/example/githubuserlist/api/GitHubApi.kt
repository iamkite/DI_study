package com.example.githubuserlist.api

import com.example.githubuserlist.model.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface GitHubApi {

    @GET("/users")
    fun getUserList(
        @Header("accept") accept: String,
        @Header("authorization") authorization: String?,
        @Query("since") since: Long?,
        @Query("per_page") perPage: Int?
    ): Single<Response<List<User>>>

}

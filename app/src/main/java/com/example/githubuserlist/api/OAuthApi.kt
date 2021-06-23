package com.example.githubuserlist.api

import com.example.githubuserlist.model.AccessToken
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface OAuthApi {
    @FormUrlEncoded
    @POST("login/oauth/access_token")
    fun getAccessToken(
        @Header("accept") accept: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Single<Response<AccessToken>>
}

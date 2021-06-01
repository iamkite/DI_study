package com.example.githubuserlist.api

import com.example.githubuserlist.Constants
import com.example.githubuserlist.model.User
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
class GitHubApiClient(private val api: GitHubApi) {

    //lateinit var api : GitHubApi

    fun getUserList(since: Long, perPage: Int) : Single<Response<List<User>>> {
        return api.getUserList(Constants.userHeader, since, perPage)
    }

    companion object {
        fun create(): GitHubApiClient {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return GitHubApiClient(retrofit.create(GitHubApi::class.java))
        }
    }
}

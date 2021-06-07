package com.example.githubuserlist.api

import com.example.githubuserlist.Constants
import com.example.githubuserlist.model.User
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubApiClient @Inject constructor(private val api: GitHubApi) {

    fun getUserList(since: Long, perPage: Int) : Single<Response<List<User>>> {
        return api.getUserList(Constants.userHeader, since, perPage)
    }
}

package com.example.githubuserlist.api

import com.example.githubuserlist.Constants
import com.example.githubuserlist.OAuth
import com.example.githubuserlist.model.AccessToken
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OAuthApiClient @Inject constructor(private val api: OAuthApi) {
    fun getAccessToken(code: String): Single<Response<AccessToken>> {
        return api.getAccessToken(Constants.oAuthHeader, OAuth.clientId, OAuth.clientSecrets, code)
    }
}

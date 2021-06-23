package com.example.githubuserlist

object Constants {
    const val gitHubBaseUrl = "https://api.github.com/"
    const val oAuthBaseUrl = "https://github.com/"

    const val user = "User"
    const val gitHub = "github"
    const val oAuth = "oauth"

    const val userHeader = "application/vnd.github.v3+json"
    const val oAuthHeader = "application/json"

    fun oAuthUrl(id: String) =
        "https://github.com/login/oauth/authorize?client_id=$id&redirect_uri=dagger2.study://android"
}

package com.example.githubuserlist.dagger

import com.example.githubuserlist.Constants
import com.example.githubuserlist.api.GitHubApi
import com.example.githubuserlist.api.OAuthApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Named(Constants.gitHub)
    @Singleton
    fun provideGitHubRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.gitHubBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Named(Constants.oAuth)
    @Singleton
    fun provideOAuthRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.oAuthBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubApi(@Named(Constants.gitHub) retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)

    @Provides
    @Singleton
    fun provideOAuthApi(@Named(Constants.oAuth) retrofit: Retrofit): OAuthApi = retrofit.create(OAuthApi::class.java)
}

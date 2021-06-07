package com.example.githubuserlist.dagger

import com.example.githubuserlist.Constants
import com.example.githubuserlist.api.GitHubApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)

}

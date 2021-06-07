package com.example.githubuserlist.dagger

import android.content.Context
import com.example.githubuserlist.userlist.UserListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(fragment: UserListFragment)
}

package com.example.githubuserlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githubuserlist.api.GitHubApiClient
import com.example.githubuserlist.api.OAuthApiClient
import com.example.githubuserlist.model.AccessToken
import com.example.githubuserlist.model.User
import com.example.githubuserlist.userlist.UserListViewModel
import com.example.githubuserlist.util.SchedulerProvider
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var gitHubApiClient: GitHubApiClient

    @Mock
    lateinit var oAuthApiClient: OAuthApiClient

    @InjectMocks
    lateinit var userListViewModel: UserListViewModel

    @Mock
    lateinit var schedulerProvider: SchedulerProvider

    private val userList = listOf<User>()
    private val accessToken = AccessToken("","","")

    @Before
    fun initMocks() {
        userListViewModel = UserListViewModel(gitHubApiClient, oAuthApiClient, schedulerProvider)
    }

    @Test
    fun request_user_list_success_empty() {
        `when`(gitHubApiClient.getUserList(anyLong(), anyInt(), anyString()))
            .thenReturn(Single.just(Response.success(userList)))
        `when`(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        `when`(schedulerProvider.main()).thenReturn(Schedulers.trampoline())

        userListViewModel.requestUserList("")
        Assert.assertEquals(userListViewModel.userList.getOrAwaitValue(), userList)
    }

    @Test
    fun request_access_token_success() {
        `when`(oAuthApiClient.getAccessToken(anyString()))
            .thenReturn(Single.just(Response.success(accessToken)))
        `when`(schedulerProvider.io()).thenReturn(Schedulers.trampoline())
        `when`(schedulerProvider.main()).thenReturn(Schedulers.trampoline())

        userListViewModel.requestAccessToken("")
        Assert.assertEquals(userListViewModel.accessToken.getOrAwaitValue(), accessToken)
    }
}

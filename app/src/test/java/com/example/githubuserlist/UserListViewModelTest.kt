package com.example.githubuserlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githubuserlist.api.GitHubApiClient
import com.example.githubuserlist.api.OAuthApiClient
import com.example.githubuserlist.model.AccessToken
import com.example.githubuserlist.model.User
import com.example.githubuserlist.userlist.UserListViewModel
import io.reactivex.Single
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
//
//    @get:Rule
//    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var gitHubApiClient: GitHubApiClient

    @Mock
    lateinit var oAuthApiClient: OAuthApiClient

    @InjectMocks
    lateinit var userListViewModel: UserListViewModel

    val userList = listOf<User>()
    val accessToken = AccessToken("","","")

    @Before
    fun initMocks() {
        userListViewModel = UserListViewModel(gitHubApiClient, oAuthApiClient)
    }

    @Test
    fun request_user_list_success_empty() {
        `when`(gitHubApiClient.getUserList(anyLong(), anyInt(), anyString()))
            .thenReturn(Single.just(Response.success(userList)))
        userListViewModel.requestUserList("")
        Assert.assertEquals(userListViewModel.userList.getOrAwaitValue(), userList)
    }

    @Test
    fun request_access_token_success() {
        `when`(oAuthApiClient.getAccessToken(anyString()))
            .thenReturn(Single.just(Response.success(accessToken)))
        Assert.assertEquals(userListViewModel.accessToken.getOrAwaitValue(), accessToken)
    }
}

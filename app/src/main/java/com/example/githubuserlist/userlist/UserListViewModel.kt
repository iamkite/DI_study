package com.example.githubuserlist.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserlist.Event
import com.example.githubuserlist.api.GitHubApiClient
import com.example.githubuserlist.api.OAuthApiClient
import com.example.githubuserlist.model.AccessToken
import com.example.githubuserlist.model.User
import com.example.githubuserlist.reactive.DisposeBag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserListViewModel(
    private val gitHubApiClient: GitHubApiClient,
    private val oAuthApiClient: OAuthApiClient
) : ViewModel() {

    private val disposeBag = DisposeBag()

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    private val _accessToken = MutableLiveData<AccessToken>()
    val accessToken: LiveData<AccessToken> get() = _accessToken

    private val _showError = MutableLiveData<Event<Boolean>>()
    val showError: LiveData<Event<Boolean>> get() = _showError

    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }

    fun requestUserList(token: String?) {
        disposeBag.add(
            gitHubApiClient.getUserList(0, 30, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    if (!it.isSuccessful || it.body() == null) {
                        _showError.value = Event(true)
                        return@doOnSuccess
                    }

                    _userList.value = it.body()
                }
                .doOnError {
                    _showError.value = Event(true)
                }
                .subscribe()
        )
    }

    fun requestAccessToken(
        code: String
    ) {
        disposeBag.add(
            oAuthApiClient.getAccessToken(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    if (!it.isSuccessful || it.body() == null) {
                        _showError.value = Event(true)
                        return@doOnSuccess
                    }

                    _accessToken.value = it.body()
                }
                .doOnError {
                    _showError.value = Event(true)
                }
                .subscribe()
        )
    }

    class Factory @Inject constructor(
        private val gitHubApiClient: GitHubApiClient,
        private val oAuthApiClient: OAuthApiClient
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserListViewModel(gitHubApiClient, oAuthApiClient) as T
        }
    }

}

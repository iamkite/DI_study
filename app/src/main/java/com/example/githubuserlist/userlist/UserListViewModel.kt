package com.example.githubuserlist.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserlist.Event
import com.example.githubuserlist.api.GitHubApiClient
import com.example.githubuserlist.model.User
import com.example.githubuserlist.reactive.DisposeBag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserListViewModel(private val apiClient: GitHubApiClient) : ViewModel() {

    private val disposeBag = DisposeBag()

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    private val _showError = MutableLiveData<Event<Boolean>>()
    val showError: LiveData<Event<Boolean>> get() = _showError

    init {
        requestUserList()
    }

    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }

    private fun requestUserList() {
        disposeBag.add(
            apiClient.getUserList(0, 30)
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

    class Factory @Inject constructor(
        private val apiClient: GitHubApiClient
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserListViewModel(apiClient) as T
        }
    }

}

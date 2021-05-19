package com.example.runningapp.presentation.users

import androidx.lifecycle.*
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.usecases.UserUseCase
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UsersViewModel(
    private val userUseCase: UserUseCase,
    private val coroutineContext: CoroutineContext
) : ViewModel() {
    private var users: LiveData<List<UserItem>>? = null
    private var usersData: MutableLiveData<List<UserItem>> = MutableLiveData()
    private var predicate: (UserItem) -> Boolean = {
        true
    }

    fun getUsers(): LiveData<List<UserItem>> {
        return usersData
    }

    fun getAllUsers() {
        predicate = {
            true
        }
        updateValue()
    }

    fun getSubscribedUsers() {
        predicate = {
            it.isSubscribed
        }
        updateValue()
    }

    private fun updateValue() {
        users?.value?.filter(predicate)?.let {
            usersData.postValue(it)
        }
    }

    init {
        refreshUsers()
        observe()
    }

    fun subscribeClick(userId: String) {
        viewModelScope.launch(coroutineContext) {
            userUseCase.subscribe(userId)
        }
    }

    private fun observe() {
        users = userUseCase.getAllUsers().map { list -> list.map(::mapUserToUserItem) }.apply {
            observeForever {
                usersData.postValue(it.filter(predicate))
            }
        }
    }

    fun unsubscribeClick(userId: String) {
        viewModelScope.launch(coroutineContext) {
            userUseCase.unsubscribe(userId)
        }
    }

    fun refreshUsers() {
        viewModelScope.launch(coroutineContext) {
            userUseCase.refreshUsers()
        }
    }

    private fun mapUserToUserItem(user: User): UserItem {
        if (user.id == null) throw IllegalStateException("Id must be not null")
        if (user.name == null) throw IllegalStateException("Name must be not null")
        return UserItem(
            id = user.id!!,
            name = user.name!!,
            isSubscribed = user.isSubscribed ?: false
        )
    }

}
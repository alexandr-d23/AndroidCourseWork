package com.example.runningapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningapp.domain.model.Sprint
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.usecases.RunUseCase
import com.example.runningapp.domain.usecases.UserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val runUseCase: RunUseCase,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    private val userLiveData: MutableLiveData<User?> = MutableLiveData()

    private val sprints: MutableLiveData<List<Sprint>> = MutableLiveData()
    private val sprintsError: MutableLiveData<Throwable> = MutableLiveData()

    suspend fun getSprints(): LiveData<List<Sprint>> = runUseCase.getAuthSprints()

    fun getError(): LiveData<Throwable> = sprintsError


    fun signOut() {
        viewModelScope.launch(coroutineContext) {
            userUseCase.signOut()
            getCurrentUser()
        }
    }

    fun getCurrentUser(): LiveData<User?> {
        viewModelScope.launch(coroutineContext) {
            userLiveData.postValue(userUseCase.getCurrentUser())
        }
        return userLiveData
    }

    fun updateName(name: String) {
        viewModelScope.launch(coroutineContext) {
            userUseCase.updateUsername(name)
        }
    }
}
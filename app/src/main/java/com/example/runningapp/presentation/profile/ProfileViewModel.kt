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
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(
    private val userUseCase: UserUseCase,
    private val runUseCase: RunUseCase,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    private val userLiveData: MutableLiveData<User?> = MutableLiveData()

    private val sprints: MutableLiveData<List<Sprint>> = MutableLiveData()
    private val sprintsError: MutableLiveData<Throwable> = MutableLiveData()

    init {
        refreshSprints()
    }

    fun getSprints(): LiveData<List<Sprint>> = sprints

    fun getError(): LiveData<Throwable> = sprintsError


    fun refreshSprints(){
        viewModelScope.launch(coroutineContext){
            try{
                sprints.postValue(runUseCase.getAuthSprints())
            }catch (e: Exception){
                sprintsError.postValue(e)
            }
        }
    }

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
package com.example.runningapp.presentation.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.usecases.UserUseCase
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignUpViewModel(
    private val userUseCase: UserUseCase,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    private val userLiveData: MutableLiveData<User?> = MutableLiveData()

    private val errorSignUpLiveData: MutableLiveData<Throwable> = MutableLiveData()
    fun getSignUpErrorLiveData(): LiveData<Throwable> = errorSignUpLiveData

    fun signUp(email: String, name: String, password: String) {
        viewModelScope.launch(coroutineContext) {
            try {
                val user = userUseCase.signUp(User(null, email, name, password))
                Log.d("MYTAG", "UserViewModel signUp() : ${user}")
                user?.let {
                    userLiveData.postValue(it)
                } ?: errorSignUpLiveData.postValue(IllegalStateException("Не вышло"))
                //TODO EXCEPTION EXT
                //errorLiveData.postValue(Throwable("User not found"))
            } catch (e: Exception) {
                e.printStackTrace()
                errorSignUpLiveData.postValue(e)
            }
        }
    }

    fun getCurrentUser(): LiveData<User?> {
        viewModelScope.launch(coroutineContext) {
            try {
                userLiveData.postValue(userUseCase.getCurrentUser())
            } catch (e: Exception) {
                errorSignUpLiveData.postValue(e)
            }
        }
        return userLiveData
    }
}
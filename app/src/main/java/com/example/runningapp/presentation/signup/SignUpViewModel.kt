package com.example.runningapp.presentation.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningapp.data.room.entities.User
import com.example.runningapp.domain.usecases.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalStateException

class SignUpViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {

    //TODO DEPENDENCY INJECTION
    private val coroutineContext = SupervisorJob() + Dispatchers.IO

    private val userLiveData: MutableLiveData<User?> = MutableLiveData()
    private val errorSignInLiveData: MutableLiveData<Throwable> = MutableLiveData()
    fun getSignInErrorLiveData(): LiveData<Throwable> = errorSignInLiveData

    private val errorSignUpLiveData: MutableLiveData<Throwable> = MutableLiveData()
    fun getSignUpErrorLiveData(): LiveData<Throwable> = errorSignUpLiveData

    fun signUp(email: String, name: String, password: String) {
        viewModelScope.launch(coroutineContext) {
            try {
                val user = userUseCase.signUp(createUser(email, password, name))
                Log.d("MYTAG", "UserViewModel signUp() : ${user}")
                user?.let {
                    userLiveData.postValue(it)
                } ?: errorSignUpLiveData.postValue(IllegalStateException("Не вышло"))
                //TODO EXCEPTION EXT
                //errorLiveData.postValue(Throwable("User not found"))
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorSignUpLiveData.postValue(e)
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch(coroutineContext) {
            try {
                val user = createUser(email, password)
                Log.d("MYTAG", "UserViewModel signUp() : ${user}")
                user?.let {
                    userLiveData.postValue(it)
                }
                    ?: errorSignInLiveData.postValue(IllegalStateException("Incorrect login or password"))
                //TODO EXCEPTION EXT
                //errorLiveData.postValue(Throwable("User not found"))
            } catch (e: Exception) {
                e.printStackTrace()
                errorSignInLiveData.postValue(e)
            }
        }
    }

    private fun createUser(email: String, password: String, name: String = ""): User {
        return User(
            email = email,
            password = password,
            name = name
        )
    }

    fun getCurrentUser(): LiveData<User?> {
        viewModelScope.launch(coroutineContext) {
            userLiveData.postValue(userUseCase.getCurrentUser())
        }
        return userLiveData
    }

    fun signOut() {
        viewModelScope.launch(coroutineContext) {
            userUseCase.signOut()
            getCurrentUser()
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch(coroutineContext) {
            userUseCase.updateUsername(name)
        }
    }
}
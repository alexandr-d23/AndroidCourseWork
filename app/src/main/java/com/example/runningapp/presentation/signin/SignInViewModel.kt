package com.example.runningapp.presentation.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runningapp.domain.model.User
import com.example.runningapp.domain.usecases.UserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SignInViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val coroutineContext: CoroutineContext
) : ViewModel() {

    private val userLiveData: MutableLiveData<User?> = MutableLiveData()
    private val errorSignInLiveData: MutableLiveData<Throwable> = MutableLiveData()
    fun getSignInErrorLiveData(): LiveData<Throwable> = errorSignInLiveData

    fun signIn(email: String, password: String) {
        viewModelScope.launch(coroutineContext) {
            try {
                val user = userUseCase.signIn(
                    User(
                        email = email,
                        password = password
                    )
                )
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

    fun getCurrentUser(): LiveData<User?> {
        viewModelScope.launch(coroutineContext) {
            try {
                userLiveData.postValue(userUseCase.getCurrentUser())
            } catch (e: Exception) {
                errorSignInLiveData.postValue(e)
            }
        }
        return userLiveData
    }
}
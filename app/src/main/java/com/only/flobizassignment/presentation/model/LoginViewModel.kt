package com.only.flobizassignment.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.only.flobizassignment.domain.GoogleLoginHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: GoogleLoginHandler
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun signInWithGoogle() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = authRepository.signInWithGoogle()
            _loginState.value = if (result.isSuccess) {
                LoginState.Success(result.getOrNull())
            } else {
                LoginState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val user: FirebaseUser?) : LoginState()
    data class Error(val message: String) : LoginState()
}
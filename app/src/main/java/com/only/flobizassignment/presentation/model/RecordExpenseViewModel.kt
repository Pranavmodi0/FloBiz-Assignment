package com.only.flobizassignment.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.only.flobizassignment.data.Expense
import com.only.flobizassignment.data.usecase.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val authRepository: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecordExpenseUiState>(RecordExpenseUiState.Idle)
    val uiState: StateFlow<RecordExpenseUiState> = _uiState

    fun addExpense(expense: Expense) {
        val userId = authRepository.currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                _uiState.value = RecordExpenseUiState.Loading
                val result = addExpenseUseCase.execute(expense)
                _uiState.value = if (result) RecordExpenseUiState.Success else RecordExpenseUiState.Error
            }
        } else {
            _uiState.value = RecordExpenseUiState.Error
        }
    }
}

sealed class RecordExpenseUiState {
    data object Idle : RecordExpenseUiState()
    data object Loading : RecordExpenseUiState()
    data object Success : RecordExpenseUiState()
    data object Error : RecordExpenseUiState()
}

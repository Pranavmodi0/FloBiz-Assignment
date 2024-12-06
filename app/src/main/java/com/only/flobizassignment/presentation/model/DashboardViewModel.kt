package com.only.flobizassignment.presentation.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.only.flobizassignment.data.Expenses
import com.only.flobizassignment.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expenses>>(emptyList())
    val expenses: StateFlow<List<Expenses>> get() = _expenses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun fetchExpenses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _expenses.value = repository.getUserExpenses()
            } catch (e: Exception) {
                _expenses.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteExpense(expense: Expenses) {
        viewModelScope.launch {
            firestore.collection("expenses")
                .document(expense.id)
                .delete()
                .addOnSuccessListener {
                    _expenses.value = _expenses.value - expense
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", "Error deleting document", e)
                }
        }
    }
}

package com.only.flobizassignment.presentation.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.only.flobizassignment.data.Expenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun updateExpense(expenseId: String, updatedExpense: Expenses, onComplete: () -> Unit) {
        _isLoading.value = true

        firestore.collection("expenses")
            .document(expenseId)
            .set(updatedExpense)
            .addOnSuccessListener {
                _isLoading.value = false
                onComplete()
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("FirestoreError", "Error updating document", e)
            }
    }

    fun deleteExpense(expenseId: String, onComplete: () -> Unit) {
        _isLoading.value = true

        firestore.collection("expenses")
            .document(expenseId)
            .delete()
            .addOnSuccessListener {
                _isLoading.value = false
                onComplete()
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                Log.e("FirestoreError", "Error deleting document", e)
            }
    }
}
package com.only.flobizassignment.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.only.flobizassignment.data.Expense
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun addExpense(expense: Expense): Boolean {
        return try {
            firestore.collection("expenses")
                .add(expense.toMap())
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

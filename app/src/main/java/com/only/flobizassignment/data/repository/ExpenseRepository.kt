package com.only.flobizassignment.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.only.flobizassignment.data.Expenses
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    suspend fun getUserExpenses(): List<Expenses> {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        val snapshot = firestore.collection("expenses").whereEqualTo("uid",userId).get().await()
        return snapshot.documents.map { document ->
            document.toObject(Expenses::class.java)?.copy(id = document.id) ?: Expenses()
        }
    }
}

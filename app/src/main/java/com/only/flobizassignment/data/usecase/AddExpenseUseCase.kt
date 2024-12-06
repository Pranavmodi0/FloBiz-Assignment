package com.only.flobizassignment.data.usecase

import com.only.flobizassignment.data.Expense
import com.only.flobizassignment.data.repository.FirestoreRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {
    suspend fun execute(expense: Expense): Boolean {
        return firestoreRepository.addExpense(expense)
    }
}

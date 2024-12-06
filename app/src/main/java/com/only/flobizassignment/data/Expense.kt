package com.only.flobizassignment.data

data class Expense(
    val date: String,
    val description: String,
    val amount: Double,
    val type: String,
    val uid: String
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "date" to date,
            "description" to description,
            "amount" to amount,
            "type" to type,
            "uid" to uid
        )
    }
}

data class Expenses(
    val id: String = "",
    val date: String= "",
    val description: String= "",
    val amount: Double = 0.0,
    val type: String= "",
    val uid: String= ""
)

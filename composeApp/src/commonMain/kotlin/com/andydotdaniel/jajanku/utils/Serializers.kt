package com.andydotdaniel.jajanku.utils

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
private data class ExpenseTypeTitle(val en: String, val id: String)

fun parseSerializableExpenseTypeTitle(titles: String): String {
    return Json.decodeFromString<ExpenseTypeTitle>(titles).en
}





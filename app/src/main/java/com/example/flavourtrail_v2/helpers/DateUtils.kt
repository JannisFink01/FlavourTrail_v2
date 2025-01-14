package com.example.flavourtrail_v2.helpers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    fun parseDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
}
package com.example.flavourtrail_v2.data

import androidx.room.TypeConverter
import java.util.Date

/**
 * A converter class to handle the conversion between `Date` and `Long` for Room database.
 */
class DateConverter {

    /**
     * Converts a timestamp value to a `Date` object.
     *
     * @param value The timestamp value in milliseconds.
     * @return The corresponding `Date` object, or `null` if the value is `null`.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Converts a `Date` object to a timestamp value.
     *
     * @param date The `Date` object to be converted.
     * @return The corresponding timestamp value in milliseconds, or `null` if the date is `null`.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
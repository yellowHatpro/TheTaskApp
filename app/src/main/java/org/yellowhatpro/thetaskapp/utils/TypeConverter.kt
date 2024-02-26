package org.yellowhatpro.thetaskapp.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime

class TypeConverter {
    @TypeConverter
    fun fromTimestamp(value: String?) = value?.let {
        LocalDateTime.parse(it)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?) = date?.toString()
}
package com.example.runningapp.data.room

import androidx.room.TypeConverter
import org.joda.time.DateTime

class DateTimeConverter {

    @TypeConverter
    fun fromDateTime(dateTime: DateTime): Long = dateTime.millis

    @TypeConverter
    fun toDateTime(timestamp: Long) : DateTime = DateTime(timestamp)
}
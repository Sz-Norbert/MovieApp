package com.nika.movieapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MovieTypeConverter {

    @TypeConverter
    fun fromListofIntToString(atribute: List<Int>?): String? {
        return atribute?.joinToString(",")
    }

    @TypeConverter
    fun stringToIntList(Atribute: String): List<Int> =
        Atribute.split(",").filter { it.toIntOrNull() != null }
            .map { it.toInt() }

}
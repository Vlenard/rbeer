package com.anonymous.data

import androidx.room.TypeConverter

class BeerTypeConverter {

    @TypeConverter
    fun fromBeerType(type: BeerType): String {
        return type.name
    }

    @TypeConverter
    fun toBeerType(value: String): BeerType {
        return BeerType.valueOf(value)
    }
}
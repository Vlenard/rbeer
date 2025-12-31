package com.anonymous.data

import androidx.room.ColumnInfo

data class BeerTypeCount(
    @ColumnInfo(name = "type")
    val type: BeerType,

    @ColumnInfo(name = "count")
    val count: Int
)

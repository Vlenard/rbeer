package com.anonymous.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "beers",
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class Beer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val rating: Int,   // 1–5 (validálható ViewModelben vagy DAO-ban)

    val note: String,

    val type: BeerType
)

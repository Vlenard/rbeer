package com.anonymous.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beers")
data class Beer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val rating: Int,   // 1–5 (validálható ViewModelben vagy DAO-ban)

    val note: String,

    val type: BeerType
)

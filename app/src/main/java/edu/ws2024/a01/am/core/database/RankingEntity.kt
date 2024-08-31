package edu.ws2024.a01.am.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RankingEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,

    @ColumnInfo("name") val name: String,
    @ColumnInfo("secondsTime") val secondsTime: Int,
    @ColumnInfo("score") val score: Int
)

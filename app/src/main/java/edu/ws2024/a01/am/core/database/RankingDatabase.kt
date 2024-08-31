package edu.ws2024.a01.am.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RankingEntity::class], version = 1)
abstract class RankingDatabase : RoomDatabase() {

    abstract fun getRankingDao(): RankingDao

}
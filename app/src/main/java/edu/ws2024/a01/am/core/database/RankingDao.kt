package edu.ws2024.a01.am.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RankingDao {

    @Query("SELECT * FROM RankingEntity")
    fun getAllRankings(): Flow<List<RankingEntity>>

    @Insert
    suspend fun insertRanking(rankingEntity: RankingEntity)

}
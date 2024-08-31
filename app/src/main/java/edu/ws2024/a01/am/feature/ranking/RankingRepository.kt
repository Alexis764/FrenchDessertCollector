package edu.ws2024.a01.am.feature.ranking

import edu.ws2024.a01.am.core.database.RankingDao
import edu.ws2024.a01.am.core.database.RankingEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RankingRepository @Inject constructor(
    private val rankingDao: RankingDao
) {

    fun getAllRankings(): Flow<List<RankingEntity>> {
        return rankingDao.getAllRankings()
    }

}
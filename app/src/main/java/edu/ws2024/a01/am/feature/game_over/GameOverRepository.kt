package edu.ws2024.a01.am.feature.game_over

import edu.ws2024.a01.am.core.database.RankingDao
import edu.ws2024.a01.am.core.database.RankingEntity
import javax.inject.Inject

class GameOverRepository @Inject constructor(
    private val rankingDao: RankingDao
) {

    suspend fun insertRanking(rankingEntity: RankingEntity) {
        rankingDao.insertRanking(rankingEntity)
    }

}
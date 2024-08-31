package edu.ws2024.a01.am.feature.game_over

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ws2024.a01.am.core.database.RankingEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameOverViewModel @Inject constructor(
    private val gameOverRepository: GameOverRepository
) : ViewModel() {

    fun insertRanking(username: String, secondsTime: Int, score: Int) {
        viewModelScope.launch {
            gameOverRepository.insertRanking(
                RankingEntity(
                    name = username,
                    secondsTime = secondsTime,
                    score = score
                )
            )
        }
    }

}
package edu.ws2024.a01.am.feature.ranking

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ws2024.a01.am.core.database.RankingEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankingRepository: RankingRepository
) : ViewModel() {

    private val _rankingList = mutableStateListOf<RankingEntity>()
    val rankingList: List<RankingEntity> = _rankingList

    init {
        viewModelScope.launch {
            rankingRepository.getAllRankings().collect {
                _rankingList.clear()
                _rankingList.addAll(it.sortedByDescending { item -> item.score })
            }
        }
    }

}
package com.github.abraga.fuzetest.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.github.abraga.fuzetest.data.models.MatchResponse
import com.github.abraga.fuzetest.data.models.PlayerResponse
import com.github.abraga.fuzetest.data.models.ResponseState
import com.github.abraga.fuzetest.data.models.State
import com.github.abraga.fuzetest.data.repository.MatchesRepository
import com.github.abraga.fuzetest.data.repository.PlayersRepository
import com.github.abraga.fuzetest.utils.orZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias PlayerState = List<Pair<PlayerResponse?, PlayerResponse?>>

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val matchesRepository: MatchesRepository,
    private val playersRepository: PlayersRepository
) : ViewModel(), LifecycleObserver {

    private val _playersState = createStateFlow<PlayerState>()
    val playersState: MutableStateFlow<ResponseState<PlayerState>>
        get() = _playersState

    private val _matchesState = createStateFlow<PagingData<MatchResponse>>()
    val matchesFlow: MutableStateFlow<ResponseState<PagingData<MatchResponse>>>
        get() = _matchesState

    fun getMatches() = viewModelScope.launch {
        _matchesState.value = ResponseState(State.LOADING, null, null)

        matchesRepository.getMatches().catch {
            _matchesState.value = ResponseState(State.ERROR, null, it.message)
        }.collectLatest {
            _matchesState.value = ResponseState(State.SUCCESS, it, null)
        }
    }

    fun getPlayers(match: MatchResponse) = viewModelScope.launch {
        val homeTeamId = getTeamId(match, 0) ?: run {
            onError()
            return@launch
        }
        val awayTeamId = getTeamId(match, 1) ?: run {
            onError()
            return@launch
        }

        playersRepository.getPlayers("$homeTeamId,$awayTeamId").catch {
            _playersState.value = ResponseState(State.ERROR, null, it.message)
        }.collectLatest {
            val playersPairList = getPlayersPairList(homeTeamId, awayTeamId, it)
            _playersState.value = ResponseState(State.SUCCESS, playersPairList, null)
        }
    }

    private fun getPlayersPairList(
        homeTeamId: Int?,
        awayTeamId: Int?,
        responseList: ResponseState<List<PlayerResponse>>
    ): ArrayList<Pair<PlayerResponse?, PlayerResponse?>> {
        val homeTeamPlayers = responseList.data?.filter { it.team?.id == homeTeamId }
        val awayTeamPlayers = responseList.data?.filter { it.team?.id == awayTeamId }

        val count = if (homeTeamPlayers?.size.orZero > awayTeamPlayers?.size.orZero)
            homeTeamPlayers?.size.orZero
        else
            awayTeamPlayers?.size.orZero

        val playersPair = arrayListOf<Pair<PlayerResponse?, PlayerResponse?>>()
        (0..count).forEach {
            val currentHomePlayer = homeTeamPlayers?.getOrNull(it)
            val currentAwayPlayer = awayTeamPlayers?.getOrNull(it)
            playersPair.add(currentHomePlayer to currentAwayPlayer)
        }
        return playersPair
    }

    private fun getTeamId(match: MatchResponse, position: Int): Int? {
        val teams = match.teams ?: return null
        if (teams.size > 1) {
            return teams[position].opponent?.id
        }
        return null
    }

    private fun onError() {
        _playersState.value = ResponseState(State.ERROR, null, null)
    }

    private fun <T> createStateFlow() = MutableStateFlow(
        ResponseState<T>(
            State.LOADING,
            null,
            null
        )
    )
}
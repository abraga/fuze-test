package com.github.abraga.fuzetest.data.repository

import com.github.abraga.fuzetest.data.api.MatchesApi
import com.github.abraga.fuzetest.data.models.PlayerResponse
import com.github.abraga.fuzetest.data.models.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlayersRepositoryImpl @Inject constructor(
    private val api: MatchesApi
) : PlayersRepository {

    override suspend fun getPlayers(
        teamsIds: String
    ): Flow<ResponseState<List<PlayerResponse>>> {
        return flow {
            val players = api.fetchPlayersByTeamsIds(teamsIds)
            emit(ResponseState.success(players))
        }.flowOn(Dispatchers.IO)
    }
}
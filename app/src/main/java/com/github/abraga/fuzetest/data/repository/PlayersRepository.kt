package com.github.abraga.fuzetest.data.repository

import com.github.abraga.fuzetest.data.models.PlayerResponse
import com.github.abraga.fuzetest.data.models.ResponseState
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {

    suspend fun getPlayers(
        teamsIds: String,
    ): Flow<ResponseState<List<PlayerResponse>>>
}
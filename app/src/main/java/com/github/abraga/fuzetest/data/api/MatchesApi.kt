package com.github.abraga.fuzetest.data.api

import com.github.abraga.fuzetest.data.models.MatchResponse
import com.github.abraga.fuzetest.data.models.PlayerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesApi {

    @GET("matches?filter[status]=running,not_started,finished&sort=-status,begin_at&per_page=10")
    suspend fun fetchMatches(@Query("page") page: Int): List<MatchResponse>

    @GET("players")
    suspend fun fetchPlayersByTeamsIds(
        @Query("filter[team_id]") teamsIds: String,
    ): List<PlayerResponse>
}
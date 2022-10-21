package com.github.abraga.fuzetest.data.repository

import androidx.paging.PagingData
import com.github.abraga.fuzetest.data.models.MatchResponse
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getMatches(): Flow<PagingData<MatchResponse>>
}
package com.github.abraga.fuzetest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.abraga.fuzetest.data.api.MatchesApi
import com.github.abraga.fuzetest.data.api.datasource.MatchesDataSource
import com.github.abraga.fuzetest.data.models.MatchResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val api: MatchesApi
) : MatchesRepository {

    override suspend fun getMatches(): Flow<PagingData<MatchResponse>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 2
        ),
        pagingSourceFactory = {
            MatchesDataSource(api)
        }
    ).flow
}
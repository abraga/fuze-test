package com.github.abraga.fuzetest.data.api.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.abraga.fuzetest.data.api.MatchesApi
import com.github.abraga.fuzetest.data.models.MatchResponse

class MatchesDataSource(private val api: MatchesApi) : PagingSource<Int, MatchResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MatchResponse>) = state.anchorPosition?.let {
        state.closestPageToPosition(it)?.prevKey?.plus(1) ?:
        state.closestPageToPosition(it)?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MatchResponse> {
        val currentPage = params.key ?: INITIAL_PAGE
        return try {
            val response = api.fetchMatches(page = currentPage)

            val nextKey = if (response.isEmpty()) {
                null
            } else {
                currentPage + (params.loadSize / PAGES_PER_LOAD)
            }

            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == INITIAL_PAGE) null else currentPage,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        private const val INITIAL_PAGE = 1
        private const val PAGES_PER_LOAD = 10
    }
}
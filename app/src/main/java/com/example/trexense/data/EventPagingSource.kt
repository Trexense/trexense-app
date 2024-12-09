package com.example.trexense.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trexense.data.response.DataItem
import com.example.trexense.data.retrofit.ApiService

class EventPagingSource(private val apiService: ApiService) : PagingSource<Int, DataItem>() {
    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val nextPage = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getEvent(page = nextPage, limit = params.loadSize)
            val events = response.data
            LoadResult.Page(
                data = events,
                prevKey = if (nextPage == INITIAL_PAGE_INDEX) null else nextPage - 1,
                nextKey = if (events.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
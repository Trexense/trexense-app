package com.example.trexense.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trexense.data.repository.EventRepository
import com.example.trexense.data.response.DataItem
import com.example.trexense.data.response.EventResponse
import com.example.trexense.data.retrofit.ApiService
import retrofit2.HttpException

class EventPagingSource(private val apiService: ApiService) : PagingSource<Int, DataItem>() {
    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }

        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val nextPage = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getEvent(page = nextPage, limit = params.loadSize)
            val events = response.data
            val responseData = mutableListOf<DataItem>()
            events.let { responseData.addAll(it) }
            LoadResult.Page(
                data = responseData,
                prevKey =  if(nextPage == INITIAL_PAGE_INDEX) null else -1,
                nextKey = nextPage.plus(1)
            )
//            LoadResult.Page(
//                data = events,
//                prevKey = if (nextPage == INITIAL_PAGE_INDEX) null else nextPage - 1,
//                nextKey = if (events.isEmpty()) null else nextPage + 1
//            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpE: HttpException) {
            LoadResult.Error(httpE)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
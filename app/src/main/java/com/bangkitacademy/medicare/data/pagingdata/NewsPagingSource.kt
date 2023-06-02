package com.bangkitacademy.medicare.data.pagingdata

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bangkitacademy.medicare.data.remote.response.ArticlesItem
import com.bangkitacademy.medicare.data.remote.retrofitpublic.PublicApiService

class NewsPagingSource(private val publicApiService: PublicApiService) :
    PagingSource<Int, ArticlesItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = publicApiService.getNews(page = position, pageSize = params.loadSize)

            LoadResult.Page(
                data = responseData.articles,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.articles.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}
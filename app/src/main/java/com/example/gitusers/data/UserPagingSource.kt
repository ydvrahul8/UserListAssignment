package com.example.gitusers.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gitusers.data.model.UserListItem
import com.example.gitusers.data.remote.UserAPI
import com.example.gitusers.util.Constants.PER_PAGE

const val STARTING_PAGE_INDEX = 1

class UserPagingSource(private val userAPI: UserAPI) : PagingSource<Int, UserListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserListItem> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = userAPI.getUserList(position, PER_PAGE)

            return LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = position + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
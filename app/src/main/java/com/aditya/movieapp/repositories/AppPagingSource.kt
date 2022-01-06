package com.aditya.movieapp.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aditya.movieapp.extras.Constants.API_KEY
import com.aditya.movieapp.extras.Constants.START_PAGE
import com.aditya.movieapp.local.interfaces.APIClient
import com.aditya.movieapp.local.responses.ResponseModel
import com.aditya.movieapp.local.responses.ResultModel
import javax.inject.Inject

class AppPagingSource @Inject constructor(private val apiClient: APIClient) :
    PagingSource<Int, ResultModel>() {

    override fun getRefreshKey(state: PagingState<Int, ResultModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultModel> {
        val pageNumber = params.key ?: START_PAGE
        val responseModel: ResponseModel =
            apiClient.getResponseFromAPI(api_key = API_KEY, pageNumber)
        val resultModel: List<ResultModel> = responseModel.resultModels

        return try {
            LoadResult.Page(
                data = resultModel,
                prevKey = if (pageNumber == START_PAGE) null else pageNumber - 1,
                nextKey = if (resultModel.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
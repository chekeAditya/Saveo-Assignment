package com.aditya.movieapp.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.aditya.movieapp.extras.Constants.API_KEY
import com.aditya.movieapp.local.ResponseHandler
import com.aditya.movieapp.local.interfaces.APIClient
import com.aditya.movieapp.local.responses.ResponseModel
import com.aditya.movieapp.local.Resource
import javax.inject.Inject

class AppRepo @Inject constructor(private val apiClient: APIClient) {

    private val responseHandler = ResponseHandler()

    fun getPageList() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            AppPagingSource(apiClient)
        }
    ).liveData

    suspend fun getResponseFromAPI(page: Int): Resource<ResponseModel> {
        val response = apiClient.getResponseFromAPI(API_KEY, page)
        return try {
            responseHandler.handleSuccess(response)
        }catch (e: Exception){
            responseHandler.handleException(e)
        }
    }
}
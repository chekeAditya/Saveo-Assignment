package com.aditya.movieapp.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.aditya.movieapp.local.interfaces.APIClient
import javax.inject.Inject

class AppRepo @Inject constructor(private val apiClient: APIClient) {

    fun getPageList() = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            AppPagingSource(apiClient)
        }
    ).liveData
}
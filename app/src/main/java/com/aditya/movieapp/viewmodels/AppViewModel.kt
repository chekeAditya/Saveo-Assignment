package com.aditya.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.aditya.movieapp.local.Resource
import com.aditya.movieapp.local.responses.ResponseModel
import com.aditya.movieapp.repositories.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appRepo: AppRepo) : ViewModel() {

    fun getMovieResponse() = appRepo.getPageList()


    fun getResponseFromAPI(page: Int): LiveData<Resource<ResponseModel>> {
        return liveData(Dispatchers.IO) {
            val response = appRepo.getResponseFromAPI(page)
            emit(response)
        }
    }
}
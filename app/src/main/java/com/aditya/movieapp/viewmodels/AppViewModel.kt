package com.aditya.movieapp.viewmodels

import androidx.lifecycle.ViewModel
import com.aditya.movieapp.repositories.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appRepo: AppRepo) : ViewModel(){

    fun getMovieResponse() = appRepo.getPageList()

}
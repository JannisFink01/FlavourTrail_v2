package com.example.flavourtrail_v2.data.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flavourtrail_v2.PlaceReviewViewModel
import com.example.flavourtrail_v2.data.repository.PlaceReviewRepository

class ReviewViewModelFactory(private val repository: PlaceReviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaceReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.flavourtrail_v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavourtrail_v2.data.entity.PlaceReviewWithDetails
import com.example.flavourtrail_v2.data.repository.PlaceReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceReviewViewModel(private val repository: PlaceReviewRepository) : ViewModel() {

    private val _reviews = MutableStateFlow<List<PlaceReviewWithDetails>>(emptyList())
    val reviews: StateFlow<List<PlaceReviewWithDetails>> = _reviews

    fun getReviewsByPlaceId(placeId: Int) {
        viewModelScope.launch {
            _reviews.value = repository.getPlaceReviewsWithDetailsByPlaceId(placeId)
        }
    }
    fun getReviewsWithDetailsByPlaceId(placeId: Int) {
        viewModelScope.launch {
            _reviews.value = repository.getPlaceReviewsWithDetailsByPlaceId(placeId)
        }
    }
}
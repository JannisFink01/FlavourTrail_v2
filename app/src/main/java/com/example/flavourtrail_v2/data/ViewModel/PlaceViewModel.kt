package com.example.flavourtrail_v2.data.ViewModel

import androidx.lifecycle.ViewModel
import com.example.flavourtrail_v2.data.entity.Place
import com.example.flavourtrail_v2.data.repository.PlaceRepository

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {

    suspend fun getPlaceById(placeId: Int): Place? {
        return repository.getPlaceById(placeId)
    }

}
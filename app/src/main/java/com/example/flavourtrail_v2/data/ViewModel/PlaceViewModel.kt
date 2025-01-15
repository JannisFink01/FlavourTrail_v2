package com.example.flavourtrail_v2.data.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavourtrail_v2.data.entity.Place
import com.example.flavourtrail_v2.data.repository.PlaceRepository
import kotlinx.coroutines.launch

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {

    fun insertPlace(place: Place) {
        viewModelScope.launch {
            repository.insertPlace(place)
        }
    }

    fun insertAllPlaces(vararg places: Place) {
        viewModelScope.launch {
            repository.insertAllPlaces(*places)
        }
    }

    fun updatePlace(place: Place) {
        viewModelScope.launch {
            repository.updatePlace(place)
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            repository.deletePlace(place)
        }
    }

    suspend fun getPlaceById(placeId: Int): Place? {
        return repository.getPlaceById(placeId)
    }

    suspend fun getAllPlaces(): List<Place> {
        return repository.getAllPlaces()
    }
}
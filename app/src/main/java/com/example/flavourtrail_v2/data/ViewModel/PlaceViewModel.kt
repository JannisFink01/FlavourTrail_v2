package com.example.flavourtrail_v2.data.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavourtrail_v2.data.entity.Place
import com.example.flavourtrail_v2.data.entity.PlaceReviewWithDetails
import com.example.flavourtrail_v2.data.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

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

    fun loadPlaces() {
        viewModelScope.launch {
            _places.value = repository.getAllPlaces()
        }
    }
}
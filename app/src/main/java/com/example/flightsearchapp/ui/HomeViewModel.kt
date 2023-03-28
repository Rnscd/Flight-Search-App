package com.example.flightsearchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.FlightApplication
import com.example.flightsearchapp.data.FlightDao
import com.example.flightsearchapp.data.airport
import com.example.flightsearchapp.data.favorite
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val flightDao: FlightDao
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())

    val homeUiState: StateFlow<HomeUiState>
        get() = _homeUiState

    fun getFavorites() = flightDao.getFavorites()

    fun insertFavorite(fav: favorite)  =
        viewModelScope.launch {
            flightDao.insertFav(fav)
        }

    fun getAirports(search: String) = flightDao.getAirports(search)



    fun getResult(id: Int) {
        viewModelScope.launch {
            val result = flightDao.getResult(id).first()
            val destinations = flightDao.getDestinations(id).first()
            _homeUiState.value = HomeUiState(airportSt = result, destination = destinations)
        }
    }



    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightApplication)
                HomeViewModel(application.database.flightDao())
            }
        }
    }
}

data class HomeUiState(
    val airportSt: airport = airport(0, "none", "none", 0),
    val destination: List<airport> = emptyList()
)

package com.example.flightsearchapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.favorite
import kotlinx.coroutines.Job
import kotlin.math.abs

@Composable
fun DetailsScreen(
    homeUiState: State<HomeUiState>, modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: HomeViewModel,
    ) {

    val departure = homeUiState.value.airportSt
    val destinations = homeUiState.value.destination
    LazyColumn(){
        items(destinations.size){
            CardFlights(
                departureCode = departure.iata_code,
                departureName = departure.name,
                destinationCode = destinations[it].iata_code,
                destinationName = destinations[it].name
            ) {
                viewModel.insertFavorite(
                    favorite(
                       // id = abs((departure.iata_code + destinations[it].iata_code).hashCode()),
                        departure_code = departure.iata_code,
                        destination_code = destinations[it].iata_code
                    )
                )
            }
        }
    }
}

@Composable
fun CardFlights(
    departureCode: String,
    departureName: String,
    destinationCode: String,
    destinationName: String,
    onCheck: () -> Unit
) {
    Card(modifier = Modifier.padding(10.dp)) {
        Column() {
            Text(text = "DEPARTURE")
            Row() {
                Text(text = departureCode, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = departureName)
                // IconToggleButton(checked = false, onCheckedChange = onCheck) {
            }

            Text(text = "DESTINATION")
            Row() {
                Text(text = destinationCode, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = destinationName)
            }

            Button(onClick = onCheck) {
            }
        }
    }
}


/*
   Column() {
       Text(text = homeUiState.value.airportSt.name)
       Text(text = homeUiState.value.airportSt.iata_code)
       Text(text = homeUiState.value.airportSt.passengers.toString())

       val destinations = homeUiState.value.destination.collectAsState(initial = emptyList()).value
       if (destinations.isNotEmpty()) {
           Text(text = destinations[0].name)
           Text(text = destinations[0].iata_code)
           Text(text = destinations[0].passengers.toString())
       } else {
           Text(text = "No destinations found.")
       }
   }

    */
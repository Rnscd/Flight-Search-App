package com.example.flightsearchapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.data.favorite

@Composable
fun DetailsScreen(
    homeUiState: State<HomeUiState>,
    viewModel: HomeViewModel,
    ) {

    val departure = homeUiState.value.airportSt
    val destinations = homeUiState.value.destination
    LazyColumn(modifier = Modifier.fillMaxWidth()){
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
    onFavorite: () -> Unit
) {
    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxSize()) {
        Column(Modifier.padding(5.dp)) {
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

            IconButton(onClick = onFavorite,  modifier = Modifier.padding(start = 100.dp)) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "favorite")
            }
        }
    }
}

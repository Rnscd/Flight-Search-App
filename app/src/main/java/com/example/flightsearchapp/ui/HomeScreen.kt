package com.example.flightsearchapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    uiState: SearchUiState,
    saveSearch: (String) -> Unit,
    onNav: () -> Unit,
) {
    var search = uiState.searchSt

    val airports by viewModel.getAirports(search).collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)) {

        TextField(
            value = search,
            onValueChange = {
                search = it
                saveSearch(it)
                viewModel.getAirports(it)
            },
            label = { Text("Search") },
            placeholder = { Text("Search airports...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (search.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()
            ) {
                items(airports.size) {
                    ColumnAirports(
                        onClick = {
                            viewModel.getResult(airports[it].id)
                            onNav()
                        },
                        name = airports[it].name,
                        iata = airports[it].iata_code
                    )
                }
            }
        }  else{
            val favorites by viewModel.getFavorites().collectAsState(initial = emptyList())
            if (favorites.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Saved Routes")

                    LazyColumn() {
                        items(favorites.size) {
                            FavoritesColumn(
                                departure = favorites[it].departure_code,
                                destination = favorites[it].destination_code
                            )
                        }
                    }
                }
            }else{
                Text(text = "No favorites")
            }
        }
    }
}

@Composable
fun ColumnAirports(
    onClick: () -> Unit,
    name: String,
    iata: String,
) {
    Row(Modifier.clickable(onClick = onClick)) {
        Text(text = iata, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = name)
    }
}

@Composable
fun FavoritesColumn(departure: String, destination: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row() {
            Text(text = "Departure")
            Spacer(modifier = Modifier.width(30.dp))
            Text(text = "Destination")
        }
        Row() {
            Text(text = departure, Modifier.padding(10.dp))
            Spacer(modifier = Modifier.width(30.dp))
            Text(text = destination, Modifier.padding(10.dp))
        }
    }
}
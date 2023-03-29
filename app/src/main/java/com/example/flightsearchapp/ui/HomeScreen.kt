package com.example.flightsearchapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.first

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    uiState: SearchUiState,
    saveSearch: (String) -> Unit,
    onNav: () -> Unit,
) {
    var search = uiState.searchSt

    val airports by viewModel.getAirports(search).collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {

        TextField(
            value = search,
            onValueChange = {
                search = it
                saveSearch(search)
                viewModel.getAirports(it)
            },
            label = { Text("Search") },
            placeholder = { Text("Search airports...") }
        )

        if (search.isNotEmpty()) {
            LazyColumn() {
                items(airports.size) {
                    ColumnAirports(
                        onClick = {
                            viewModel.getResult(airports[it].id)
                            onNav() // Invoke the onNav lambda to navigate to "details" destination
                        },
                        name = airports[it].name,
                        iata = airports[it].iata_code
                    )
                }
            }
        }  else{
            val favorites by viewModel.getFavorites().collectAsState(initial = emptyList())
            if (favorites.isNotEmpty()) {
                LazyColumn() {
                    items(favorites.size) {
                        Text(text = favorites[it].departure_code)
                        Text(text = favorites[it].destination_code)
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


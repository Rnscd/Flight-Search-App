package com.example.flightsearchapp.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity
data class airport(
    @PrimaryKey
    val id: Int,
    val iata_code: String,
    val name: String,
    val passengers: Int
)

@Entity(indices = [Index(value = ["departure_code", "destination_code"], unique = true)])
data class favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val departure_code: String,
    val destination_code: String
)

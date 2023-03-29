package com.example.flightsearchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [airport::class, favorite::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun flightDao(): FlightDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val databaseName = "app_database"
                if (context.getDatabasePath(databaseName).exists()) {
                    // Database file already exists, use the existing database.
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        databaseName
                    ).build()
                } else {
                    // Database file does not exist, create a new database from asset file.
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "$databaseName-new"
                    )
                        .createFromAsset("database/flight_search.db")
                        .fallbackToDestructiveMigration()
                        .build()
                        .also {
                            // Rename the new database file to the desired name.
                            context.getDatabasePath("$databaseName-new").renameTo(
                                context.getDatabasePath(databaseName)
                            )
                            INSTANCE = it
                        }
                }
            }
        }
    }
}
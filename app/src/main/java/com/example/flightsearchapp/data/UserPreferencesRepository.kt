package com.example.flightsearchapp.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

/*
 * Concrete class implementation to access data store
 */
class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val SEARCH = stringPreferencesKey("search")
        const val TAG = "UserPreferencesRepo"
    }

    val search: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[SEARCH] ?: "empty"
        }

    suspend fun saveSearchPreference(search: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH] = search
        }
    }
}

/*


           */
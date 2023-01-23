package com.mendelu.xmoric.burgermaster.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class storeProfileInfo(private val context: Context){

    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_SURNAME_KEY = stringPreferencesKey("user_surname")
        val STREET_KEY = stringPreferencesKey("street_name")
        val ZIP_KEY = stringPreferencesKey("zip_number")
        val CITY_KEY = stringPreferencesKey("city_name")
        val STATE_KEY = stringPreferencesKey("state_name")
    }

    val getName: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
    }

    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    val getSurname: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_SURNAME_KEY] ?: ""
        }

    suspend fun saveSurname(surname: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_SURNAME_KEY] = surname
        }
    }

    val getStreet: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[STREET_KEY] ?: ""
        }

    suspend fun saveStreet(street: String) {
        context.dataStore.edit { preferences ->
            preferences[STREET_KEY] = street
        }
    }

    val getZip: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ZIP_KEY] ?: ""
        }

    suspend fun saveZip(zip: String) {
        context.dataStore.edit { preferences ->
            preferences[ZIP_KEY] = zip
        }
    }

    val getCity: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[CITY_KEY] ?: ""
        }

    suspend fun saveCity(city: String) {
        context.dataStore.edit { preferences ->
            preferences[CITY_KEY] = city
        }
    }

    val getState: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[STATE_KEY] ?: ""
        }

    suspend fun saveState(surname: String) {
        context.dataStore.edit { preferences ->
            preferences[STATE_KEY] = surname
        }
    }


}

package com.comets.comets.data.prefs

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.comets.comets.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenPreferences @Inject constructor(@ApplicationContext val context: Context) {
    companion object {
        const val AUTH_TOKEN = "auth_token"
    }

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey(AUTH_TOKEN)] = token
        }
        Log.d(
            "AuthTokenPreferences",
            "authToken saved: $token"
        )
    }

    suspend fun clearAuthToken() {
        context.dataStore.edit { pref ->
            pref.remove(stringPreferencesKey(AUTH_TOKEN))
        }
    }

    val authToken: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { pref ->
            pref[stringPreferencesKey(AUTH_TOKEN)] ?: ""
        }
}
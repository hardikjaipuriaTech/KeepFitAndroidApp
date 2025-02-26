package com.material.tortoise.view

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class TortoiseDataStorePreferencesRepo(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val GOALS_EDITABLE_PREF = booleanPreferencesKey("goals_editable")
        val HISTORY_EDITABLE_PREF = booleanPreferencesKey("history_editable")
    }

    suspend  fun storeGoalsEditableKey(value: Boolean){
        dataStore.edit {
            it[GOALS_EDITABLE_PREF] = value
        }
    }

    suspend  fun storeHistoryEditableKey(value: Boolean){
        dataStore.edit {
            it[HISTORY_EDITABLE_PREF] = value
        }
    }

     suspend fun toggleGoalsEditable() = dataStore.edit {
         it[GOALS_EDITABLE_PREF] = !(it[GOALS_EDITABLE_PREF] ?: true)
     }

    suspend fun toggleHistoryEditable() = dataStore.edit {
        it[HISTORY_EDITABLE_PREF] = !(it[HISTORY_EDITABLE_PREF] ?: true)
    }

     fun isGoalEditable() = dataStore.data.catch { exception -> // 1
         // dataStore.data throws an IOException if it can't read the data
         if (exception is IOException) { // 2
             emit(emptyPreferences())
         } else {
             throw exception
         }
     }.map { it[GOALS_EDITABLE_PREF] ?: true }

    fun isHistoryEditable() = dataStore.data.catch { exception -> // 1
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) { // 2
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[HISTORY_EDITABLE_PREF] ?: true }

}
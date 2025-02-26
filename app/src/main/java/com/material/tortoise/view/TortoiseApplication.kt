package com.material.tortoise.view

import android.app.Application
import com.material.tortoise.data.TortoiseDatabase
import com.material.tortoise.data.TortoiseRepository

class TortoiseApplication : Application() {

    private val database by lazy { TortoiseDatabase.getDatabase(this) }


    val repository by lazy {
        TortoiseRepository(database.tortoiseDao())
    }
    val dataStorePrefRepo by lazy {
        TortoiseDataStorePreferencesRepo(this)
    }

}
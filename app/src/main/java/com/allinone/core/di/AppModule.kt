package com.allinone.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.allinone.core.data.database.AppDatabase
import com.allinone.core.data.repository.DemoRepository

// Application context holder
object AppModule {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    val context: Context
        get() = appContext

    // SharedPreferences
    val sharedPreferences: SharedPreferences by lazy {
        appContext.getSharedPreferences("allinone_prefs", Context.MODE_PRIVATE)
    }

    // DataStore
    val dataStore: DataStore<Preferences> by lazy {
        appContext.dataStore
    }

    // Room Database
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "allinone_database"
        ).fallbackToDestructiveMigration().build()
    }

    // Repositories
    val demoRepository: DemoRepository by lazy {
        DemoRepository(database.demoDao(), dataStore, sharedPreferences)
    }
}

// DataStore delegate
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

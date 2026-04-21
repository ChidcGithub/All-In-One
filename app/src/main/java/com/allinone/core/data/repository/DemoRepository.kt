package com.allinone.core.data.repository

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.allinone.core.data.database.DemoDao
import com.allinone.core.data.database.DemoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DemoRepository(
    private val demoDao: DemoDao,
    private val dataStore: DataStore<Preferences>,
    private val sharedPreferences: SharedPreferences
) {
    // Room operations
    fun getAllItems(): Flow<List<DemoItem>> = demoDao.getAllItems()
    fun getItemsByCategory(category: String): Flow<List<DemoItem>> = demoDao.getItemsByCategory(category)
    fun searchItems(query: String): Flow<List<DemoItem>> = demoDao.searchItems(query)
    fun getAllCategories(): Flow<List<String>> = demoDao.getAllCategories()

    suspend fun insertItem(item: DemoItem) = demoDao.insertItem(item)
    suspend fun insertItems(items: List<DemoItem>) = demoDao.insertItems(items)
    suspend fun deleteItem(item: DemoItem) = demoDao.deleteItem(item)
    suspend fun deleteItemsByCategory(category: String) = demoDao.deleteItemsByCategory(category)
    suspend fun getItemCount(): Int = demoDao.getItemCount()

    // DataStore operations
    fun getPreference(key: String): Flow<String?> = dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)]
    }

    suspend fun savePreference(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    // SharedPreferences operations
    fun getSharedString(key: String, defaultValue: String): String =
        sharedPreferences.getString(key, defaultValue) ?: defaultValue

    fun saveSharedString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getSharedBoolean(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    fun saveSharedBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getSharedInt(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)

    fun saveSharedInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }
}

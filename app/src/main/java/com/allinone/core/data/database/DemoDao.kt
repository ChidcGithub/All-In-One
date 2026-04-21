package com.allinone.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "demo_items")
data class DemoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface DemoDao {
    @Query("SELECT * FROM demo_items ORDER BY timestamp DESC")
    fun getAllItems(): Flow<List<DemoItem>>

    @Query("SELECT * FROM demo_items WHERE category = :category ORDER BY timestamp DESC")
    fun getItemsByCategory(category: String): Flow<List<DemoItem>>

    @Query("SELECT * FROM demo_items WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchItems(query: String): Flow<List<DemoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: DemoItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<DemoItem>)

    @Delete
    suspend fun deleteItem(item: DemoItem)

    @Query("DELETE FROM demo_items WHERE category = :category")
    suspend fun deleteItemsByCategory(category: String)

    @Query("SELECT COUNT(*) FROM demo_items")
    suspend fun getItemCount(): Int

    @Query("SELECT DISTINCT category FROM demo_items")
    fun getAllCategories(): Flow<List<String>>
}

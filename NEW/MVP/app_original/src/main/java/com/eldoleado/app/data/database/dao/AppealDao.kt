package com.eldoleado.app.data.database.dao

import androidx.room.*
import com.eldoleado.app.data.database.entities.AppealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppealDao {

    @Query("SELECT * FROM appeals ORDER BY updatedAt DESC")
    fun getAllAppeals(): Flow<List<AppealEntity>>

    @Query("SELECT * FROM appeals ORDER BY updatedAt DESC")
    suspend fun getAllAppealsList(): List<AppealEntity>

    @Query("SELECT * FROM appeals WHERE id = :id")
    suspend fun getAppealById(id: String): AppealEntity?

    @Query("SELECT * FROM appeals WHERE id = :id")
    fun getAppealByIdFlow(id: String): Flow<AppealEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppeal(appeal: AppealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppeals(appeals: List<AppealEntity>)

    @Update
    suspend fun updateAppeal(appeal: AppealEntity)

    @Query("UPDATE appeals SET lastAccessed = :timestamp WHERE id = :id")
    suspend fun updateLastAccessed(id: String, timestamp: Long)

    @Query("UPDATE appeals SET isCacheValid = 0 WHERE id = :id")
    suspend fun invalidateCache(id: String)

    @Query("UPDATE appeals SET isCacheValid = 0")
    suspend fun invalidateAllCache()

    @Query("UPDATE appeals SET hasFullDetails = 1, isCacheValid = 1 WHERE id = :id")
    suspend fun markAsFullyLoaded(id: String)

    @Delete
    suspend fun deleteAppeal(appeal: AppealEntity)

    @Query("DELETE FROM appeals WHERE id = :id")
    suspend fun deleteAppealById(id: String)

    @Query("DELETE FROM appeals")
    suspend fun deleteAllAppeals()

    @Query("SELECT COUNT(*) FROM appeals")
    suspend fun getAppealsCount(): Int
}

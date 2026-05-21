package com.record.fitness.data.repository

import androidx.room.*
import com.record.fitness.data.model.WorkoutRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<WorkoutRecord>>

    @Query("SELECT * FROM workout_records WHERE exerciseId = :exerciseId ORDER BY timestamp DESC")
    fun getRecordsByExercise(exerciseId: String): Flow<List<WorkoutRecord>>

    @Query("SELECT * FROM workout_records WHERE exerciseId = :exerciseId ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentRecordsByExercise(exerciseId: String, limit: Int): Flow<List<WorkoutRecord>>

    @Query("SELECT MAX(weight) FROM workout_records WHERE exerciseId = :exerciseId")
    suspend fun getMaxWeight(exerciseId: String): Float?

    @Query("SELECT * FROM workout_records WHERE exerciseId = :exerciseId AND isPR = 1 ORDER BY timestamp DESC LIMIT 1")
    suspend fun getPRRecord(exerciseId: String): WorkoutRecord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: WorkoutRecord)

    @Delete
    suspend fun deleteRecord(record: WorkoutRecord)

    @Query("DELETE FROM workout_records WHERE id = :recordId")
    suspend fun deleteRecordById(recordId: Long)

    @Query("DELETE FROM workout_records")
    suspend fun deleteAllRecords()
}

package com.record.fitness.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.record.fitness.data.model.*
import com.record.fitness.data.repository.AppDatabase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class WorkoutUiState(
    val exercises: List<Exercise> = DefaultExercises.exercises,
    val selectedMuscleGroup: String = "chest",
    val selectedExercise: Exercise? = null,
    val records: List<WorkoutRecord> = emptyList(),
    val exerciseRecords: Map<String, List<WorkoutRecord>> = emptyMap(),
    val isLoading: Boolean = false,
    val showAddDialog: Boolean = false,
    val showDetailSheet: Boolean = false,
    val accentColor: Long = 0xFF007AFF
)

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val workoutDao = database.workoutDao()

    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    init {
        loadAllRecords()
    }

    private fun loadAllRecords() {
        viewModelScope.launch {
            workoutDao.getAllRecords().collect { records ->
                val exerciseMap = mutableMapOf<String, List<WorkoutRecord>>()
                val updatedExercises = DefaultExercises.exercises.map { exercise ->
                    val exerciseRecords = records.filter { it.exerciseId == exercise.id }
                    exerciseMap[exercise.id] = exerciseRecords
                    val maxPR = exerciseRecords.maxOfOrNull { it.weight } ?: 0f
                    exercise.copy(currentPR = maxPR)
                }
                _uiState.update {
                    it.copy(
                        records = records,
                        exerciseRecords = exerciseMap,
                        exercises = updatedExercises
                    )
                }
            }
        }
    }

    fun selectMuscleGroup(groupId: String) {
        _uiState.update { it.copy(selectedMuscleGroup = groupId, showDetailSheet = false) }
    }

    fun selectExercise(exercise: Exercise) {
        _uiState.update { it.copy(selectedExercise = exercise, showDetailSheet = true) }
        loadExerciseRecords(exercise.id)
    }

    private fun loadExerciseRecords(exerciseId: String) {
        viewModelScope.launch {
            workoutDao.getRecordsByExercise(exerciseId).collect { records ->
                _uiState.update { state ->
                    state.copy(
                        exerciseRecords = state.exerciseRecords + (exerciseId to records)
                    )
                }
            }
        }
    }

    fun closeDetail() {
        _uiState.update { it.copy(showDetailSheet = false, selectedExercise = null) }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addRecord(exerciseId: String, exerciseName: String, muscleGroup: String, weight: Float, reps: Int, sets: Int) {
        viewModelScope.launch {
            val currentPR = workoutDao.getMaxWeight(exerciseId) ?: 0f
            val isPR = weight > currentPR
            val volume = weight * reps * sets

            val record = WorkoutRecord(
                exerciseId = exerciseId,
                exerciseName = exerciseName,
                muscleGroup = muscleGroup,
                weight = weight,
                reps = reps,
                sets = sets,
                volume = volume,
                isPR = isPR,
                timestamp = System.currentTimeMillis(),
                date = dateFormat.format(Date())
            )

            workoutDao.insertRecord(record)
            hideAddDialog()
        }
    }

    fun deleteRecord(record: WorkoutRecord) {
        viewModelScope.launch {
            workoutDao.deleteRecord(record)
        }
    }

    fun setAccentColor(color: Long) {
        _uiState.update { it.copy(accentColor = color) }
    }
}

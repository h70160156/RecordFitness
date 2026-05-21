package com.record.fitness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.record.fitness.ui.WorkoutUiState
import com.record.fitness.ui.WorkoutViewModel
import com.record.fitness.ui.screens.*
import com.record.fitness.ui.theme.RecordFitnessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RecordFitnessTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WorkoutApp()
                }
            }
        }
    }
}

@Composable
fun WorkoutApp(viewModel: WorkoutViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AnimatedContent(
            targetState = uiState.selectedExercise != null,
            transitionSpec = {
                if (targetState) {
                    slideInHorizontally { it } + fadeIn() togetherWith
                            slideOutHorizontally { -it } + fadeOut()
                } else {
                    slideInHorizontally { -it } + fadeIn() togetherWith
                            slideOutHorizontally { it } + fadeOut()
                }
            },
            label = "pageTransition"
        ) { showDetail ->
            if (showDetail && uiState.selectedExercise != null) {
                ExerciseDetailScreen(
                    exercise = uiState.selectedExercise!!,
                    records = uiState.exerciseRecords[uiState.selectedExercise!!.id] ?: emptyList(),
                    onBack = { viewModel.closeDetail() },
                    onAddRecord = { viewModel.showAddDialog() },
                    onRecordAdded = { weight, reps, sets ->
                        val exercise = uiState.selectedExercise!!
                        viewModel.addRecord(exercise.id, exercise.name, exercise.muscleGroup, weight, reps, sets)
                    },
                    onDeleteRecord = { viewModel.deleteRecord(it) },
                    showDialog = uiState.showAddDialog,
                    onDismissDialog = { viewModel.hideAddDialog() },
                    accentColor = Color(uiState.accentColor)
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    when (uiState.activePage) {
                        "workout" -> WorkoutScreen(
                            uiState = uiState,
                            onMuscleGroupSelected = { viewModel.selectMuscleGroup(it) },
                            onExerciseSelected = { viewModel.selectExercise(it) },
                            onAddRecord = { viewModel.showAddDialog() },
                            onRecordAdded = { id, name, muscle, weight, reps, sets ->
                                viewModel.addRecord(id, name, muscle, weight, reps, sets)
                            },
                            onDismissDialog = { viewModel.hideAddDialog() },
                            accentColor = Color(uiState.accentColor)
                        )
                        "settings" -> SettingsScreen(
                            currentAccentColor = Color(uiState.accentColor),
                            onAccentColorSelected = { viewModel.setAccentColor(it.value.toLong()) },
                            accentColor = Color(uiState.accentColor)
                        )
                    }
                }
            }
        }
    }
}

private val WorkoutUiState.activePage: String
    get() = "workout"

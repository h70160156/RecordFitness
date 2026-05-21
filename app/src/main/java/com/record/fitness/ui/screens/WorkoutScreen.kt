package com.record.fitness.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.record.fitness.data.model.*
import com.record.fitness.ui.components.*
import com.record.fitness.ui.theme.DarkSurfaceVariant

@Composable
fun WorkoutScreen(
    uiState: com.record.fitness.ui.WorkoutUiState,
    onMuscleGroupSelected: (String) -> Unit,
    onExerciseSelected: (Exercise) -> Unit,
    onAddRecord: () -> Unit,
    onRecordAdded: (String, String, String, Float, Int, Int) -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF007AFF)
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "健身记录",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            MuscleGroupSelector(
                groups = MuscleGroups.groups,
                selectedGroup = uiState.selectedMuscleGroup,
                onGroupSelected = onMuscleGroupSelected,
                modifier = Modifier.padding(horizontal = 20.dp),
                accentColor = accentColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            val filteredExercises = uiState.exercises.filter {
                it.muscleGroup == uiState.selectedMuscleGroup
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(filteredExercises) { exercise ->
                    val records = uiState.exerciseRecords[exercise.id] ?: emptyList()
                    ExerciseCard(
                        exercise = exercise,
                        recordCount = records.size,
                        lastWeight = records.firstOrNull()?.weight,
                        onClick = { onExerciseSelected(exercise) }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(bottom = 16.dp)
        ) {
            GlassBottomNavigation(
                items = listOf(
                    NavItem("workout", "训练", "💪"),
                    NavItem("settings", "设置", "⚙️")
                ),
                selectedItem = "workout",
                onItemSelected = {},
                accentColor = accentColor
            )
        }

        if (uiState.showAddDialog && uiState.selectedExercise != null) {
            AddRecordDialog(
                exercise = uiState.selectedExercise,
                onDismiss = onDismissDialog,
                onConfirm = { weight, reps, sets ->
                    val exercise = uiState.selectedExercise!!
                    onRecordAdded(exercise.id, exercise.name, exercise.muscleGroup, weight, reps, sets)
                },
                accentColor = accentColor
            )
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    recordCount: Int,
    lastWeight: Float?,
    onClick: () -> Unit
) {
    LiquidGlassCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = exercise.icon, style = MaterialTheme.typography.headlineSmall)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Text(
                        text = if (recordCount > 0) "最近: ${lastWeight?.toInt()}kg" else "暂无记录",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                if (exercise.currentPR > 0) {
                    Text(
                        text = "PR",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFFF9500)
                    )
                    Text(
                        text = "${exercise.currentPR.toInt()}kg",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "+ 添加",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

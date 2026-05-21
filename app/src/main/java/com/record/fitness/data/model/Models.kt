package com.record.fitness.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_records")
data class WorkoutRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val exerciseId: String,
    val exerciseName: String,
    val muscleGroup: String,
    val weight: Float,
    val reps: Int,
    val sets: Int,
    val volume: Float,
    val isPR: Boolean,
    val timestamp: Long,
    val date: String
)

data class Exercise(
    val id: String,
    val name: String,
    val muscleGroup: String,
    val icon: String,
    var currentPR: Float = 0f
)

data class MuscleGroup(
    val id: String,
    val name: String,
    val icon: String
)

object MuscleGroups {
    val groups = listOf(
        MuscleGroup("chest", "胸", "💪"),
        MuscleGroup("back", "背", "🏋️"),
        MuscleGroup("legs", "腿", "🦵"),
        MuscleGroup("arms", "手臂", "✊"),
        MuscleGroup("shoulders", "肩", "👊")
    )
}

object DefaultExercises {
    val exercises = listOf(
        Exercise("1", "卧推", "chest", "🏋️"),
        Exercise("2", "飞鸟", "chest", "💪"),
        Exercise("3", "俯卧撑", "chest", "🙌"),
        Exercise("4", "引体向上", "back", "🏃"),
        Exercise("5", "硬拉", "back", "🏋️"),
        Exercise("6", "划船", "back", "🚣"),
        Exercise("7", "深蹲", "legs", "🦵"),
        Exercise("8", "腿举", "legs", "🏋️"),
        Exercise("9", "箭步蹲", "legs", "🚶"),
        Exercise("10", "二头弯举", "arms", "✊"),
        Exercise("11", "三头下压", "arms", "💪"),
        Exercise("12", "锤式弯举", "arms", "🔨"),
        Exercise("13", "肩推", "shoulders", "👊"),
        Exercise("14", "侧平举", "shoulders", "✨"),
        Exercise("15", "面拉", "shoulders", "🧵")
    )
}

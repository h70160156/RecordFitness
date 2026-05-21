package com.record.fitness.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.record.fitness.data.model.Exercise
import com.record.fitness.data.model.WorkoutRecord
import com.record.fitness.ui.components.LiquidGlassCard
import com.record.fitness.ui.components.LiquidGlassButton
import com.record.fitness.ui.components.AddRecordDialog

@Composable
fun ExerciseDetailScreen(
    exercise: Exercise,
    records: List<WorkoutRecord>,
    onBack: () -> Unit,
    onAddRecord: () -> Unit,
    onRecordAdded: (Float, Int, Int) -> Unit,
    onDeleteRecord: (WorkoutRecord) -> Unit,
    onDismissDialog: () -> Unit,
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF007AFF)
) {
    val totalVolume = records.sumOf { it.volume.toDouble() }.toFloat()
    val prRecord = records.filter { it.isPR }.firstOrNull()
    val last15Records = records.take(15).reversed()

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(20.dp, 20.dp, 20.dp, 40.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onBack) {
                        Text("← 返回", color = Color.White)
                    }

                    Text(
                        text = "${exercise.icon} ${exercise.name}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )

                    TextButton(onClick = onAddRecord) {
                        Text("+ 添加", color = accentColor)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LiquidGlassCard(modifier = Modifier.weight(1f)) {
                        Column {
                            Text(
                                text = "🏆 个人记录",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "${exercise.currentPR.toInt()} kg",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                        }
                    }

                    LiquidGlassCard(modifier = Modifier.weight(1f)) {
                        Column {
                            Text(
                                text = "📊 训练次数",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "${records.size}",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(
                            text = "💪 累计容量",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "${totalVolume.toInt()} kg",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (last15Records.size > 1) {
                item {
                    LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column {
                            Text(
                                text = "📈 重量趋势",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.7f)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LineChart(
                                data = last15Records.map { it.weight },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                lineColor = accentColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Text(
                    text = "📝 训练历史",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (records.isEmpty()) {
                item {
                    LiquidGlassCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "💪",
                                style = MaterialTheme.typography.displayMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "还没有记录",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Text(
                                text = "点击右上角开始记录你的第一次训练",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            } else {
                items(records) { record ->
                    RecordItem(
                        record = record,
                        onDelete = { onDeleteRecord(record) },
                        accentColor = accentColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (showDialog) {
            AddRecordDialog(
                exercise = exercise,
                onDismiss = onDismissDialog,
                onConfirm = { weight, reps, sets ->
                    onRecordAdded(weight, reps, sets)
                },
                accentColor = accentColor
            )
        }
    }
}

@Composable
private fun RecordItem(
    record: WorkoutRecord,
    onDelete: () -> Unit,
    accentColor: Color
) {
    LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${record.weight.toInt()}kg × ${record.reps} × ${record.sets}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    if (record.isPR) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PR",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFFF9500),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFFF9500).copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = "${record.date} · ${record.volume.toInt()}kg",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }

            TextButton(onClick = onDelete) {
                Text("🗑️", color = Color(0xFFFF3B30))
            }
        }
    }
}

@Composable
private fun LineChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color
) {
    if (data.size < 2) return

    Canvas(modifier = modifier) {
        val maxValue = data.maxOrNull() ?: 1f
        val minValue = data.minOrNull() ?: 0f
        val range = maxValue - minValue

        val stepX = size.width / (data.size - 1)
        val scaleY = if (range > 0) size.height / range else 1f

        val path = Path()
        val fillPath = Path()

        data.forEachIndexed { index, value ->
            val x = index * stepX
            val y = size.height - ((value - minValue) * scaleY)

            if (index == 0) {
                path.moveTo(x, y)
                fillPath.moveTo(x, size.height)
                fillPath.lineTo(x, y)
            } else {
                path.lineTo(x, y)
                fillPath.lineTo(x, y)
            }
        }

        fillPath.lineTo(size.width, size.height)
        fillPath.close()

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    lineColor.copy(alpha = 0.4f),
                    lineColor.copy(alpha = 0f)
                )
            )
        )

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 3f)
        )

        data.forEachIndexed { index, value ->
            val x = index * stepX
            val y = size.height - ((value - minValue) * scaleY)
            drawCircle(
                color = lineColor,
                radius = 6f,
                center = Offset(x, y)
            )
            drawCircle(
                color = Color(0xFF1C1C1E),
                radius = 3f,
                center = Offset(x, y)
            )
        }
    }
}

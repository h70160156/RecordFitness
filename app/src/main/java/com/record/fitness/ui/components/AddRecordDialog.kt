package com.record.fitness.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.record.fitness.data.model.Exercise

@Composable
fun AddRecordDialog(
    exercise: Exercise,
    onDismiss: () -> Unit,
    onConfirm: (weight: Float, reps: Int, sets: Int) -> Unit,
    accentColor: Color = Color(0xFF007AFF)
) {
    var weight by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }

    val isValid = weight.toFloatOrNull() != null &&
            reps.toIntOrNull() != null &&
            sets.toIntOrNull() != null

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1C1C1E),
                            Color(0xFF2C2C2E)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "添加记录",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${exercise.icon} ${exercise.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedInput(
                        value = weight,
                        onValueChange = { weight = it },
                        label = "重量(kg)",
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedInput(
                        value = reps,
                        onValueChange = { reps = it },
                        label = "次数",
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedInput(
                        value = sets,
                        onValueChange = { sets = it },
                        label = "组数",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("取消", color = Color.White.copy(alpha = 0.7f))
                    }

                    LiquidGlassButton(
                        text = "保存",
                        onClick = {
                            val w = weight.toFloatOrNull() ?: return@LiquidGlassButton
                            val r = reps.toIntOrNull() ?: return@LiquidGlassButton
                            val s = sets.toIntOrNull() ?: return@LiquidGlassButton
                            onConfirm(w, r, s)
                        },
                        enabled = isValid,
                        modifier = Modifier.weight(1f),
                        accentColor = accentColor
                    )
                }
            }
        }
    }
}

@Composable
private fun OutlinedInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                onValueChange(newValue)
            }
        },
        label = { Text(label, color = Color.White.copy(alpha = 0.5f)) },
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFF007AFF),
            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
            cursorColor = Color(0xFF007AFF)
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}

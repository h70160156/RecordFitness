package com.record.fitness.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.record.fitness.data.model.AccentColor
import com.record.fitness.ui.components.GlassBottomNavigation
import com.record.fitness.ui.components.LiquidGlassCard
import com.record.fitness.ui.components.NavItem

val accentColors = listOf(
    AccentColor("blue", "蓝色", Color(0xFF007AFF)),
    AccentColor("green", "绿色", Color(0xFF34C759)),
    AccentColor("orange", "橙色", Color(0xFFFF9500)),
    AccentColor("red", "红色", Color(0xFFFF3B30)),
    AccentColor("purple", "紫色", Color(0xFFAF52DE)),
    AccentColor("pink", "粉色", Color(0xFFFF2D55)),
    AccentColor("teal", "青色", Color(0xFF5AC8FA)),
    AccentColor("yellow", "黄色", Color(0xFFFFCC00))
)

@Composable
fun SettingsScreen(
    currentAccentColor: Color,
    onAccentColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF007AFF)
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(20.dp, 20.dp, 20.dp, 100.dp)
        ) {
            item {
                Text(
                    text = "设置",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                LiquidGlassCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "主题颜色",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            accentColors.forEach { color ->
                                ColorOption(
                                    color = color.color,
                                    isSelected = color.color == currentAccentColor,
                                    onClick = { onAccentColorSelected(color.color) }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                LiquidGlassCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "关于",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        SettingsItem(title = "版本", subtitle = "1.0.0")
                        SettingsItem(title = "开发者", subtitle = "Record Fitness Team")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                LiquidGlassCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "数据",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        SettingsItem(title = "导出数据", subtitle = "备份你的训练记录")
                        SettingsItem(title = "导入数据", subtitle = "从备份恢复")
                    }
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
                selectedItem = "settings",
                onItemSelected = {},
                accentColor = accentColor
            )
        }
    }
}

@Composable
private fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .then(
                if (isSelected) {
                    Modifier.border(3.dp, Color.White, CircleShape)
                } else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Text(
                text = "✓",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.6f)
        )
    }
}

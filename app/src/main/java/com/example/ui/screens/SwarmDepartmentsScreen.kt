package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SwarmDepartment
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun SwarmDepartmentsScreen(
    departments: List<SwarmDepartment>,
    modifier: Modifier = Modifier
) {
    val totalEmployees = departments.sumOf { it.humanEquivalent }
    val totalValue = departments.sumOf { it.economicValueCreatedUsd }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("swarm_departments_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardSurface)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "12 AUTONOMOUS DEPARTMENT SWARMS",
                        color = NeonCyan,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Replaces ${"%,d".format(totalEmployees)} elite human executives & engineers on 100% Autopilot.",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Cumulative Value Created:",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "$${"%,.0f".format(totalValue)}",
                            color = NeonEmerald,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        items(departments) { dept ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dept_card_${dept.agentId}")
                    .clip(RoundedCornerShape(12.dp))
                    .background(CardSurface)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dept.agentId,
                            color = NeonCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        StatusBadge(
                            text = "100% AUTOPILOT",
                            color = NeonEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = dept.title,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "DNA: ${dept.dnaEntities}",
                        color = ElectricViolet,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = dept.lastActionDescription,
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Replaces: ${"%,d".format(dept.humanEquivalent)} Staff",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "Value: $${"%,.0f".format(dept.economicValueCreatedUsd)}",
                            color = NeonEmerald,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

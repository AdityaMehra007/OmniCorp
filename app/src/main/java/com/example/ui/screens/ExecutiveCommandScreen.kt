package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GridNode
import com.example.model.LiveLogEvent
import com.example.model.MetricSummary
import com.example.ui.components.MetricStatCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun ExecutiveCommandScreen(
    metrics: MetricSummary,
    nodes: List<GridNode>,
    logs: List<LiveLogEvent>,
    onStepCycle: () -> Unit,
    onToggleAutoPilot: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("executive_command_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header & Autopilot Controls
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hero_banner_card"),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "OMNICORP SOVEREIGN OS",
                                color = NeonCyan,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "1 Founder = 1,000 MNCs + 1,000 Startups",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                        StatusBadge(
                            text = if (metrics.isAutoPilotRunning) "AUTOPILOT: ON" else "AUTOPILOT: PAUSED",
                            color = if (metrics.isAutoPilotRunning) NeonEmerald else EnergyAmber
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "SHA-256 State Root:",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                    Text(
                        text = metrics.sha256StateRoot,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onStepCycle,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("step_cycle_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Bolt, contentDescription = "Step Cycle", tint = ObsidianBg)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "RUN CYCLE #${metrics.currentCycle + 1}",
                                color = ObsidianBg,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        OutlinedButton(
                            onClick = onToggleAutoPilot,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("toggle_autopilot_button"),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (metrics.isAutoPilotRunning) EnergyAmber else NeonEmerald
                            )
                        ) {
                            Icon(
                                if (metrics.isAutoPilotRunning) Icons.Default.Stop else Icons.Default.PlayArrow,
                                contentDescription = "Toggle",
                                tint = if (metrics.isAutoPilotRunning) EnergyAmber else NeonEmerald
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (metrics.isAutoPilotRunning) "PAUSE SWARM" else "START AUTOPILOT",
                                color = if (metrics.isAutoPilotRunning) EnergyAmber else NeonEmerald,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // 4 Key Financial Metrics (2x2 Grid)
        item {
            Text(
                text = "1. CAPITAL & VALUATION METRICS",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricStatCard(
                    label = "Valuation (22x ARR)",
                    value = "$${"%,.1f".format(metrics.impliedValuationUsd / 1_000_000_000.0)}B",
                    subValue = "ARR: $${"%,.1f".format(metrics.annualizedArrUsd / 1_000_000.0)}M/yr",
                    accentColor = NeonEmerald,
                    modifier = Modifier.weight(1f),
                    tag = "card_valuation"
                )
                MetricStatCard(
                    label = "Liquid Treasury",
                    value = "$${"%,.1f".format(metrics.liquidTreasuryUsd / 1_000_000.0)}M",
                    subValue = "Unencumbered Float",
                    accentColor = NeonCyan,
                    modifier = Modifier.weight(1f),
                    tag = "card_treasury"
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricStatCard(
                    label = "Day-1 Float Wired",
                    value = "$${"%,.1f".format(metrics.berkshirePrepaidFloatUsd / 1_000_000.0)}M",
                    subValue = "Berkshire Prepayments",
                    accentColor = ElectricViolet,
                    modifier = Modifier.weight(1f),
                    tag = "card_float"
                )
                MetricStatCard(
                    label = "Net-60 Payables",
                    value = "$${"%,.1f".format(metrics.amazonNet60PayablesUsd / 1_000.0)}k",
                    subValue = "Amazon Negative CCC",
                    accentColor = EnergyAmber,
                    modifier = Modifier.weight(1f),
                    tag = "card_payables"
                )
            }
        }

        // Physical Pod Fleet Quick View
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "2. GLOBAL ENERGY & COMPUTE FLEET (${metrics.totalGpus.let { "%,d".format(it) }} GPUs)",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                StatusBadge(
                    text = "${metrics.physicalPodsCount} SpaceX Pods Active",
                    color = NeonCyan
                )
            }
        }

        items(nodes) { node ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("node_card_${node.nodeId}")
                    .clip(RoundedCornerShape(10.dp))
                    .background(CardSurface)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = node.nodeId,
                            color = NeonCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        StatusBadge(
                            text = if (node.spotPriceKwh <= 0.0) "NEGATIVE PRICE" else "GRID SURPLUS",
                            color = if (node.spotPriceKwh <= 0.0) NeonEmerald else EnergyAmber
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = node.regionName,
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Spot: $${"%.4f".format(node.spotPriceKwh)}/kWh",
                            color = if (node.spotPriceKwh <= 0.0) NeonEmerald else EnergyAmber,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "PUE: ${"%.3f".format(node.pue)}",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "${node.gpusTotal} GPUs",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Live Real-Time Telemetry Log
        item {
            Text(
                text = "3. REAL-TIME AUTOPILOT EXECUTION STREAM",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        items(logs.take(6)) { log ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (log.isAlert) LaserRose.copy(alpha = 0.1f) else CardSurface)
                    .border(1.dp, if (log.isAlert) LaserRose.copy(alpha = 0.5f) else BorderSubtle, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "[${log.timestamp}] ${log.departmentId}",
                            color = if (log.isAlert) LaserRose else NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "+$${"%,.0f".format(log.valueUsd)}",
                            color = NeonEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = log.message,
                        color = TextPrimary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

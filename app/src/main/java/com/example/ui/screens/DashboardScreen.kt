package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MetricSummary
import com.example.model.SwarmDepartment
import com.example.ui.components.MetricStatCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

/**
 * DashboardScreen:
 * A comprehensive executive component visualizing the current status of the
 * 12-Department Swarm Orchestrator alongside core financial, operational, and cryptographic metrics.
 */
@Composable
fun DashboardScreen(
    metrics: MetricSummary,
    departments: List<SwarmDepartment>,
    onStepCycle: () -> Unit,
    onToggleAutoPilot: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDepartment by remember { mutableStateOf<SwarmDepartment?>(null) }
    var filterQuery by remember { mutableStateOf("") }

    val filteredDepartments = remember(departments, filterQuery) {
        if (filterQuery.isBlank()) departments
        else departments.filter {
            it.title.contains(filterQuery, ignoreCase = true) ||
            it.agentId.contains(filterQuery, ignoreCase = true) ||
            it.dnaEntities.contains(filterQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Master Orchestrator Status Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("orchestrator_master_banner"),
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (metrics.isAutoPilotRunning) NeonEmerald else EnergyAmber)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "12-DEPARTMENT SWARM ORCHESTRATOR",
                                color = NeonCyan,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                        }
                        StatusBadge(
                            text = if (metrics.isAutoPilotRunning) "ACTIVE AUTOPILOT" else "MANUAL MODE",
                            color = if (metrics.isAutoPilotRunning) NeonEmerald else EnergyAmber
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Orchestrating 12 autonomous specialized AI divisions. Replaces 50,000 elite human executives and engineers for 1 Solo Sovereign Founder.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Real-Time Control & Status Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onStepCycle,
                            modifier = Modifier
                                .weight(1.2f)
                                .testTag("orchestrator_step_cycle_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Bolt, contentDescription = "Step", tint = ObsidianBg, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DISPATCH CYCLE #${metrics.currentCycle + 1}",
                                color = ObsidianBg,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }

                        OutlinedButton(
                            onClick = onToggleAutoPilot,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("orchestrator_toggle_autopilot_button"),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (metrics.isAutoPilotRunning) EnergyAmber else NeonEmerald
                            )
                        ) {
                            Icon(
                                if (metrics.isAutoPilotRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Toggle",
                                tint = if (metrics.isAutoPilotRunning) EnergyAmber else NeonEmerald,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (metrics.isAutoPilotRunning) "PAUSE" else "RESUME",
                                color = if (metrics.isAutoPilotRunning) EnergyAmber else NeonEmerald,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "State Root: ${metrics.sha256StateRoot.take(16)}...",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "Cycle #${metrics.currentCycle}",
                            color = NeonEmerald,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // Real-Time MetricsOverview: Cumulative Revenue, Net Profit, Treasury Balance with Interactive Charts
        item {
            com.example.ui.components.MetricsOverview(
                metrics = metrics,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Core Financial & Operational Matrix (4-Grid)
        item {
            Text(
                text = "CORE SOVEREIGN METRICS",
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
                    label = "Implied Valuation",
                    value = "$${"%,.1f".format(metrics.impliedValuationUsd / 1_000_000_000.0)}B",
                    subValue = "22x ARR Multiple",
                    accentColor = NeonEmerald,
                    modifier = Modifier.weight(1f),
                    tag = "dashboard_metric_valuation"
                )
                MetricStatCard(
                    label = "Unencumbered Float",
                    value = "$${"%,.1f".format(metrics.liquidTreasuryUsd / 1_000_000.0)}M",
                    subValue = "Liquid Treasury",
                    accentColor = NeonCyan,
                    modifier = Modifier.weight(1f),
                    tag = "dashboard_metric_treasury"
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricStatCard(
                    label = "Prepaid Day-1 Cash",
                    value = "$${"%,.1f".format(metrics.berkshirePrepaidFloatUsd / 1_000_000.0)}M",
                    subValue = "Berkshire Float SLA",
                    accentColor = ElectricViolet,
                    modifier = Modifier.weight(1f),
                    tag = "dashboard_metric_float"
                )
                MetricStatCard(
                    label = "Managed GPU Fleet",
                    value = "${"%,d".format(metrics.totalGpus)}",
                    subValue = "${metrics.physicalPodsCount} SpaceX Pods Active",
                    accentColor = EnergyAmber,
                    modifier = Modifier.weight(1f),
                    tag = "dashboard_metric_gpus"
                )
            }
        }

        // Swarm Orchestrator Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SWARM ORCHESTRATOR STATUS (${filteredDepartments.size}/12 DEPARTMENTS)",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${"%,d".format(departments.sumOf { it.autonomousActionsCount })} Total Actions",
                    color = NeonCyan,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        // Filter / Search Input for Departments
        item {
            OutlinedTextField(
                value = filterQuery,
                onValueChange = { filterQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("swarm_search_field"),
                placeholder = { Text("Filter departments or DNA (e.g., 'Aladdin', 'SpaceX', 'Stripe')", color = TextMuted, fontSize = 12.sp) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = BorderSubtle,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = CardSurface,
                    unfocusedContainerColor = CardSurface
                ),
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    if (filterQuery.isNotEmpty()) {
                        IconButton(onClick = { filterQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = TextSecondary)
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = TextMuted)
                    }
                }
            )
        }

        // Swarm Department Interactive Cards
        items(filteredDepartments) { dept ->
            val isSelected = selectedDepartment?.agentId == dept.agentId

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("swarm_card_${dept.agentId}")
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) CardSurfaceElevated else CardSurface)
                    .border(
                        1.dp,
                        if (isSelected) NeonCyan else BorderSubtle,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        selectedDepartment = if (isSelected) null else dept
                    }
                    .padding(14.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(NeonEmerald)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = dept.agentId,
                                color = NeonCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        StatusBadge(
                            text = "${dept.autonomousActionsCount} ACTIONS",
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
                        text = "Synthesized DNA: ${dept.dnaEntities}",
                        color = ElectricViolet,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = dept.lastActionDescription,
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Replaces ${"%,d".format(dept.humanEquivalent)} Staff",
                            color = TextMuted,
                            fontSize = 11.sp
                        )

                        Text(
                            text = "Economic Value: $${"%,.0f".format(dept.economicValueCreatedUsd)}",
                            color = NeonEmerald,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Expandable Detail Drawer
                    AnimatedVisibility(
                        visible = isSelected,
                        enter = fadeIn(tween(200)) + slideInVertically(tween(200))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CardSurfaceElevated)
                                .border(1.dp, BorderSubtle, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "AUTONOMOUS OPERATIONAL PROTOCOL",
                                color = NeonCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Operating 24/7/365 with automated checkpointing, zero human latency, and continuous cryptographic hash chain validation. Reallocates workload across optimal renewable nodes without manual intervention.",
                                color = TextPrimary,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MetricSummary
import com.example.model.PerformancePoint
import com.example.ui.theme.*

enum class ChartMetricFilter {
    CUMULATIVE_REVENUE,
    NET_PROFIT,
    TREASURY_BALANCE
}

/**
 * MetricsOverview Component:
 * Utilizes high-fidelity interactive data cards and custom Jetpack Compose Canvas charts
 * to present real-time performance indicators: Cumulative Revenue, Net Profit, and Treasury Balance.
 */
@Composable
fun MetricsOverview(
    metrics: MetricSummary,
    modifier: Modifier = Modifier
) {
    var selectedMetric by remember { mutableStateOf(ChartMetricFilter.CUMULATIVE_REVENUE) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("metrics_overview_component"),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(NeonCyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShowChart,
                            contentDescription = "Performance Chart",
                            tint = NeonCyan,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "METRICS OVERVIEW",
                            color = NeonCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Real-time Autonomous Financial Telemetry",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }

                StatusBadge(
                    text = "CYCLE #${metrics.currentCycle}",
                    color = NeonEmerald
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3 Interactive Metric Selector Cards (Cumulative Revenue, Net Profit, Treasury Balance)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Card 1: Cumulative Revenue
                InteractiveMetricCard(
                    title = "Cumulative Revenue",
                    value = "$${"%,.1f".format(metrics.cumulativeRevenueUsd / 1_000_000.0)}M",
                    subtext = "Gross Cash In",
                    icon = Icons.Default.TrendingUp,
                    accentColor = NeonCyan,
                    isSelected = selectedMetric == ChartMetricFilter.CUMULATIVE_REVENUE,
                    onClick = { selectedMetric = ChartMetricFilter.CUMULATIVE_REVENUE },
                    modifier = Modifier.weight(1f),
                    tag = "card_cumulative_revenue"
                )

                // Card 2: Net Profit
                InteractiveMetricCard(
                    title = "Net Profit",
                    value = "$${"%,.1f".format(metrics.netProfitUsd / 1_000_000.0)}M",
                    subtext = "Margin: 94.2%",
                    icon = Icons.Default.ShowChart,
                    accentColor = NeonEmerald,
                    isSelected = selectedMetric == ChartMetricFilter.NET_PROFIT,
                    onClick = { selectedMetric = ChartMetricFilter.NET_PROFIT },
                    modifier = Modifier.weight(1f),
                    tag = "card_net_profit"
                )

                // Card 3: Treasury Balance
                InteractiveMetricCard(
                    title = "Treasury Balance",
                    value = "$${"%,.1f".format(metrics.liquidTreasuryUsd / 1_000_000.0)}M",
                    subtext = "Float Reserve",
                    icon = Icons.Default.AccountBalance,
                    accentColor = ElectricViolet,
                    isSelected = selectedMetric == ChartMetricFilter.TREASURY_BALANCE,
                    onClick = { selectedMetric = ChartMetricFilter.TREASURY_BALANCE },
                    modifier = Modifier.weight(1f),
                    tag = "card_treasury_balance"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Real-Time Canvas Trajectory Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CardSurfaceElevated)
                    .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                val history = metrics.performanceHistory.ifEmpty {
                    listOf(
                        PerformancePoint(0, 0.0, 0.0, 1450000.0),
                        PerformancePoint(1, 1365000.0, 1250000.0, 1820000.0),
                        PerformancePoint(2, 2040000.0, 1910000.0, 2240000.0)
                    )
                }

                val dataPoints = remember(history, selectedMetric) {
                    history.map { point ->
                        when (selectedMetric) {
                            ChartMetricFilter.CUMULATIVE_REVENUE -> point.cumulativeRevenueUsd
                            ChartMetricFilter.NET_PROFIT -> point.netProfitUsd
                            ChartMetricFilter.TREASURY_BALANCE -> point.treasuryBalanceUsd
                        }
                    }
                }

                val activeColor = when (selectedMetric) {
                    ChartMetricFilter.CUMULATIVE_REVENUE -> NeonCyan
                    ChartMetricFilter.NET_PROFIT -> NeonEmerald
                    ChartMetricFilter.TREASURY_BALANCE -> ElectricViolet
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (selectedMetric) {
                                ChartMetricFilter.CUMULATIVE_REVENUE -> "CUMULATIVE REVENUE TRAJECTORY"
                                ChartMetricFilter.NET_PROFIT -> "NET OPERATING PROFIT TRAJECTORY"
                                ChartMetricFilter.TREASURY_BALANCE -> "LIQUID TREASURY BALANCE TRAJECTORY"
                            },
                            color = activeColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "${history.size} Cycles Tracked",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    PerformanceTrajectoryChart(
                        values = dataPoints,
                        lineColor = activeColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Footer Telemetry Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(NeonEmerald)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Negative Working Capital (Amazon CCC: -60 Days)",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                }

                Text(
                    text = "Zero Dilution Float",
                    color = EnergyAmber,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun InteractiveMetricCard(
    title: String,
    value: String,
    subtext: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tag: String
) {
    val bg = if (isSelected) accentColor.copy(alpha = 0.14f) else CardSurfaceElevated
    val border = if (isSelected) accentColor else BorderSubtle

    Box(
        modifier = modifier
            .testTag(tag)
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(14.dp)
                )
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = accentColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                color = TextPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )

            Text(
                text = subtext,
                color = TextMuted,
                fontSize = 9.sp,
                maxLines = 1
            )
        }
    }
}

/**
 * Custom Canvas chart that draws smooth trend lines, gradient fill, and data points
 */
@Composable
fun PerformanceTrajectoryChart(
    values: List<Double>,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (values.size < 2) return@Canvas

        val w = size.width
        val h = size.height
        val minVal = values.minOrNull() ?: 0.0
        val maxVal = (values.maxOrNull() ?: 1.0).coerceAtLeast(minVal + 1.0)
        val range = maxVal - minVal

        val points = values.mapIndexed { index, v ->
            val x = (index.toFloat() / (values.size - 1)) * w
            val y = h - (((v - minVal) / range).toFloat() * (h - 16f)) - 8f
            Offset(x, y)
        }

        // 1. Draw Subtle Grid Guide Lines
        drawLine(
            color = Color(0xFF334155).copy(alpha = 0.5f),
            start = Offset(0f, h * 0.25f),
            end = Offset(w, h * 0.25f),
            strokeWidth = 1f
        )
        drawLine(
            color = Color(0xFF334155).copy(alpha = 0.5f),
            start = Offset(0f, h * 0.75f),
            end = Offset(w, h * 0.75f),
            strokeWidth = 1f
        )

        // 2. Build and Draw Gradient Fill Path Under Curve
        val fillPath = Path().apply {
            moveTo(points.first().x, h)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(points.last().x, h)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(lineColor.copy(alpha = 0.28f), lineColor.copy(alpha = 0.02f)),
                startY = 0f,
                endY = h
            )
        )

        // 3. Draw Main Metric Trajectory Line
        val strokePath = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }

        drawPath(
            path = strokePath,
            color = lineColor,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // 4. Draw Highlight Points on Each Step
        points.forEach { pt ->
            drawCircle(
                color = CardSurface,
                radius = 5.dp.toPx(),
                center = pt
            )
            drawCircle(
                color = lineColor,
                radius = 3.dp.toPx(),
                center = pt
            )
        }
    }
}

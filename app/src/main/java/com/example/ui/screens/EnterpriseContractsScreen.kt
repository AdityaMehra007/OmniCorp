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
import com.example.model.EnterpriseContract
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@Composable
fun EnterpriseContractsScreen(
    contracts: List<EnterpriseContract>,
    modifier: Modifier = Modifier
) {
    val totalPrepaidFloat = contracts.sumOf { it.prepaidFloatCollectedUsd }
    val totalNet60Payables = contracts.sumOf { it.net60SupplierCostUsd }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("enterprise_contracts_screen"),
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
                        text = "BERKSHIRE FLOAT & AMAZON NET-60 PIPELINE",
                        color = NeonCyan,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Enterprise AI clients pay 100% upfront on Day 1; power & bare-metal suppliers settle Net-60.",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Total Day-1 Float Collected", color = TextMuted, fontSize = 11.sp)
                            Text(
                                "$${"%,.0f".format(totalPrepaidFloat)}",
                                color = NeonEmerald,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Net-60 Deferred Payables", color = TextMuted, fontSize = 11.sp)
                            Text(
                                "$${"%,.0f".format(totalNet60Payables)}",
                                color = EnergyAmber,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }

        items(contracts) { contract ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("contract_card_${contract.contractId}")
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
                            text = contract.contractId,
                            color = NeonCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        StatusBadge(
                            text = "DAY-1 WIRED",
                            color = NeonEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = contract.clientName,
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Bundle: ${contract.bundleType} (${contract.gpusAllocated} GPUs @ $${"%.2f".format(contract.clearingPricePerHrUsd)}/hr)",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Upfront Cash Float", color = TextMuted, fontSize = 10.sp)
                            Text(
                                "$${"%,.0f".format(contract.prepaidFloatCollectedUsd)}",
                                color = NeonEmerald,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Column {
                            Text("Supplier Net-60", color = TextMuted, fontSize = 10.sp)
                            Text(
                                "$${"%,.0f".format(contract.net60SupplierCostUsd)}",
                                color = EnergyAmber,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Assigned Node", color = TextMuted, fontSize = 10.sp)
                            Text(
                                contract.assignedNodeId,
                                color = TextPrimary,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Stripe API Key: ${contract.stripeApiKey}",
                        color = ElectricViolet,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

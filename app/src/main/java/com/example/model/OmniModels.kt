package com.example.model

data class PerformancePoint(
    val cycle: Int,
    val cumulativeRevenueUsd: Double,
    val netProfitUsd: Double,
    val treasuryBalanceUsd: Double
)

data class MetricSummary(
    val impliedValuationUsd: Double,
    val liquidTreasuryUsd: Double,
    val amazonNet60PayablesUsd: Double,
    val berkshirePrepaidFloatUsd: Double,
    val annualizedArrUsd: Double,
    val grossComputeRevenueUsd: Double,
    val netProfitUsd: Double,
    val cumulativeRevenueUsd: Double,
    val appleEcosystemTaxUsd: Double,
    val teslaAutobidderProfitUsd: Double,
    val blackRockFloatYieldUsd: Double,
    val totalGpus: Int,
    val physicalPodsCount: Int,
    val totalNodes: Int,
    val currentCycle: Int,
    val sha256StateRoot: String,
    val isAutoPilotRunning: Boolean,
    val performanceHistory: List<PerformancePoint> = emptyList()
)

enum class NodeTier {
    PHYSICAL_STRANDED,
    ZERO_CAPEX_BROKER
}

data class GridNode(
    val nodeId: String,
    val regionName: String,
    val tier: NodeTier,
    val gpusTotal: Int,
    val gpusAvailable: Int,
    val spotPriceKwh: Double,
    val reserveGpuHrUsd: Double,
    val pue: Double,
    val latencyMs: Double,
    val carbonFree: Boolean,
    val isOnline: Boolean = true
)

data class SwarmDepartment(
    val agentId: String,
    val title: String,
    val dnaEntities: String,
    val humanEquivalent: Int,
    val autonomousActionsCount: Int,
    val economicValueCreatedUsd: Double,
    val currentStatus: String,
    val lastActionDescription: String
)

data class EnterpriseContract(
    val contractId: String,
    val clientName: String,
    val bundleType: String,
    val gpusAllocated: Int,
    val clearingPricePerHrUsd: Double,
    val prepaidFloatCollectedUsd: Double,
    val net60SupplierCostUsd: Double,
    val assignedNodeId: String,
    val stripeApiKey: String,
    val cycleMined: Int,
    val sha256Certificate: String
)

data class LiveLogEvent(
    val id: String,
    val timestamp: String,
    val cycle: Int,
    val departmentId: String,
    val message: String,
    val valueUsd: Double,
    val isAlert: Boolean = false
)

data class AsicBOMBreakdown(
    val chipName: String,
    val processNode: String,
    val bareDieCostUsd: Double,
    val hbm3eStackCostUsd: Double,
    val cowosPackagingCostUsd: Double,
    val totalAsicBomUsd: Double,
    val merchantGpuPriceUsd: Double,
    val capexReductionPct: Double,
    val pod1024ChipsSavingsUsd: Double
)

data class WattFlopOrder(
    val orderId: String,
    val partyType: String, // "AI_BUYER" or "ENERGY_SELLER"
    val actor: String,
    val contractsWfi: Int,
    val priceUsd: Double,
    val clearedAtPriceUsd: Double,
    val status: String
)

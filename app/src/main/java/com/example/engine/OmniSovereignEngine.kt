package com.example.engine

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * OmniSovereignEngine runs the complete 12-Titan Apex Synthesis + 12-Department Swarm loop:
 * - Vickrey Second-Price Auction Clearing
 * - Berkshire Hathaway Upfront Float Accumulation
 * - Amazon Net-60 Deferred Supplier Payables
 * - BlackRock Aladdin Overnight Repo Yield
 * - Tesla Autobidder Peak Power Grid Response
 * - SpaceX $380k Immersion Pod Auto-Replication
 * - Apple 12% Ecosystem Agent Marketplace Tax
 * - 4.5s Live Workload Migration on Grid Spikes
 */
class OmniSovereignEngine {

    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)

    private val initialNodes = listOf(
        GridNode(
            nodeId = "IND-GIFT-SOLAR-01",
            regionName = "Bhadla/Khavda Solar Grid (India)",
            tier = NodeTier.PHYSICAL_STRANDED,
            gpusTotal = 1024,
            gpusAvailable = 1024,
            spotPriceKwh = -0.0185,
            reserveGpuHrUsd = 0.38,
            pue = 1.025,
            latencyMs = 28.0,
            carbonFree = true
        ),
        GridNode(
            nodeId = "USA-ERCOT-WIND-01",
            regionName = "West Texas Wind & Megapack (USA)",
            tier = NodeTier.PHYSICAL_STRANDED,
            gpusTotal = 1024,
            gpusAvailable = 1024,
            spotPriceKwh = -0.0240,
            reserveGpuHrUsd = 0.40,
            pue = 1.028,
            latencyMs = 19.0,
            carbonFree = true
        ),
        GridNode(
            nodeId = "NOR-HYDRO-ARCTIC-01",
            regionName = "Narvik Hydro Immersion Dam (Norway)",
            tier = NodeTier.PHYSICAL_STRANDED,
            gpusTotal = 2048,
            gpusAvailable = 2048,
            spotPriceKwh = 0.0052,
            reserveGpuHrUsd = 0.48,
            pue = 1.018,
            latencyMs = 32.0,
            carbonFree = true
        ),
        GridNode(
            nodeId = "GLOBAL-SPOT-MESH-01",
            regionName = "Zero-CapEx Multi-Cloud Bare-Metal Broker",
            tier = NodeTier.ZERO_CAPEX_BROKER,
            gpusTotal = 4096,
            gpusAvailable = 4096,
            spotPriceKwh = 0.0680,
            reserveGpuHrUsd = 0.92,
            pue = 1.180,
            latencyMs = 12.0,
            carbonFree = false
        )
    )

    private val initialDepartments = listOf(
        SwarmDepartment(
            agentId = "AGENT_01_EXEC",
            title = "Executive & Capital Allocation",
            dnaEntities = "Rockefeller + Bezos + Musk + Jensen Cortex",
            humanEquivalent = 500,
            autonomousActionsCount = 18,
            economicValueCreatedUsd = 450000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Enforced 0-dilution CapEx reinvestment and OKR governance."
        ),
        SwarmDepartment(
            agentId = "AGENT_02_QUANT",
            title = "Global Power & Spot Arbitrage",
            dnaEntities = "Goldman Sachs + McKinsey + Citadel Arbitrage",
            humanEquivalent = 2500,
            autonomousActionsCount = 64,
            economicValueCreatedUsd = 1280000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Secured 25MW curtailed solar off-take at -$0.019/kWh floor."
        ),
        SwarmDepartment(
            agentId = "AGENT_03_DEVOPS",
            title = "Zero-Downtime Autonomous DevOps",
            dnaEntities = "Stripe + Vercel + Linear + Cursor Code Factory",
            humanEquivalent = 8000,
            autonomousActionsCount = 92,
            economicValueCreatedUsd = 950000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Deployed Omni-CUDA compilation matrix across global edge nodes."
        ),
        SwarmDepartment(
            agentId = "AGENT_04_GROWTH",
            title = "Jio-Scale Outbound & Free Solar Keys",
            dnaEntities = "Reliance Jio + Clay + Apollo + HubSpot Outbound",
            humanEquivalent = 6000,
            autonomousActionsCount = 140,
            economicValueCreatedUsd = 840000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Provisioned 128 free developer keys during midday solar surplus."
        ),
        SwarmDepartment(
            agentId = "AGENT_05_CLOSER",
            title = "Enterprise Deal Desk & Float SLAs",
            dnaEntities = "Microsoft + Palantir + Salesforce Deal Desk",
            humanEquivalent = 5000,
            autonomousActionsCount = 42,
            economicValueCreatedUsd = 5436000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Closed 3 enterprise prepaid contracts with Day-1 wire settlement."
        ),
        SwarmDepartment(
            agentId = "AGENT_06_CFO",
            title = "Aladdin Float & Negative CCC Treasury",
            dnaEntities = "Berkshire Float + BlackRock Aladdin Treasury",
            humanEquivalent = 3000,
            autonomousActionsCount = 55,
            economicValueCreatedUsd = 620000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Swept unencumbered cash into overnight repo (5.3% annual yield)."
        ),
        SwarmDepartment(
            agentId = "AGENT_07_SPACEX",
            title = "SpaceX Pods & Tesla Autobidder SCADA",
            dnaEntities = "SpaceX Pod Builder + Tesla Megapack SCADA",
            humanEquivalent = 7500,
            autonomousActionsCount = 38,
            economicValueCreatedUsd = 1850000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Triggered 4.5s battery discharge during evening peak grid spike."
        ),
        SwarmDepartment(
            agentId = "AGENT_08_BELL",
            title = "Bell Labs Custom Silicon & FP4 Kernels",
            dnaEntities = "Bell Labs + DeepMind + TSMC Silicon/Kernel R&D",
            humanEquivalent = 4000,
            autonomousActionsCount = 28,
            economicValueCreatedUsd = 1650000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Synthesized fused FP4 attention kernel; slashed watt-per-token by 21%."
        ),
        SwarmDepartment(
            agentId = "AGENT_09_IFSC",
            title = "Delaware C-Corp + GIFT City 0% Tax",
            dnaEntities = "Skadden + Delaware + GIFT City 0%-Tax Shield",
            humanEquivalent = 1500,
            autonomousActionsCount = 19,
            economicValueCreatedUsd = 880000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Settled Delaware-to-GIFT transfer at 0% corporate tax under Sec 80LA."
        ),
        SwarmDepartment(
            agentId = "AGENT_10_PR",
            title = "Apple-Grade Category Evangelism",
            dnaEntities = "Apple Keynote + NVIDIA GTC Category King",
            humanEquivalent = 2000,
            autonomousActionsCount = 31,
            economicValueCreatedUsd = 340000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Published real-time benchmark: 42% cheaper than AWS p5.48xlarge."
        ),
        SwarmDepartment(
            agentId = "AGENT_11_SCM",
            title = "Toyota & Foxconn Direct OEM Procurement",
            dnaEntities = "Toyota + Foxconn Direct Factory Buyer (India/Taiwan)",
            humanEquivalent = 6500,
            autonomousActionsCount = 47,
            economicValueCreatedUsd = 920000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Direct-ordered Vadodara transformers at 80% discount vs Schneider."
        ),
        SwarmDepartment(
            agentId = "AGENT_12_SEC",
            title = "Zero-Trust Cryptographic Mesh & SOC",
            dnaEntities = "CrowdStrike + Cloudflare Cryptographic SOC",
            humanEquivalent = 3500,
            autonomousActionsCount = 88,
            economicValueCreatedUsd = 420000.0,
            currentStatus = "ONLINE_AUTOPILOT",
            lastActionDescription = "Verified 100% SHA-256 chain integrity across all ledger blocks."
        )
    )

    private val initialContracts = listOf(
        EnterpriseContract(
            contractId = "CTR-C1-ALPHA",
            clientName = "Citadel-Quant-Trading-AI",
            bundleType = "ALADDIN_QUANT_PRO",
            gpusAllocated = 1024,
            clearingPricePerHrUsd = 2.45,
            prepaidFloatCollectedUsd = 735000.0,
            net60SupplierCostUsd = 32000.0,
            assignedNodeId = "IND-GIFT-SOLAR-01",
            stripeApiKey = "sk_omni_live_4f9a1b2c",
            cycleMined = 1,
            sha256Certificate = "a8f3b219e44d89b109c7e22c55a8f1b7"
        ),
        EnterpriseContract(
            contractId = "CTR-C1-BETA",
            clientName = "Reliance-Bharat-LLM",
            bundleType = "JIO_SOVEREIGN_SCALE",
            gpusAllocated = 1024,
            clearingPricePerHrUsd = 2.10,
            prepaidFloatCollectedUsd = 630000.0,
            net60SupplierCostUsd = 24000.0,
            assignedNodeId = "USA-ERCOT-WIND-01",
            stripeApiKey = "sk_omni_live_8e7d6c5b",
            cycleMined = 1,
            sha256Certificate = "5d9028c14f9032e1a6b7823f9100e4d2"
        ),
        EnterpriseContract(
            contractId = "CTR-C2-GAMMA",
            clientName = "BioTech-Protein-Fold-Lab",
            bundleType = "OMNI_CUDA_ENTERPRISE",
            gpusAllocated = 1024,
            clearingPricePerHrUsd = 2.25,
            prepaidFloatCollectedUsd = 675000.0,
            net60SupplierCostUsd = 28000.0,
            assignedNodeId = "NOR-HYDRO-ARCTIC-01",
            stripeApiKey = "sk_omni_live_3c2b1a09",
            cycleMined = 2,
            sha256Certificate = "9f4e2a11c8b3710d02a7b8e1f5c6d3a8"
        )
    )

    private val initialLogs = listOf(
        LiveLogEvent(
            id = "LOG-01",
            timestamp = "10:24:12",
            cycle = 1,
            departmentId = "AGENT_06_CFO",
            message = "Collected $2,040,000.00 Day-1 Berkshire Float from 3 enterprise contracts.",
            valueUsd = 2040000.0
        ),
        LiveLogEvent(
            id = "LOG-02",
            timestamp = "10:24:45",
            cycle = 1,
            departmentId = "AGENT_07_SPACEX",
            message = "Autonomous CapEx Loop triggered: Ordered 1,024-GPU Immersion Pod with 100% Float.",
            valueUsd = 380000.0
        ),
        LiveLogEvent(
            id = "LOG-03",
            timestamp = "10:25:30",
            cycle = 2,
            departmentId = "AGENT_02_QUANT",
            message = "ERCOT spot price spiked to $0.28/kWh; auto-migrated 512 GPUs to Bhadla Solar node in 4.2s.",
            valueUsd = 45000.0,
            isAlert = true
        ),
        LiveLogEvent(
            id = "LOG-04",
            timestamp = "10:26:10",
            cycle = 2,
            departmentId = "AGENT_08_BELL",
            message = "Custom 2nm Omni-ASIC tapeout simulation verified: $1,652 BOM vs $38k merchant GPU.",
            valueUsd = 37200000.0
        )
    )

    val asicBOM = AsicBOMBreakdown(
        chipName = "Omni-ASIC N2 Matrix Accelerator",
        processNode = "TSMC 2nm Nanosheet GAA + CoWoS-L",
        bareDieCostUsd = 422.41,
        hbm3eStackCostUsd = 920.00,
        cowosPackagingCostUsd = 310.00,
        totalAsicBomUsd = 1652.41,
        merchantGpuPriceUsd = 38000.00,
        capexReductionPct = 95.65,
        pod1024ChipsSavingsUsd = 37219932.16
    )

    val wattFlopOrders = listOf(
        WattFlopOrder("WFI-01", "AI_BUYER", "OpenAI-Agent-Swarm", 1200, 145.0, 118.5, "CLEARED"),
        WattFlopOrder("WFI-02", "ENERGY_SELLER", "Rajasthan-Solar-IPP", 1400, 92.0, 118.5, "CLEARED"),
        WattFlopOrder("WFI-03", "AI_BUYER", "Reliance-Jio-Cloud", 1500, 138.0, 116.5, "CLEARED"),
        WattFlopOrder("WFI-04", "ENERGY_SELLER", "Gujarat-Khavda-Grid", 1200, 95.0, 116.5, "CLEARED")
    )

    private val performanceHistoryList = mutableListOf(
        PerformancePoint(cycle = 0, cumulativeRevenueUsd = 0.0, netProfitUsd = 0.0, treasuryBalanceUsd = 1450000.0),
        PerformancePoint(cycle = 1, cumulativeRevenueUsd = 1365000.0, netProfitUsd = 1250000.0, treasuryBalanceUsd = 1820000.0),
        PerformancePoint(cycle = 2, cumulativeRevenueUsd = 2040000.0, netProfitUsd = 1910000.0, treasuryBalanceUsd = 2240000.0)
    )

    private val _nodesFlow = MutableStateFlow(initialNodes)
    val nodesFlow: StateFlow<List<GridNode>> = _nodesFlow.asStateFlow()

    private val _departmentsFlow = MutableStateFlow(initialDepartments)
    val departmentsFlow: StateFlow<List<SwarmDepartment>> = _departmentsFlow.asStateFlow()

    private val _contractsFlow = MutableStateFlow(initialContracts)
    val contractsFlow: StateFlow<List<EnterpriseContract>> = _contractsFlow.asStateFlow()

    private val _logsFlow = MutableStateFlow(initialLogs)
    val logsFlow: StateFlow<List<LiveLogEvent>> = _logsFlow.asStateFlow()

    private val _metricsFlow = MutableStateFlow(calculateMetrics(initialNodes, initialContracts, 2, "7c319f80e24b11a890d56c42f19002a38471c55e99b0124d8a76e11209f834a1", true))
    val metricsFlow: StateFlow<MetricSummary> = _metricsFlow.asStateFlow()

    private var currentCycleCounter = 2
    private var lastStateHash = "7c319f80e24b11a890d56c42f19002a38471c55e99b0124d8a76e11209f834a1"

    private fun sha256(data: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(data.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun calculateMetrics(
        nodes: List<GridNode>,
        contracts: List<EnterpriseContract>,
        cycle: Int,
        hash: String,
        isAutoPilot: Boolean
    ): MetricSummary {
        val totalGpus = nodes.sumOf { it.gpusTotal }
        val physicalPods = nodes.count { it.tier == NodeTier.PHYSICAL_STRANDED }
        val grossRevenue = contracts.sumOf { it.prepaidFloatCollectedUsd }
        val net60Payables = contracts.sumOf { it.net60SupplierCostUsd }
        val appleTax = grossRevenue * 0.12
        val teslaProfit = 18500.0 * cycle
        val blackRockYield = (grossRevenue - net60Payables) * 0.016 * cycle
        val cumulativeRevenue = grossRevenue + appleTax + teslaProfit + blackRockYield
        val netProfit = cumulativeRevenue - net60Payables - (physicalPods * 65000.0) // Operational CapEx amortized
        val liquidTreasury = (grossRevenue + appleTax + teslaProfit + blackRockYield) - (physicalPods * 380000.0) - net60Payables
        val annualizedArr = cumulativeRevenue * 52.0
        val impliedValuation = annualizedArr * 22.0

        return MetricSummary(
            impliedValuationUsd = impliedValuation,
            liquidTreasuryUsd = liquidTreasury.coerceAtLeast(1450000.0),
            amazonNet60PayablesUsd = net60Payables,
            berkshirePrepaidFloatUsd = grossRevenue,
            annualizedArrUsd = annualizedArr,
            grossComputeRevenueUsd = grossRevenue,
            netProfitUsd = netProfit.coerceAtLeast(850000.0),
            cumulativeRevenueUsd = cumulativeRevenue,
            appleEcosystemTaxUsd = appleTax,
            teslaAutobidderProfitUsd = teslaProfit,
            blackRockFloatYieldUsd = blackRockYield,
            totalGpus = totalGpus,
            physicalPodsCount = physicalPods,
            totalNodes = nodes.size,
            currentCycle = cycle,
            sha256StateRoot = hash,
            isAutoPilotRunning = isAutoPilot,
            performanceHistory = performanceHistoryList.toList()
        )
    }

    /**
     * Executes one complete autonomous cycle step:
     * - Ingests new enterprise bid
     * - Executes Vickrey clearing
     * - Simulates grid telemetry oscillation
     * - Triggers live migration if needed
     * - Collects Float & Net-60 Payables
     * - Auto-replicates SpaceX Immersion Pods if float threshold met
     */
    fun triggerAutonomousCycleStep() {
        currentCycleCounter++
        val cycle = currentCycleCounter
        val timestamp = dateFormat.format(Date())

        // 1. Grid Telemetry Oscillation
        val updatedNodes = _nodesFlow.value.map { node ->
            val harmonic = sin(cycle.toDouble() * 1.2 + node.nodeId.length) * 0.012
            val newPrice = if (node.nodeId.contains("ERCOT") && cycle % 3 == 0) {
                0.2450 // Grid spike!
            } else {
                node.spotPriceKwh + harmonic
            }
            val newPue = (node.pue + (cos(cycle.toDouble()) * 0.002)).coerceIn(1.015, 1.25)
            node.copy(spotPriceKwh = newPrice, pue = newPue)
        }.toMutableList()

        // 2. Incoming Enterprise Client Closing via Autonomous Outbound (Agent 04 & 05)
        val prospectiveClients = listOf(
            Triple("Frontier-AGI-Lab-Series-B", "ALADDIN_QUANT_PRO", 1024),
            Triple("Apex-Quant-Hedge-Fund", "OMNI_CUDA_ENTERPRISE", 1024),
            Triple("Sovereign-Defense-AI-Command", "PALANTIR_ONTOLOGY_MAX", 1024),
            Triple("Global-Autonomous-Mobility", "JIO_SOVEREIGN_SCALE", 1024)
        )
        val selected = prospectiveClients[cycle % prospectiveClients.size]
        val clientName = "${selected.first}-C$cycle"
        val clearingRate = 2.15 + (sin(cycle.toDouble()) * 0.25).coerceAtLeast(0.0)
        val day1Float = selected.third * clearingRate * 300.0 // 300 compute hours block
        val net60Cost = selected.third * 0.12 * 300.0
        val certHash = sha256("$clientName|$day1Float|$cycle|$lastStateHash")

        val newContract = EnterpriseContract(
            contractId = "CTR-C$cycle-${UUID.randomUUID().toString().take(6).uppercase()}",
            clientName = clientName,
            bundleType = selected.second,
            gpusAllocated = selected.third,
            clearingPricePerHrUsd = clearingRate,
            prepaidFloatCollectedUsd = day1Float,
            net60SupplierCostUsd = net60Cost,
            assignedNodeId = updatedNodes.first().nodeId,
            stripeApiKey = "sk_omni_live_${UUID.randomUUID().toString().replace("-", "").take(14)}",
            cycleMined = cycle,
            sha256Certificate = certHash
        )
        val updatedContracts = listOf(newContract) + _contractsFlow.value

        // 3. Check for Autonomous SpaceX Pod Replication
        val currentFloat = updatedContracts.sumOf { it.prepaidFloatCollectedUsd }
        val currentPods = updatedNodes.count { it.tier == NodeTier.PHYSICAL_STRANDED }
        val neededPods = (currentFloat / 380000.0).toInt().coerceAtMost(25)

        val newLogs = mutableListOf<LiveLogEvent>()
        newLogs.add(
            LiveLogEvent(
                id = "LOG-${UUID.randomUUID().toString().take(6)}",
                timestamp = timestamp,
                cycle = cycle,
                departmentId = "AGENT_05_CLOSER",
                message = "Closed $clientName | Day-1 Float wired: $${"%,.0f".format(day1Float)} (Stripe Key issued).",
                valueUsd = day1Float
            )
        )

        if (neededPods > currentPods) {
            val podIndex = currentPods + 1
            val newPod = GridNode(
                nodeId = "IND-GIFT-POD-0$podIndex",
                regionName = "Autonomous Immersion Pod #$podIndex (GIFT SEZ / Gujarat Solar)",
                tier = NodeTier.PHYSICAL_STRANDED,
                gpusTotal = 1024,
                gpusAvailable = 1024,
                spotPriceKwh = -0.0210,
                reserveGpuHrUsd = 0.36,
                pue = 1.021,
                latencyMs = 26.0,
                carbonFree = true
            )
            updatedNodes.add(newPod)
            newLogs.add(
                LiveLogEvent(
                    id = "LOG-${UUID.randomUUID().toString().take(6)}",
                    timestamp = timestamp,
                    cycle = cycle,
                    departmentId = "AGENT_07_SPACEX",
                    message = "Auto-CapEx: Fabricated & deployed 1,024-GPU Pod #$podIndex with 100% Float (Zero Dilution).",
                    valueUsd = 380000.0
                )
            )
        }

        // Live migration event
        if (cycle % 3 == 0) {
            newLogs.add(
                LiveLogEvent(
                    id = "LOG-${UUID.randomUUID().toString().take(6)}",
                    timestamp = timestamp,
                    cycle = cycle,
                    departmentId = "AGENT_02_QUANT",
                    message = "ERCOT grid LMP surged to $0.245/kWh. Autonomous SCADA live-migrated 1,024 GPUs in 4.1s.",
                    valueUsd = 62000.0,
                    isAlert = true
                )
            )
        }

        // Update departments telemetry
        val updatedDepts = _departmentsFlow.value.map { dept ->
            dept.copy(
                autonomousActionsCount = dept.autonomousActionsCount + (1..3).random(),
                economicValueCreatedUsd = dept.economicValueCreatedUsd + (15000..85000).random().toDouble()
            )
        }

        // Update SHA-256 Hash Root
        lastStateHash = sha256("$cycle|$currentFloat|${updatedNodes.size}|$lastStateHash")

        val newMetrics = calculateMetrics(updatedNodes, updatedContracts, cycle, lastStateHash, _metricsFlow.value.isAutoPilotRunning)
        performanceHistoryList.add(
            PerformancePoint(
                cycle = cycle,
                cumulativeRevenueUsd = newMetrics.cumulativeRevenueUsd,
                netProfitUsd = newMetrics.netProfitUsd,
                treasuryBalanceUsd = newMetrics.liquidTreasuryUsd
            )
        )

        _nodesFlow.value = updatedNodes
        _contractsFlow.value = updatedContracts
        _departmentsFlow.value = updatedDepts
        _logsFlow.update { newLogs + it }
        _metricsFlow.value = newMetrics.copy(performanceHistory = performanceHistoryList.toList())
    }

    fun toggleAutoPilot() {
        val current = _metricsFlow.value.isAutoPilotRunning
        _metricsFlow.update { it.copy(isAutoPilotRunning = !current) }
    }
}

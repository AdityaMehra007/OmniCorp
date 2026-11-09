package com.example

import com.example.engine.OmniSovereignEngine
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class OmniSovereignEngineTest {

    private lateinit var engine: OmniSovereignEngine

    @Before
    fun setUp() {
        engine = OmniSovereignEngine()
    }

    @Test
    fun testInitialEngineState() {
        val metrics = engine.metricsFlow.value
        assertTrue(metrics.impliedValuationUsd > 1_000_000_000.0)
        assertTrue(metrics.liquidTreasuryUsd > 1_000_000.0)
        assertEquals(4, engine.nodesFlow.value.size)
        assertEquals(12, engine.departmentsFlow.value.size)
        assertTrue(engine.contractsFlow.value.isNotEmpty())
    }

    @Test
    fun testCycleStepIncrementsMetricsAndReplicatesPods() {
        val initialCycle = engine.metricsFlow.value.currentCycle
        val initialFloat = engine.metricsFlow.value.berkshirePrepaidFloatUsd

        engine.triggerAutonomousCycleStep()

        val updatedMetrics = engine.metricsFlow.value
        assertEquals(initialCycle + 1, updatedMetrics.currentCycle)
        assertTrue(updatedMetrics.berkshirePrepaidFloatUsd > initialFloat)
        assertTrue(engine.logsFlow.value.isNotEmpty())
    }

    @Test
    fun testAsicBomEconomics() {
        val asic = engine.asicBOM
        assertEquals(1652.41, asic.totalAsicBomUsd, 0.01)
        assertEquals(38000.00, asic.merchantGpuPriceUsd, 0.01)
        assertTrue(asic.capexReductionPct > 90.0)
    }
}

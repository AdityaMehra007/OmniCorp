package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.engine.OmniSovereignEngine
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class OmniViewModel(
    private val engine: OmniSovereignEngine = OmniSovereignEngine()
) : ViewModel() {

    val metrics: StateFlow<MetricSummary> = engine.metricsFlow
    val nodes: StateFlow<List<GridNode>> = engine.nodesFlow
    val departments: StateFlow<List<SwarmDepartment>> = engine.departmentsFlow
    val contracts: StateFlow<List<EnterpriseContract>> = engine.contractsFlow
    val logs: StateFlow<List<LiveLogEvent>> = engine.logsFlow
    val asicBOM: AsicBOMBreakdown = engine.asicBOM
    val wattFlopOrders: List<WattFlopOrder> = engine.wattFlopOrders

    init {
        // Background autopilot timer loop
        viewModelScope.launch {
            while (isActive) {
                delay(4000)
                if (metrics.value.isAutoPilotRunning) {
                    engine.triggerAutonomousCycleStep()
                }
            }
        }
    }

    fun stepCycle() {
        engine.triggerAutonomousCycleStep()
    }

    fun toggleAutoPilot() {
        engine.toggleAutoPilot()
    }
}

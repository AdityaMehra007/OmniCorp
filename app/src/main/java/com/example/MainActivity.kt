package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.CyberTabButton
import com.example.ui.screens.DeepTechSiliconScreen
import com.example.ui.screens.EnterpriseContractsScreen
import com.example.ui.screens.ExecutiveCommandScreen
import com.example.ui.screens.SwarmDepartmentsScreen
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardSurface
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.OmniCorpTheme
import com.example.viewmodel.OmniViewModel

enum class OmniDashboardTab(val title: String) {
    DASHBOARD("DASHBOARD"),
    EXECUTIVE_COMMAND("COMMAND CENTER"),
    SWARM_DEPARTMENTS("12-AGENT SWARMS"),
    ENTERPRISE_FLOAT("FLOAT & CONTRACTS"),
    DEEP_TECH_SILICON("2nm ASIC & WATT-FLOP")
}

class MainActivity : ComponentActivity() {

    private val viewModel: OmniViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OmniCorpTheme {
                val metrics by viewModel.metrics.collectAsStateWithLifecycle()
                val nodes by viewModel.nodes.collectAsStateWithLifecycle()
                val departments by viewModel.departments.collectAsStateWithLifecycle()
                val contracts by viewModel.contracts.collectAsStateWithLifecycle()
                val logs by viewModel.logs.collectAsStateWithLifecycle()

                var currentTab by remember { mutableStateOf(OmniDashboardTab.DASHBOARD) }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ObsidianBg),
                    containerColor = ObsidianBg,
                    contentWindowInsets = WindowInsets.safeDrawing,
                    topBar = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(CardSurface)
                                .windowInsetsPadding(WindowInsets.statusBars)
                                .padding(vertical = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState())
                                    .padding(horizontal = 14.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OmniDashboardTab.values().forEach { tab ->
                                    CyberTabButton(
                                        title = tab.title,
                                        isSelected = currentTab == tab,
                                        onClick = { currentTab = tab },
                                        tag = "tab_${tab.name.lowercase()}"
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(BorderSubtle)
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(ObsidianBg)
                    ) {
                        when (currentTab) {
                            OmniDashboardTab.DASHBOARD -> {
                                com.example.ui.screens.DashboardScreen(
                                    metrics = metrics,
                                    departments = departments,
                                    onStepCycle = { viewModel.stepCycle() },
                                    onToggleAutoPilot = { viewModel.toggleAutoPilot() }
                                )
                            }
                            OmniDashboardTab.EXECUTIVE_COMMAND -> {
                                ExecutiveCommandScreen(
                                    metrics = metrics,
                                    nodes = nodes,
                                    logs = logs,
                                    onStepCycle = { viewModel.stepCycle() },
                                    onToggleAutoPilot = { viewModel.toggleAutoPilot() }
                                )
                            }
                            OmniDashboardTab.SWARM_DEPARTMENTS -> {
                                SwarmDepartmentsScreen(
                                    departments = departments
                                )
                            }
                            OmniDashboardTab.ENTERPRISE_FLOAT -> {
                                EnterpriseContractsScreen(
                                    contracts = contracts
                                )
                            }
                            OmniDashboardTab.DEEP_TECH_SILICON -> {
                                DeepTechSiliconScreen(
                                    asic = viewModel.asicBOM,
                                    orders = viewModel.wattFlopOrders
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

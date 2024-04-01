package com.minux.monitoring.feature.monitoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonitoringFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(
            requireContext()
        ).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                val monitoringViewModel = viewModel<MonitoringViewModel>()
                val state by monitoringViewModel.monitoringState.collectAsStateWithLifecycle()

                MonitoringScreen(
                    monitoringState = state,
                    onEvent = monitoringViewModel::onEvent
                )
            }
        }
    }
}
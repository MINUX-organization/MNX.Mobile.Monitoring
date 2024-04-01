package com.minux.monitoring.feature.wallets

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

class WalletsFragment : Fragment() {
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
                val walletsViewModel = viewModel<WalletsViewModel>()
                val state by walletsViewModel.walletsState.collectAsStateWithLifecycle()

                WalletsScreen(
                    walletsState = state,
                    onEvent = walletsViewModel::onEvent
                )
            }
        }
    }
}
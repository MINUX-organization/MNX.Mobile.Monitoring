package com.minux.monitoring.feature.cryptos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class CryptosFragment : Fragment() {
    private val cryptosViewModel by viewModels<CryptosViewModel>()

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
                val state by cryptosViewModel.cryptosState.collectAsState()
                CryptosScreen(
                    cryptosState = state,
                    onEvent = cryptosViewModel::onEvent
                )
            }
        }
    }
}
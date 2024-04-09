package com.minux.monitoring.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.minux.monitoring.core.designsystem.theme.MNXTheme

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MNXTheme {
                MNXApp()
            }
        }
    }
}
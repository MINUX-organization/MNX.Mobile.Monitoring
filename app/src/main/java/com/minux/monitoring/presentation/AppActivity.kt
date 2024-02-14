package com.minux.monitoring.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minux.monitoring.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityAppBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }
}
package dev.ohoussein.cryptoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import dev.ohoussein.cryptoapp.dev.ohoussein.cryptoapp.createApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createApp()
    }
}

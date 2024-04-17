package dev.ohoussein.cryptoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.ohoussein.tldr.createApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createApp()
    }
}

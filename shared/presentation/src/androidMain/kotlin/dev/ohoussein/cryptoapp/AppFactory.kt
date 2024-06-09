package dev.ohoussein.cryptoapp

import SharedApp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

fun ComponentActivity.createApp() {
    setContent {
        SharedApp()
    }
}

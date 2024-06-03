package dev.ohoussein.cryptoapp

import SharedApp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

fun ComponentActivity.createApp() {
    setContent {
        SharedApp()
    }
}

@Preview
@Composable
internal fun PreviewApp() {
    SharedApp()
}

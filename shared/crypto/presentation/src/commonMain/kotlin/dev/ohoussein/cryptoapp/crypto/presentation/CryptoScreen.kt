package dev.ohoussein.cryptoapp.crypto.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CryptoScreen() {
    Text(
        "CryptoScreen",
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onPrimary,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxSize()
    )
}

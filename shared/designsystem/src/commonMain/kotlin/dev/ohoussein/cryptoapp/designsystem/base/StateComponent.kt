package dev.ohoussein.cryptoapp.designsystem.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cryptoapp.shared.designsystem.generated.resources.Res
import cryptoapp.shared.designsystem.generated.resources.core_retry
import cryptoapp.shared.designsystem.generated.resources.ic_error
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun StateError(
    modifier: Modifier = Modifier,
    message: String,
    icon: Painter = painterResource(Res.drawable.ic_error),
    onRetryClick: (() -> Unit)? = null,
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = icon,
            contentDescription = message,
            modifier = Modifier.padding(12.dp),
        )
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        if (onRetryClick != null) {
            Spacer(modifier = Modifier.padding(24.dp))
            TextButton(onClick = onRetryClick) {
                Text(
                    text = stringResource(Res.string.core_retry),
                )
            }
        }
    }
}

// @Preview(showSystemUi = true, showBackground = true)
@Composable
fun StateLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            Modifier
                .size(32.dp)
        )
    }
}

// @Preview(showSystemUi = true, showBackground = true)
// @Composable
// private fun PreviewErrorState() {
//     StateError(
//         message = "No internet connection",
//         onRetryClick = {}
//     )
// }

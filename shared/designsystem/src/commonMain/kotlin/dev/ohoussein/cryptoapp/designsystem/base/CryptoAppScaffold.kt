package dev.ohoussein.cryptoapp.designsystem.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cryptoapp.shared.designsystem.generated.resources.Res
import cryptoapp.shared.designsystem.generated.resources.core_back
import dev.ohoussein.cryptoapp.designsystem.theme.AppbarFontFamily
import dev.ohoussein.cryptoapp.designsystem.theme.CryptoAppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun CryptoAppScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    CryptoAppTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = topBar,
            content = content,
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.background),
        )
    }
}

@Composable
fun CryptoAppTopBar(
    title: String,
    titlePrefix: @Composable (() -> Unit)? = null,
    onBackButton: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            titlePrefix?.let {
                titlePrefix()
                Spacer(Modifier.width(24.dp))
            }
            Text(
                text = title,
                style = LocalTextStyle.current.copy(fontFamily = AppbarFontFamily),
            )
        },
        navigationIcon = {
            if (onBackButton != null) {
                IconButton(onClick = onBackButton) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.core_back)
                    )
                }
            }
        },
        contentColor = MaterialTheme.colors.onPrimary,
        backgroundColor = MaterialTheme.colors.primary,
    )
}

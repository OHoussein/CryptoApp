package dev.ohoussein.cryptoapp.designsystem.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cryptoapp.shared.designsystem.generated.resources.Res
import cryptoapp.shared.designsystem.generated.resources.core_app_bar_title
import cryptoapp.shared.designsystem.generated.resources.core_back
import cryptoapp.shared.designsystem.generated.resources.ic_coin
import dev.ohoussein.cryptoapp.designsystem.theme.AppbarFontFamily
import dev.ohoussein.cryptoapp.designsystem.theme.CryptoAppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CryptoAppScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onBackButton: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    CryptoAppTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { CryptoAppTopBar(onBackButton) },

            content = content,
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.background),
        )
    }
}

@Composable
fun CryptoAppTopBar(
    onBackButton: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(Res.string.core_app_bar_title),
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
            } else {
                Icon(
                    painter = painterResource(Res.drawable.ic_coin),
                    contentDescription = stringResource(Res.string.core_back),
                    modifier = Modifier.padding(12.dp),
                    tint = Color.Unspecified,
                )
            }
        },
        contentColor = MaterialTheme.colors.onPrimary,
        backgroundColor = MaterialTheme.colors.primary,
    )
}

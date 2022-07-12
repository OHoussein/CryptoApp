package dev.ohoussein.cryptoapp.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ohoussein.cryptoapp.core.presentation.R
import dev.ohoussein.cryptoapp.presentation.theme.AppbarFontFamily
import dev.ohoussein.cryptoapp.presentation.theme.CryptoAppTheme

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

@Preview
@Composable
fun CryptoAppTopBar(
    onBackButton: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.core_app_bar_title),
                style = LocalTextStyle.current.copy(fontFamily = AppbarFontFamily),
            )
        },
        navigationIcon = {
            if (onBackButton != null) {
                IconButton(onClick = onBackButton) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.core_back)
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_coin),
                    contentDescription = stringResource(id = R.string.core_back),
                    modifier = Modifier.padding(12.dp),
                    tint = Color.Unspecified,
                )
            }
        },
        contentColor = MaterialTheme.colors.onPrimary,
        backgroundColor = MaterialTheme.colors.primary,
    )
}

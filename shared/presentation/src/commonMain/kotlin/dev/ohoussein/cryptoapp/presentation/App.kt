import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SharedApp() {
    Text(
        "Hello CMP",
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onPrimary,
    )
}

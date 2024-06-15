package dev.ohoussein.cryptoapp.designsystem.graph.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.ohoussein.cryptoapp.designsystem.graph.model.GraphPoint
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("LongMethod")
@Composable
fun LinearGraph(
    values: List<GraphPoint>,
    color: Color,
    modifier: Modifier = Modifier,
    stroke: Dp = 1.dp,
) {
    if (values.isEmpty()) {
        Box(modifier = modifier)
        return
    }
    val minXValue = remember(values) { values.minOf { it.x } }
    val maxXValue = remember(values) { values.maxOf { it.x } }
    val minYValue = remember(values) { values.minOf { it.y } }
    val maxYValue = remember(values) { values.maxOf { it.y } }

    Box(
        modifier = modifier
            .drawBehind {
                val startPadding = 1.dp.toPx()

                val pixelPoints = values.map {
                    val x = it.x.mapValueToDifferentRange(
                        inMin = minXValue,
                        inMax = maxXValue,
                        outMin = startPadding,
                        outMax = (size.width - startPadding)
                    )

                    val y = it.y.mapValueToDifferentRange(
                        inMin = minYValue,
                        inMax = maxYValue,
                        outMin = (size.height - startPadding),
                        outMax = startPadding,
                    )

                    PixelPoint(x.toFloat(), y.toFloat())
                }

                val path = Path().apply {
                    if (pixelPoints.size > 2) {
                        moveTo(pixelPoints.first().x, pixelPoints.first().y)

                        for (i in 1 until pixelPoints.size) {
                            val p0 = pixelPoints[i - 1]
                            val p1 = pixelPoints[i]

                            val controlX1 = (p0.x + p1.x) / 2
                            val controlY1 = p0.y
                            val controlX2 = (p0.x + p1.x) / 2
                            val controlY2 = p1.y

                            cubicTo(controlX1, controlY1, controlX2, controlY2, p1.x, p1.y)
                        }
                    }
                }

                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round),
                )

                val fillPath = Path().apply {
                    addPath(path)
                    lineTo(pixelPoints.last().x, size.height - startPadding)
                    lineTo(pixelPoints.first().x, size.height - startPadding)
                    close()
                }

                drawPath(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.2f),
                            color.copy(alpha = 0f),
                        ),
                        startY = 0f,
                        endY = size.height / 2,
                    ),
                    path = fillPath,
                    style = Fill,
                )
            }
    )
}

private fun Double.mapValueToDifferentRange(
    inMin: Double,
    inMax: Double,
    outMin: Float,
    outMax: Float,
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

@Preview
@Composable
private fun Preview() {
    LinearGraph(
        values = listOf(
            GraphPoint(20.0, 100.0),
            GraphPoint(21.5, 110.0),
            GraphPoint(23.0, 105.0),
            GraphPoint(24.5, 120.0),
            GraphPoint(26.0, 108.0),
            GraphPoint(27.5, 130.0),
            GraphPoint(29.0, 90.0),
            GraphPoint(30.0, 108.0),
            GraphPoint(32.0, 122.0),
            GraphPoint(34.0, 110.0),
        ),
        color = Color.LightGray,
        modifier = Modifier
            .padding(30.dp)
            .size(300.dp, 200.dp),
    )
}

private data class PixelPoint(
    val x: Float,
    val y: Float,
)

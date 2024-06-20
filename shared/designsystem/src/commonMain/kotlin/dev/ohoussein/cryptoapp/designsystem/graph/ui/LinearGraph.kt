package dev.ohoussein.cryptoapp.designsystem.graph.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.ohoussein.cryptoapp.designsystem.graph.model.GraphPoint
import dev.ohoussein.cryptoapp.designsystem.graph.model.GridPoint
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("LongMethod")
@Composable
fun LinearGraph(
    values: List<GraphPoint>,
    color: Color,
    modifier: Modifier = Modifier,
    stroke: Dp = 2.dp,
    gridColor: Color = Color.LightGray,
    gridStroke: Dp = 0.5.dp,
    gridTextStyle: TextStyle = TextStyle.Default,
    horizontalGridPoints: List<GridPoint>,
    verticalGridPoints: List<GridPoint>,
) {
    if (values.isEmpty()) {
        Box(modifier = modifier)
        return
    }
    val scaledFrame = remember(values) { values.scaledFrame() }
    val textMeasurer: TextMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
            .drawBehind {
                val startPadding = 1.dp.toPx()

                drawGrid(
                    textMeasurer = textMeasurer,
                    textStyle = gridTextStyle,
                    gridColor = gridColor,
                    gridStroke = gridStroke,
                    horizontalGridPoints = horizontalGridPoints,
                    verticalGridPoints = verticalGridPoints,
                )

                val pixelPoints = values.map {
                    val x = it.x.mapValueToDifferentRange(
                        inMin = scaledFrame.minXValue,
                        inMax = scaledFrame.maxXValue,
                        outMin = startPadding,
                        outMax = (size.width - startPadding)
                    )

                    val y = it.y.mapValueToDifferentRange(
                        inMin = scaledFrame.minYValue,
                        inMax = scaledFrame.maxYValue,
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

private fun List<GraphPoint>.scaledFrame() = ScaledFrame(
    minXValue = minOf { it.x },
    maxXValue = maxOf { it.x },
    minYValue = minOf { it.y },
    maxYValue = maxOf { it.y },
)

private fun Double.mapValueToDifferentRange(
    inMin: Double,
    inMax: Double,
    outMin: Float,
    outMax: Float,
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

@Suppress("LongParameterList")
private fun DrawScope.drawGrid(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    gridColor: Color,
    gridStroke: Dp,
    horizontalGridPoints: List<GridPoint>,
    verticalGridPoints: List<GridPoint>,
) {
    for (i in verticalGridPoints.indices) {
        // Vertical grid lines
        val point = verticalGridPoints[i]
        val x = point.position.mapValueToDifferentRange(
            inMin = verticalGridPoints.minOf { it.position },
            inMax = verticalGridPoints.maxOf { it.position },
            outMin = 0f,
            outMax = size.width
        ).toFloat()

        // drawLine(
        //     color = gridColor,
        //     start = Offset(x, 0f),
        //     end = Offset(x, size.height),
        //     strokeWidth = gridStroke.toPx()
        // )

        // Vertical labels
        if (i == 0) continue
        val measuredText = textMeasurer.measure(
            point.label,
            style = textStyle,
        )
        drawText(
            textLayoutResult = measuredText,
            topLeft = Offset(
                x = x - measuredText.size.width - 1.dp.toPx(),
                y = size.height - measuredText.size.height,
            ),
            color = gridColor,
        )
    }

    // Horizontal grid lines
    for (i in horizontalGridPoints.indices) {
        val point = horizontalGridPoints[i]
        val y = point.position.mapValueToDifferentRange(
            inMin = horizontalGridPoints.minOf { it.position },
            inMax = horizontalGridPoints.maxOf { it.position },
            outMin = size.height,
            outMax = 0f,
        ).toFloat()

        drawLine(
            color = gridColor,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = gridStroke.toPx()
        )

        val measuredText = textMeasurer.measure(
            point.label,
            style = textStyle,
        )
        val offset = if (i == horizontalGridPoints.lastIndex) {
            Offset(0f, y)
        } else {
            Offset(0f, y - measuredText.size.height)
        }
        drawText(
            textLayoutResult = measuredText,
            topLeft = offset,
            color = gridColor,
        )
    }
}

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
        horizontalGridPoints = listOf(
            GridPoint(90.0, "90"),
            GridPoint(100.0, "100"),
            GridPoint(110.0, "110"),
            GridPoint(120.0, "120"),
            GridPoint(130.0, "130"),
        ),
        verticalGridPoints = listOf(
            GridPoint(10.0, "10"),
            GridPoint(20.0, "20"),
            GridPoint(30.0, "30"),
            GridPoint(40.0, "40"),
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

private data class ScaledFrame(
    val minXValue: Double,
    val maxXValue: Double,
    val minYValue: Double,
    val maxYValue: Double,
)

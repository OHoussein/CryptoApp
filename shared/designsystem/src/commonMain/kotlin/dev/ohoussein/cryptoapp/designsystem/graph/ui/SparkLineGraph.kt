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

@Composable
fun SparkLineGraph(
    points: List<GraphPoint>,
    color: Color,
    modifier: Modifier = Modifier,
    stroke: Dp = 1.dp,
) {
    val minTime = remember(points) { points.minOf { it.x } }
    val maxTime = remember(points) { points.maxOf { it.x } }
    val minPrice = remember(points) { points.minOf { it.y } }
    val maxPrice = remember(points) { points.maxOf { it.y } }

    Box(
        modifier = modifier
            .drawBehind {
                val startPadding = 2.dp.toPx()

                // Scale the points to fit within the canvas dimensions
                val xScale = (size.width - startPadding) / (maxTime - minTime)
                val yScale = (size.height - startPadding) / (maxPrice - minPrice)

                val path = Path().apply {
                    var previousX = 0f
                    var previousY = 0f
                    var currentX: Float
                    var currentY: Float

                    points.forEachIndexed { index, point ->
                        currentX = ((point.x - minTime) * xScale).toFloat()
                        currentY = (size.height - (point.y - minPrice) * yScale).toFloat()
                        if (index == 0) {
                            moveTo(currentX + startPadding, currentY - startPadding)
                        } else {
                            val controlX1 = (previousX + currentX) / 2
                            val controlY1 = previousY
                            val controlX2 = (previousX + currentX) / 2
                            val controlY2 = currentY
                            cubicTo(controlX1, controlY1, controlX2, controlY2, currentX, currentY)
                        }
                        previousX = currentX
                        previousY = currentY
                    }
                }
                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                )

                val areaPath = Path().apply {
                    addPath(path)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(
                    path = areaPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.2f),
                            color.copy(alpha = 0f),
                        ),
                        startY = 0f,
                        endY = size.height,
                    ),
                    style = Fill,
                )
            }
    )
}

@Preview
@Composable
private fun Preview() {
    val sampleValues: List<GraphPoint> = listOf(
        GraphPoint(20.0, 100.0),
        GraphPoint(21.5, 110.0),
        GraphPoint(23.0, 105.0),
        GraphPoint(24.5, 120.0),
        GraphPoint(26.0, 108.0),
        GraphPoint(27.5, 112.0),
        GraphPoint(29.0, 130.0),
    )

    SparkLineGraph(
        sampleValues,
        stroke = 2.dp,
        color = Color(0xFF78909C),
        modifier = Modifier
            .padding(30.dp)
            .size(300.dp, 200.dp),
    )
}

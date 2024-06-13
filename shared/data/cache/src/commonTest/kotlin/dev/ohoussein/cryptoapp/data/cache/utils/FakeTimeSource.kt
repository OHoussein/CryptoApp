package dev.ohoussein.cryptoapp.data.cache.utils

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

class FakeTimeSource : TimeSource {
    private var currentTimeMs: Long = 0

    override fun markNow(): TimeMark = object : TimeMark {
        override fun elapsedNow(): Duration {
            return currentTimeMs.milliseconds
        }
    }

    operator fun plusAssign(duration: Duration) {
        currentTimeMs += duration.inWholeMilliseconds
    }
}

package dev.ohoussein.cryptoapp.data.cache

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class CachedDataRepositoryImplTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest

    val updater: suspend (String) -> List<String> = mockk()
    val cacheStreamer: (String) -> Flow<List<String>> = mockk()
    val cacheWriter: suspend (String, List<String>) -> Unit = mockk()

    val cachedDataRepository = CachedDataRepository(
        updater = updater,
        cacheStreamer = cacheStreamer,
        cacheWriter = cacheWriter,
    )

    describe("stream") {
        every { cacheStreamer("key") } returns flowOf(listOf("data1", "data2"))
        val steamedData = cachedDataRepository.stream("key")

        it("data should be the same with cache streamer") {
            steamedData.first() shouldBe listOf("data1", "data2")
        }

        it("it should call the cacheStreamer") {
            verify {
                cacheStreamer("key")
            }
        }
    }

    describe("refresh") {
        beforeTest {
            coEvery { updater("key") } returns listOf("data1", "data2")
            coJustRun { cacheWriter(any(), any()) }
            cachedDataRepository.refresh("key")
        }

        it("should call the updater") {
            coVerify {
                updater("key")
            }
        }

        it("should write the data to the cacheWriter") {
            coVerify {
                cacheWriter("key", listOf("data1", "data2"))
            }
        }
    }
})

package dev.ohoussein.cryptoapp.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        println("Create database to ${getDatabaseFile().absolutePath}")
        return JdbcSqliteDriver("jdbc:sqlite:${getDatabaseFile().absolutePath}")
    }

    private fun getDatabaseFile(): File {
        return File(
            appDir.also { if (!it.exists()) it.mkdirs() },
            "cryptoApp.db",
        )
    }

    private val appDir: File
        get() {
            val os = System.getProperty("os.name").lowercase()
            return when {
                os.contains("win") -> {
                    File(System.getenv("AppData"), "cryptoApp/db")
                }

                os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
                    File(System.getProperty("user.home"), ".cryptoApp")
                }

                os.contains("mac") -> {
                    File(System.getProperty("user.home"), "Library/Application Support/cryptoApp")
                }

                else -> error("Unsupported operating system")
            }
        }
}

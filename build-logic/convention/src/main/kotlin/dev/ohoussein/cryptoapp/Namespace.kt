package dev.ohoussein.cryptoapp

import org.gradle.configurationcache.extensions.capitalized

fun getAndroidNameSpaceFromPath(prefix: String, path: String): String {
    val relativePackage = path.replace(':', '.')
    return prefix + relativePackage
}

fun getWebNameSpaceFromPath(path: String): String = path
    .split(":")
    .filter { it.isNotBlank() }
    .mapIndexed { index: Int, s: String ->
        if (index == 0) s else s.capitalized()
    }
    .joinToString("")

package dev.ohoussein.cryptoapp

fun getAndroidNameSpaceFromPath(prefix: String, path: String): String {
    val relativePackage = path.replace(':', '.')
    return prefix + relativePackage
}

fun getWebNameSpaceFromPath(path: String): String = path
    .split(":")
    .filter { it.isNotBlank() }
    .mapIndexed { index: Int, s: String ->
        if (index == 0) s else s.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
    .joinToString("")

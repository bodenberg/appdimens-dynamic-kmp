package com.appdimens.dynamic.platform

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

private const val CACHE_FILE = "dimen_cache.bin"

/**
 * File-based [CachePersistence] for JVM desktop (user config dir).
 *
 * Default directory: `~/.appdimens-dynamic/` (override with [baseDir]).
 */
fun createDesktopCachePersistence(baseDir: File = File(System.getProperty("user.home"), ".appdimens-dynamic")): CachePersistence {
    baseDir.mkdirs()
    val file = File(baseDir, CACHE_FILE)
    return object : CachePersistence {
        override suspend fun load(): Pair<Int, ByteArray?> = withContext(Dispatchers.IO) {
            if (!file.isFile || file.length() < 4L) return@withContext 0 to null
            CacheFileFormat.decode(file.readBytes())
        }

        override suspend fun save(smallestWidthDp: Int, data: ByteArray) = withContext(Dispatchers.IO) {
            baseDir.mkdirs()
            file.writeBytes(CacheFileFormat.encode(smallestWidthDp, data))
        }

        override suspend fun clearStore() = withContext(Dispatchers.IO) {
            if (file.exists()) file.delete()
        }
    }
}

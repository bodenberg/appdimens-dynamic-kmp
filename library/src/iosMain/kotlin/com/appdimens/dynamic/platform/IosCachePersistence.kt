package com.appdimens.dynamic.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.posix.memcpy

private const val CACHE_FILE_NAME = "appdimens_dynamic_cache.bin"

@OptIn(ExperimentalForeignApi::class)
private fun iosCacheFilePath(): String {
    val url = NSFileManager.defaultManager.URLForDirectory(
        directory = NSCachesDirectory,
        appropriateForURL = null,
        create = true,
        inDomain = NSUserDomainMask,
        error = null,
    ) ?: return CACHE_FILE_NAME
    val dir = url.path ?: return CACHE_FILE_NAME
    return "$dir/$CACHE_FILE_NAME"
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArrayKmm(): ByteArray {
    val len = length.toInt()
    if (len <= 0) return ByteArray(0)
    return ByteArray(len).apply {
        usePinned { pinned ->
            memcpy(pinned.addressOf(0), bytes, length.convert())
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun ByteArray.toNSData(): NSData = usePinned { pinned ->
    NSData.create(bytes = pinned.addressOf(0), length = size.convert())
}

/**
 * File-based [CachePersistence] under the app Caches directory (large binary; avoid NSUserDefaults).
 */
fun createIosCachePersistence(): CachePersistence {
    val path = iosCacheFilePath()
    return object : CachePersistence {
        override suspend fun load(): Pair<Int, ByteArray?> = withContext(Dispatchers.Default) {
            if (!NSFileManager.defaultManager.fileExistsAtPath(path)) return@withContext 0 to null
            val data = NSData.dataWithContentsOfFile(path) ?: return@withContext 0 to null
            CacheFileFormat.decode(data.toByteArrayKmm())
        }

        override suspend fun save(smallestWidthDp: Int, data: ByteArray) = withContext(Dispatchers.Default) {
            val blob = CacheFileFormat.encode(smallestWidthDp, data)
            blob.toNSData().writeToFile(path, atomically = true)
        }

        override suspend fun clearStore() = withContext(Dispatchers.Default) {
            if (NSFileManager.defaultManager.fileExistsAtPath(path)) {
                NSFileManager.defaultManager.removeItemAtPath(path, error = null)
            }
        }
    }
}

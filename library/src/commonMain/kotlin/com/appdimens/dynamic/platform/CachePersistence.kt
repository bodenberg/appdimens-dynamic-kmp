package com.appdimens.dynamic.platform

/**
 * Persists the dimension cache between sessions (Android: DataStore; JVM: file; iOS: NSUserDefaults).
 */
interface CachePersistence {
    suspend fun load(): Pair<Int, ByteArray?>
    suspend fun save(smallestWidthDp: Int, data: ByteArray)
    suspend fun clearStore()
}

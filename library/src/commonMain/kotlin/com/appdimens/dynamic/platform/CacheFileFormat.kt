package com.appdimens.dynamic.platform

/**
 * Shared on-disk layout for [CachePersistence] file backends (iOS Caches dir, desktop user dir):
 * 4 bytes big-endian `smallestWidthDp`, then raw cache [ByteArray] (same logical payload as Android DataStore).
 */
internal object CacheFileFormat {
    fun encode(smallestWidthDp: Int, data: ByteArray): ByteArray {
        val out = ByteArray(4 + data.size)
        out[0] = ((smallestWidthDp shr 24) and 0xff).toByte()
        out[1] = ((smallestWidthDp shr 16) and 0xff).toByte()
        out[2] = ((smallestWidthDp shr 8) and 0xff).toByte()
        out[3] = (smallestWidthDp and 0xff).toByte()
        data.copyInto(out, destinationOffset = 4)
        return out
    }

    fun decode(bytes: ByteArray): Pair<Int, ByteArray?> {
        if (bytes.size < 4) return 0 to null
        val sw = ((bytes[0].toInt() and 0xff) shl 24) or
            ((bytes[1].toInt() and 0xff) shl 16) or
            ((bytes[2].toInt() and 0xff) shl 8) or
            (bytes[3].toInt() and 0xff)
        val payload = bytes.copyOfRange(4, bytes.size)
        return sw to payload
    }
}

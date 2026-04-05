package com.appdimens.dynamic.platform

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull

private val KEY_SW_DP = intPreferencesKey("smallest_width_dp")
private val KEY_CACHE_DATA = byteArrayPreferencesKey("cache_mirror")

private val Context.dimenCacheDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.appdimens.dynamic.cache",
)

fun createAndroidCachePersistence(context: Context): CachePersistence {
    val store = context.applicationContext.dimenCacheDataStore
    return object : CachePersistence {
        override suspend fun load(): Pair<Int, ByteArray?> {
            val prefs = store.data.firstOrNull() ?: return 0 to null
            return (prefs[KEY_SW_DP] ?: 0) to prefs[KEY_CACHE_DATA]
        }

        override suspend fun save(smallestWidthDp: Int, data: ByteArray) {
            store.edit { p ->
                p[KEY_SW_DP] = smallestWidthDp
                p[KEY_CACHE_DATA] = data
            }
        }

        override suspend fun clearStore() {
            store.edit { it.clear() }
        }
    }
}

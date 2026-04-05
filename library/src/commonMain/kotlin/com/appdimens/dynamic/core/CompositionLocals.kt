/**
 * Composition locals for UiMode (platform [AppDimensProvider] supplies foldable-aware mode on Android).
 */
package com.appdimens.dynamic.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.appdimens.dynamic.common.UiModeType

val LocalUiModeType = compositionLocalOf { UiModeType.UNDEFINED }

@Composable
expect fun AppDimensProvider(content: @Composable () -> Unit)

@Composable
internal expect fun getCurrentUiModeType(): UiModeType

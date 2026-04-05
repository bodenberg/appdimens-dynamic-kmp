package com.appdimens.dynamic.sample.cmp

import androidx.compose.runtime.Composable

/** Inicializa [com.appdimens.dynamic.core.DimenCache] com persistência e métricas da plataforma (dentro de [AppDimensProvider]). */
@Composable
expect fun PlatformDimenCacheBinding()

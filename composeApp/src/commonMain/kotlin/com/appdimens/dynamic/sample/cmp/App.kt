package com.appdimens.dynamic.sample.cmp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.sdp
import com.appdimens.dynamic.core.AppDimensProvider

@Composable
fun SampleApp() {
    AppDimensProvider {
        PlatformDimenCacheBinding()
        MaterialTheme {
            Surface {
                val sdp16 = 16.sdp
                Text(
                    text = "AppDimens CMP — 16.sdp = $sdp16",
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

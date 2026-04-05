package com.appdimens.dynamic.sample.cmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "AppDimens CMP sample") {
        SampleApp()
    }
}

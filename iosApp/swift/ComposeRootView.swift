import SwiftUI
import UIKit
import ComposeApp

/// Hospeda a UI Compose gerada em Kotlin (`MainViewController.kt`).
struct ComposeRootView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

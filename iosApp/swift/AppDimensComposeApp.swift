import SwiftUI

@main
struct AppDimensComposeApp: App {
    var body: some Scene {
        WindowGroup {
            ComposeRootView()
                .ignoresSafeArea(.all)
        }
    }
}

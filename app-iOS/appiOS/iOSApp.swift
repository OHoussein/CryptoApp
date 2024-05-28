import SwiftUI
import Presentation

@main
struct iOSApp: App {
	@UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}


class AppDelegate: NSObject, UIApplicationDelegate {
    
}

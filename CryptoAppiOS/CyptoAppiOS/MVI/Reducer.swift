import Foundation

protocol Reducer {
    associatedtype State

    @MainActor var state: State { get }
    // func sendIntent(intent: Intent) async
}

import Foundation

protocol Reducer {
    associatedtype State
    associatedtype Intent

    var state: State { get }
    func sendIntent(intent: Intent)
}

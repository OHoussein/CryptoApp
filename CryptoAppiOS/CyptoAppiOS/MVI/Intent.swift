import Foundation

typealias IntentHandler<Intent> = (Intent) -> Void

protocol IntentProtocol {
    associatedtype IntentType

    func send(intent: IntentType)
}

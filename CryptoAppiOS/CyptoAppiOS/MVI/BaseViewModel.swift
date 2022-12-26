import Foundation

typealias Reducer<State, Event> = (_ state: State, _ event: Event) -> State

protocol BaseViewModel: ObservableObject {
    associatedtype State
    associatedtype Intent
    associatedtype Event

    var reducer: Reducer<State, Event> { get }
    var state: State { get set }
    func sendIntent(intent: Intent)
}

extension BaseViewModel {
    func send(event: Event) {
        state = reducer(state, event)
    }
}

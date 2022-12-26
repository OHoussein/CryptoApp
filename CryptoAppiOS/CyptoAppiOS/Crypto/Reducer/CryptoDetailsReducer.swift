import Foundation

func cryptoDetailsReducer(state: CryptoDetailsState, event: CryptoDetailsEvent) -> CryptoDetailsState {
    var newState = state
    switch event {
    case let .updateDetails(details):
        newState.cryptoDetails = details
        newState.status = LoadingStatus.idle
    case let .updateStatus(status):
        newState.status = status
    }
    return newState
}

import Foundation

func cryptoListReducer(state: CryptoListState, event: CryptoListEvent) -> CryptoListState {
    var newState = state
    switch event {
    case let .updateCryptoList(list):
        newState.cryptoList = list
        newState.status = LoadingStatus.idle
    case let .updateStatus(status):
        newState.status = status
    }
    print("New status $\(newState.status)")
    return newState
}

struct CryptoListState {
    var cryptoList: [Crypto]?
    var status: LoadingStatus = .idle
}

enum CryptoListIntent {
    case refresh
    case hideError
}

enum CryptoListEvent {
    case updateCryptoList([Crypto])
    case updateStatus(LoadingStatus)
}

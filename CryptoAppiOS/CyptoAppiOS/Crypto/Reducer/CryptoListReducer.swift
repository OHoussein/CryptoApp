import Foundation

func cryptoListReducer(state: CryptoListState, event: CryptoListEvent) -> CryptoListState {
    var newState = state
    switch event {
    case let .updateCryptoList(list):
        newState.cryptoList = list
    case let .updateStatus(status):
        newState.status = status
    }
    return newState
}

struct CryptoListState: Equatable {
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

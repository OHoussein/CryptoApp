import Foundation
import sharedModules

class CryptoListViewModel: ObservableObject, Reducer {
    typealias State = CryptoListState
    typealias Intent = CryptoListIntent
    @Published private(set) var state = CryptoListState()
    private let mapper = CryptoModelMapper()
    private let getTopCryptoListUseCase = SharedModulesKt.getTopCryptoListUseCase

    init() {
        watchCryptoList()
    }

    func sendIntent(intent: CryptoListIntent) {
        switch intent {
        case .refresh: refresh()
        case .hideError: reduce(event: .updateStatus(.idle))
        }
    }

    private func refresh() {
        reduce(event: .updateStatus(.loading))
        getTopCryptoListUseCase.refresh { [weak self] error in
            if let error = error {
                print("Refresh error = \(String(describing: error))")
                self?.reduce(event: .updateStatus(LoadingStatus.error("")))
                return
            }
        }
    }

    private func watchCryptoList() {
        getTopCryptoListUseCase.getAsWrapper().subscribe { domainCryptoListParam in
            if let domainCryptoList = domainCryptoListParam {
                let list = self.mapper.convert(domain: domainCryptoList)
                self.reduce(event: .updateCryptoList(list))
            }
        }
    }

    private func reduce(event: CryptoListEvent) {
        Task {
            await MainActor.run {
                switch event {
                case let .updateCryptoList(list):
                    state.cryptoList = list
                    state.status = LoadingStatus.idle
                case let .updateStatus(status):
                    state.status = status
                }
                print("New status $\(state.status)")
            }
        }
    }
}

struct CryptoListState {
    var cryptoList: [Crypto]?
    var status: LoadingStatus = .idle
}

enum CryptoListIntent {
    case refresh
    case hideError
}

private enum CryptoListEvent {
    case updateCryptoList([Crypto])
    case updateStatus(LoadingStatus)
}

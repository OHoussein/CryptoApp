import Foundation
import sharedModules

@MainActor
class CryptoListViewModel: ObservableObject {
    @Published private(set) var state = CryptoListState()
    private let mapper = CryptoModelMapper()
    private let getTopCryptoListUseCase = SharedModulesKt.getTopCryptoListUseCase

    init() {
        watchCryptoList()
    }

    func refresh() async {
        reduce(event: .updateStatus(.loading))
        do {
            try await getTopCryptoListUseCase.refresh()
        } catch {
            print("Refresh error = \(String(describing: error))")
            reduce(event: .updateStatus(LoadingStatus.error("")))
        }
    }

    func hideError() {
        reduce(event: .updateStatus(.idle))
    }

    private func watchCryptoList() {
        getTopCryptoListUseCase.get().subscribe { domainCryptoListParam in
            if let domainCryptoList = domainCryptoListParam {
                let list = self.mapper.convert(domain: domainCryptoList)
                self.reduce(event: .updateCryptoList(list))
            }
        }
    }

    private func reduce(event: CryptoListEvent) {
        print("Event = $\(event) | OldState = \(state)")
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

struct CryptoListState {
    var cryptoList: [Crypto]?
    var status: LoadingStatus = .idle
}

enum CryptoListEvent {
    case updateCryptoList([Crypto])
    case updateStatus(LoadingStatus)
}

import Foundation
import sharedModules

class CryptoDetailsViewModel: ObservableObject, Reducer {
    typealias State = CryptoDetailsState
    typealias Intent = CryptoDetailsIntent

    private let cryptoId: String
    private let mapper = CryptoModelMapper()
    private let cryptoDetailsUseCase = SharedModulesKt.getCryptoDetailsUseCase

    @Published private(set) var state = CryptoDetailsState()

    init(cryptoId: String) {
        self.cryptoId = cryptoId
        watchCryptoDetails()
    }

    func sendIntent(intent: CryptoDetailsIntent) {
        switch intent {
        case .refresh: refresh()
        case .hideError: reduce(event: .updateStatus(.idle))
        }
    }

    private func refresh() {
        reduce(event: .updateStatus(.loading))
        cryptoDetailsUseCase.refresh(cryptoId: cryptoId) { [weak self] error in
            if let error = error {
                print("Refresh error = \(String(describing: error))")
                self?.reduce(event: .updateStatus(LoadingStatus.error("")))
                return
            }
        }
    }

    private func watchCryptoDetails() {
        cryptoDetailsUseCase.getAsWrapper(cryptoId: cryptoId)
            .subscribe { domainCryptoDetailsParam in
                if let domainCryptoDetails = domainCryptoDetailsParam {
                    let details = self.mapper.convert(domain: domainCryptoDetails)
                    self.reduce(event: .updateDetails(details))
                }
            }
    }

    private func reduce(event: CryptoDetailsEvent) {
        Task {
            await MainActor.run {
                print("Event = $\(event) | OldState = \(String(describing: state))")
                switch event {
                case let .updateDetails(details):
                    state.cryptoDetails = details
                    state.status = LoadingStatus.idle
                case let .updateStatus(status):
                    state.status = status
                }
                print("New status $\(state.status)")
            }
        }
    }
}

struct CryptoDetailsState {
    var cryptoDetails: CryptoDetails?
    var status: LoadingStatus = .idle
}

enum CryptoDetailsIntent {
    case refresh
    case hideError
}

enum CryptoDetailsEvent {
    case updateDetails(CryptoDetails)
    case updateStatus(LoadingStatus)
}

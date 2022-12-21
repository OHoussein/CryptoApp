import Foundation
import sharedModules

@MainActor
class CryptoDetailsViewModel: ObservableObject {
    private let cryptoId: String
    @Published private(set) var state = CryptoDetailsState()
    private let mapper = CryptoModelMapper()
    private let cryptoDetailsUseCase = SharedModulesKt.getCryptoDetailsUseCase

    init(cryptoId: String) {
        self.cryptoId = cryptoId
        watchCryptoDetails()
    }

    func load() async {
        reduce(event: .updateStatus(.loading))
        do {
            try await cryptoDetailsUseCase.refresh(cryptoId: cryptoId)
        } catch {
            print("Loan details for \(cryptoId) error: \(String(describing: error))")
            reduce(event: .updateStatus(LoadingStatus.error("Network error")))
        }
    }

    @MainActor
    func hideError() {
        reduce(event: .updateStatus(.idle))
    }

    private func watchCryptoDetails() {
        cryptoDetailsUseCase.get(cryptoId: cryptoId)
            .subscribe { domainCryptoDetailsParam in
                if let domainCryptoDetails = domainCryptoDetailsParam {
                    let details = self.mapper.convert(domain: domainCryptoDetails)
                    self.reduce(event: .updateDetails(details))
                }
            }
    }

    private func reduce(event: CryptoDetailsEvent) {
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

struct CryptoDetailsState {
    var cryptoDetails: CryptoDetails?
    var status: LoadingStatus = .idle
}

enum CryptoDetailsEvent {
    case updateDetails(CryptoDetails)
    case updateStatus(LoadingStatus)
}

import Foundation
import sharedModules

class CryptoDetailsViewModel: BaseViewModel {
    var reducer: Reducer<CryptoDetailsState, CryptoDetailsEvent> = cryptoDetailsReducer

    typealias State = CryptoDetailsState
    typealias Intent = CryptoDetailsIntent
    typealias Event = CryptoDetailsEvent

    private let cryptoId: String
    private let mapper: CryptoModelMapper
    private let getCryptoDetailsUseCase: CryptoDomainGetCryptoDetailsUseCase

    @Published var state = CryptoDetailsState()

    init(
        cryptoId: String,
        getCryptoDetailsUseCase: CryptoDomainGetCryptoDetailsUseCase = SharedModulesKt.getCryptoDetailsUseCase,
        cryptoModelMapper: CryptoModelMapper = CryptoModelMapper()
    ) {
        self.cryptoId = cryptoId
        self.getCryptoDetailsUseCase = getCryptoDetailsUseCase
        mapper = cryptoModelMapper
        watchCryptoDetails()
    }

    func sendIntent(intent: CryptoDetailsIntent) {
        switch intent {
        case .refresh: refresh()
        case .hideError: send(event: .updateStatus(.idle))
        }
    }

    private func refresh() {
        send(event: .updateStatus(.loading))
        getCryptoDetailsUseCase.refresh(cryptoId: cryptoId) { [weak self] error in
            if let error = error {
                print("Refresh error = \(String(describing: error))")
                self?.send(event: .updateStatus(LoadingStatus.error("Network error")))
                return
            }
        }
    }

    private func watchCryptoDetails() {
        getCryptoDetailsUseCase.getAsWrapper(cryptoId: cryptoId)
            .subscribe { domainCryptoDetailsParam in
                if let domainCryptoDetails = domainCryptoDetailsParam {
                    let details = self.mapper.convert(domain: domainCryptoDetails)
                    self.send(event: .updateDetails(details))
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

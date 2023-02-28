import Combine
import Foundation
import sharedModules

class CryptoDetailsViewModel: MVIViewModel<CryptoDetailsState, CryptoDetailsIntent, CryptoDetailsEvent> {
    private let cryptoId: String
    private let mapper: CryptoModelMapper
    private let getCryptoDetailsUseCase: CryptoDomainGetCryptoDetailsUseCase

    private var subscriptions: [AnyCancellable] = []

    init(
        cryptoId: String,
        getCryptoDetailsUseCase: CryptoDomainGetCryptoDetailsUseCase = SharedModulesKt.getCryptoDetailsUseCase,
        cryptoModelMapper: CryptoModelMapper = CryptoModelMapper()
    ) {
        self.cryptoId = cryptoId
        self.getCryptoDetailsUseCase = getCryptoDetailsUseCase
        mapper = cryptoModelMapper
        super.init(reducer: cryptoDetailsReducer, initialState: CryptoDetailsState())
        watchCryptoDetails()
    }

    override func send(intent: CryptoDetailsIntent) {
        switch intent {
        case .refresh: refresh()
        case .hideError: send(event: .updateStatus(.idle))
        }
    }

    private func refresh() {
        send(event: .updateStatus(.loading))
        getCryptoDetailsUseCase.refresh(cryptoId: cryptoId) { [weak self] error in
            if error != nil {
                self?.send(event: .updateStatus(.error("Network error")))
            } else {
                self?.send(event: .updateStatus(.idle))
            }
        }
    }

    private func watchCryptoDetails() {
        createPublisher(getCryptoDetailsUseCase.getAsWrapper(cryptoId: cryptoId))
            .receive(on: DispatchQueue.main)
            .map {
                self.mapper.convert(domain: $0)
            }
            .sink(receiveCompletion: { _ in }, receiveValue: { details in
                self.send(event: .updateDetails(details))
            })
            .store(in: &subscriptions)
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

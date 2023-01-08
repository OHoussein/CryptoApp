import Combine
import Foundation
import sharedModules

class CryptoListViewModel: MVIViewModel<CryptoListState, CryptoListIntent, CryptoListEvent> {
    private let getTopCryptoListUseCase: CryptoDomainGetTopCryptoListUseCase
    private let mapper: CryptoModelMapper

    private var subscriptions: [AnyCancellable] = []

    init(
        getTopCryptoListUseCase: CryptoDomainGetTopCryptoListUseCase = SharedModulesKt.getTopCryptoListUseCase,
        cryptoModelMapper: CryptoModelMapper = CryptoModelMapper()
    ) {
        self.getTopCryptoListUseCase = getTopCryptoListUseCase
        mapper = cryptoModelMapper
        super.init(reducer: cryptoListReducer, initialState: CryptoListState())
        watchCryptoList()
    }

    override func send(intent: CryptoListIntent) {
        switch intent {
        case .refresh: refresh()
        case .hideError: send(event: .updateStatus(.idle))
        }
    }

    private func watchCryptoList() {
        createPublisher(getTopCryptoListUseCase.getAsWrapper())
            .map {
                self.mapper.convert(domain: $0)
            }
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] cryptoArray in
                self?.send(event: .updateCryptoList(cryptoArray))
            })
            .store(in: &subscriptions)
    }

    private func refresh() {
        send(event: .updateStatus(.loading))
        getTopCryptoListUseCase.refresh { [weak self] error in
            if error != nil {
                self?.send(event: .updateStatus(LoadingStatus.error("Network error")))
            } else {
                self?.send(event: .updateStatus(LoadingStatus.idle))
            }
        }
    }
}

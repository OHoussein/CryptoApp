import Foundation
import sharedModules

class CryptoListViewModel: MVIViewModel<CryptoListState, CryptoListEvent, CryptoListIntent> {
    private let getTopCryptoListUseCase: CryptoDomainGetTopCryptoListUseCase
    private let mapper: CryptoModelMapper

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
        getTopCryptoListUseCase.getAsWrapper().subscribe { domainCryptoListParam in
            if let domainCryptoList = domainCryptoListParam {
                let list = self.mapper.convert(domain: domainCryptoList)
                self.send(event: .updateCryptoList(list))
            }
        }
    }

    private func refresh() {
        send(event: .updateStatus(.loading))
        getTopCryptoListUseCase.refresh { [weak self] error in
            if let error = error {
                print("Refresh error = \(String(describing: error))")
                self?.send(event: .updateStatus(LoadingStatus.error("")))
                return
            }
        }
    }
}

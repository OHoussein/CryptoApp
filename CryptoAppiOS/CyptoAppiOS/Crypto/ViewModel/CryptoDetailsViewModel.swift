import Foundation

class CryptoDetailsViewModel: ObservableObject {
    let cryptoId: String
    @Published private(set) var cryptoDetails: CryptoDetails?

    init(cryptoId: String) {
        self.cryptoId = cryptoId
        load()
    }

    func load() {
        cryptoDetails = mockedCryptoDetails()
    }
}

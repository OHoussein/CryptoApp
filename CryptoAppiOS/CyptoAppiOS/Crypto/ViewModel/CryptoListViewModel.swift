import Foundation

class CryptoListViewModel: ObservableObject {
    @Published private(set) var list: [Crypto] = []

    init() {
        load()
    }

    func load() {
        list = mockedCryptoList()
    }
}

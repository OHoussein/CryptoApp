import Foundation
import SwiftUI

struct CryptoListScreen: View {
    @StateObject private var viewModel = CryptoListViewModel()

    var body: some View {
        CryptoListContent(cryptoList: viewModel.list)
    }
}

private struct CryptoListContent: View {
    let cryptoList: [Crypto]

    var body: some View {
        NavigationView {
            ScrollView {
                LazyVStack {
                    ForEach(cryptoList, id: \.self.base.id) { item in
                        NavigationLink(destination: CryptoDetailsScreen(cryptoId: item.base.id)) {
                            CryptoItem(crypto: item)
                                .foregroundColor(.black)
                        }
                    }
                }
            }
        }
    }
}

struct CryptoListScreenContent_Previews: PreviewProvider {
    static var previews: some View {
        CryptoListContent(cryptoList: mockedCryptoList())
    }
}

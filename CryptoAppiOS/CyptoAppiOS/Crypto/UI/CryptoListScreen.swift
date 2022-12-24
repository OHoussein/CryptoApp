import Foundation
import SwiftUI

struct CryptoListScreen: View {
    @StateObject private var viewModel = CryptoListViewModel()

    var body: some View {
        CryptoListContent(state: viewModel.state,
                          onCloseError: { viewModel.sendIntent(intent: .hideError) },
                          onRefresh: { viewModel.sendIntent(intent: .refresh) })
            .onAppear {
                viewModel.sendIntent(intent: .refresh)
            }
    }
}

private struct CryptoListContent: View {
    let state: CryptoListState
    var onCloseError: () -> Void
    var onRefresh: () -> Void

    var body: some View {
        NavigationView {
            ZStack(alignment: .bottom) {
                if let cryptoList = state.cryptoList {
                    ScrollView {
                        LazyVStack {
                            ForEach(cryptoList, id: \.self.base.id) { item in
                                NavigationLink {
                                    CryptoDetailsScreen(cryptoId: item.base.id)
                                } label: {
                                    CryptoItem(crypto: item)
                                        .foregroundColor(.black)
                                }
                            }
                        }
                    }
                    .refreshable {
                        onRefresh()
                    }

                    if case LoadingStatus.error = state.status {
                        ToastView(type: .error, title: "Error", message: "Network error") {
                            onCloseError()
                        }
                    }
                } else if case LoadingStatus.error = state.status {
                    ErrorView(message: "Network error") {
                        onRefresh()
                    }
                }
                if case LoadingStatus.loading = state.status {
                    ProgressView()
                }
            }
        }
    }
}

struct CryptoListScreenContent_Previews: PreviewProvider {
    static var previews: some View {
        CryptoListContent(state: CryptoListState(cryptoList: mockedCryptoList()),
                          onCloseError: {},
                          onRefresh: {})
    }
}

struct CryptoListWithErrorScreenContent_Previews: PreviewProvider {
    static var previews: some View {
        CryptoListContent(state: CryptoListState(cryptoList: mockedCryptoList(),
                                                 status: LoadingStatus.error("Network error")),
                          onCloseError: {},
                          onRefresh: {})
    }
}

import Foundation
import SwiftUI

struct CryptoListScreen<VM: MVIViewModel<CryptoListState, CryptoListIntent, CryptoListEvent>>: View {
    @StateObject var viewModel: VM

    var body: some View {
        CryptoListContent(state: viewModel.state,
                          onCloseError: { viewModel.send(intent: .hideError) },
                          onRefresh: { viewModel.send(intent: .refresh) })
            .onAppear {
                viewModel.send(intent: .refresh)
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
                                    CryptoDetailsScreen(viewModel: CryptoDetailsViewModel(cryptoId: item.base.id))
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

import Foundation
import SwiftUI

struct CryptoDetailsScreen<VM: MVIViewModel<CryptoDetailsState, CryptoDetailsEvent, CryptoDetailsIntent>>: View {
    @StateObject var viewModel: VM

    var body: some View {
        CryptoDetailsContent(state: viewModel.state,
                             onCloseError: { viewModel.send(intent: .hideError) },
                             onRefresh: { viewModel.send(intent: .refresh) })
            .onAppear {
                viewModel.send(intent: .refresh)
            }
    }
}

private struct CryptoDetailsContent: View {
    let state: CryptoDetailsState
    var onCloseError: () -> Void
    var onRefresh: () -> Void

    var body: some View {
        if let cryptoDetails = state.cryptoDetails {
            ZStack(alignment: .bottom) {
                List {
                    CryptoDetailsHeader(crypto: cryptoDetails.base)
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets())
                        .padding(.bottom, 24)

                    if let sentimentUpVotesPercentage = cryptoDetails.sentimentUpVotesPercentage {
                        CryptoSentiment(upPercentSentiment: sentimentUpVotesPercentage)
                            .listRowBackground(Color.clear)
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets())
                            .padding(EdgeInsets(top: 0, leading: 48, bottom: 24, trailing: 48))
                    }
                    CryptoDetailsDescription(description: cryptoDetails.description)
                        .listRowBackground(Color.clear)
                        .listRowInsets(EdgeInsets())
                    Section(header: Text("Links")) {
                        CryptoDetailsLinks(cryptoDetails: cryptoDetails)
                    }
                }
                .listStyle(InsetGroupedListStyle())

                if case let .error(error) = state.status {
                    ToastView(type: .error, title: "Error", message: error) {
                        onCloseError()
                    }
                }
            }
        } else if case let .error(error) = state.status {
            ErrorView(message: error, onRetryTapped: onRefresh)
        } else {
            ProgressView()
        }
    }
}

struct CryptoDetailsContent_Previews: PreviewProvider {
    static var previews: some View {
        CryptoDetailsContent(
            state: CryptoDetailsState(cryptoDetails: mockedCryptoDetails()),
            onCloseError: {},
            onRefresh: {}
        )
    }
}

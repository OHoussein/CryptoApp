import Foundation
import SwiftUI

struct CryptoDetailsScreen: View {
    @StateObject private var viewModel: CryptoDetailsViewModel

    init(cryptoId: String) {
        _viewModel = StateObject(wrappedValue: CryptoDetailsViewModel(cryptoId: cryptoId))
    }

    var body: some View {
        if let cryptoDetails = viewModel.cryptoDetails {
            CryptoDetailsContent(cryptoDetails: cryptoDetails)
        }
    }
}

private struct CryptoDetailsContent: View {
    let cryptoDetails: CryptoDetails

    var body: some View {
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
    }
}

struct CryptoDetailsContent_Previews: PreviewProvider {
    static var previews: some View {
        CryptoDetailsContent(cryptoDetails: mockedCryptoDetails())
    }
}

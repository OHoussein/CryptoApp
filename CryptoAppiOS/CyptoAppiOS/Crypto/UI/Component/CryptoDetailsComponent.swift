import Foundation
import SwiftUI

struct CryptoDetailsHeader: View {
    let crypto: BaseCrypto

    var body: some View {
        HStack {
            CryptoImage(url: crypto.imageUrl)
            Text("\(crypto.name) (\(crypto.symbol))")
                .frame(maxWidth: .infinity, alignment: .leading)
                .font(Font.headline)
        }
    }
}

struct CryptoDetailsDescription: View {
    let description: String
    @State var isExpanded = false
    @State var truncated = false

    var body: some View {
        VStack(alignment: .trailing, spacing: 0) {
            Text(description)
                .lineLimit(isExpanded ? nil : 4)
                .frame(maxWidth: .infinity)
                .font(Font.body)
                .fixedSize(horizontal: false, vertical: true)
                .background(
                    Text(description)
                        .hidden()
                        .background(GeometryReader { displayedGeometry in
                            ZStack {
                                Text(description)
                                    .background(GeometryReader { fullGeometry in
                                        Color.clear.onAppear {
                                            self.truncated = fullGeometry.size.height > displayedGeometry.size.height
                                        }
                                    })
                            }
                            .frame(height: .greatestFiniteMagnitude)
                        })
                )

            if truncated {
                Button {
                    isExpanded = !isExpanded
                } label: {
                    let icon = isExpanded ? "chevron.up" : "chevron.down"
                    let label = isExpanded ? "Collapse" : "Expand"
                    Label(label, systemImage: icon)
                        .font(Font.caption)
                        .labelStyle(.titleAndIcon)
                }
                .padding(EdgeInsets(top: 5, leading: 0, bottom: 20, trailing: 20))
            }
        }
    }
}

struct CryptoSentiment: View {
    let upPercentSentiment: LabelValue<Double>

    var body: some View {
        VStack {
            HStack {
                Image("ic_bull")
                    .resizable()
                    .frame(width: 24, height: 24)
                    .scaledToFill()
                Spacer()
                Image("ic_bear")
                    .resizable()
                    .frame(width: 24, height: 24)
                    .scaledToFill()
            }

            ProgressView(value: upPercentSentiment.value, total: 100)
                .tint(positiveColor)
                .background(negativeColor)
        }
    }
}

struct CryptoDetailsLinks: View {
    let cryptoDetails: CryptoDetails

    var body: some View {
        if let homePageUrl = cryptoDetails.homePageUrl {
            ItemLink(label: "Home page", iconName: "house", url: homePageUrl)
        }
        if let blockchainSite = cryptoDetails.blockchainSite {
            ItemLink(label: "Blockchain", iconName: "link", url: blockchainSite)
        }

        if let mainRepoUrl = cryptoDetails.mainRepoUrl {
            ItemLink(label: "Source code", iconName: "scroll", url: mainRepoUrl)
        }
    }
}

struct ItemLink: View {
    let label: String
    let iconName: String
    let url: String

    var body: some View {
        HStack {
            Image(systemName: iconName)
            Link(label, destination: URL(string: url)!)
                .frame(maxWidth: .infinity, alignment: .leading)
                .foregroundColor(Color.black)
            Image(systemName: "chevron.forward")
        }
    }
}

struct CryptoDetailsHeader_Previews: PreviewProvider {
    static var previews: some View {
        CryptoDetailsHeader(crypto: CryptoDataMock.mockedCryptoDetails().base)
    }
}

struct CryptoDetailsDescription_Previews: PreviewProvider {
    static var previews: some View {
        CryptoDetailsDescription(description: CryptoDataMock.mockedCryptoDetails().description)
    }
}

struct CryptoDetailsLinks_Previews: PreviewProvider {
    static var previews: some View {
        CryptoDetailsLinks(cryptoDetails: CryptoDataMock.mockedCryptoDetails())
    }
}

struct CryptoSentiment_Previews: PreviewProvider {
    static var previews: some View {
        CryptoSentiment(upPercentSentiment: LabelValue(value: 67, label: "67%"))
    }
}

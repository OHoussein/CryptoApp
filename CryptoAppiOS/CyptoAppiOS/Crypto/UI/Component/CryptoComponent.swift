import Foundation
import SwiftUI

struct CryptoItem: View {
    let crypto: Crypto

    var body: some View {
        HStack {
            CryptoImage(url: crypto.base.imageUrl)
                .padding(5)
            VStack {
                HStack(alignment: .center, spacing: 50) {
                    Text(crypto.base.name)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .multilineTextAlignment(.leading)
                        .font(Font.title3.bold())
                    Text(crypto.price.labelValue.label)
                        .frame(alignment: .trailing)
                        .font(Font.title3.bold())
                }

                HStack {
                    Text(crypto.base.symbol)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .font(Font.subheadline)
                    if let priceChangePercentIn24h = crypto.priceChangePercentIn24h {
                        Text(priceChangePercentIn24h.label)
                            .frame(alignment: .trailing)
                            .font(Font.subheadline)
                            .foregroundColor(priceChangePercentIn24h.value >= 0 ? positiveColor : negativeColor)
                    }
                }
            }
        }
        .padding(EdgeInsets(top: 4, leading: 12, bottom: 4, trailing: 12))
        .background(blueGrey50)
        .cornerRadius(16)
        .shadow(color: Color.gray, radius: 7, x: 0, y: 5)
        .padding(EdgeInsets(top: 8, leading: 12, bottom: 8, trailing: 12))
    }
}

struct CryptoImage: View {
    let url: String

    var body: some View {
        AsyncImage(url: URL(string: url)) { image in
            image.image?
                .resizable()
                .scaledToFill()
        }
        .frame(width: 48, height: 48)
        .clipShape(Circle())
    }
}

struct CryptoItem_Previews: PreviewProvider {
    static var previews: some View {
        CryptoItem(crypto: CryptoDataMock.mockedCryptoList().first!)
    }
}

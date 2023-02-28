import Foundation
import SwiftUI

struct ErrorView: View {
    let message: String
    let image: String = "img_error"
    var onRetryTapped: (() -> Void)?

    var body: some View {
        VStack {
            Image(image)
                .resizable()
                .frame(width: 128, height: 128)
                .scaledToFill()

            Text(message)
                .font(.body.bold())
                .foregroundColor(red500)

            if let onRetry = onRetryTapped {
                Button("Retry", action: onRetry)
                    .padding(.vertical)
            }
        }
    }
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView(message: "Network error", onRetryTapped: {})
    }
}

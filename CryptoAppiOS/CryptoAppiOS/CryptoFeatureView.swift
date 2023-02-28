import SwiftUI

struct CryptoFeatureView: View {
    var body: some View {
        CryptoListScreen(viewModel: CryptoListViewModel())
    }
}

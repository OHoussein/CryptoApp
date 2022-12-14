import SwiftUI

struct CryptoFeatureView: View {
    @StateObject private var viewModel = CryptoListViewModel()
    var body: some View {
        CryptoListScreen()
    }
}

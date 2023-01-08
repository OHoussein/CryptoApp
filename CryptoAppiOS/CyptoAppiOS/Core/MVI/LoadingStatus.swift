import Foundation

enum LoadingStatus: Equatable {
    case idle
    case loading
    case error(String)
}

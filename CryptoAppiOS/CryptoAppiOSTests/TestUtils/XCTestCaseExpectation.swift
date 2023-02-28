import Combine
import Foundation
import XCTest

extension XCTestCase {
    func waitFor<Output>(
        description: String,
        publisher: Published<Output>.Publisher,
        sIncluded: @escaping (Output) -> Bool
    ) {
        let expectation = self.expectation(description: description)
        var subscriptions: [AnyCancellable] = []

        publisher.filter(sIncluded)
            .sink { _ in
                expectation.fulfill()
            }
            .store(in: &subscriptions)
        wait(for: [expectation], timeout: 1)
    }
}

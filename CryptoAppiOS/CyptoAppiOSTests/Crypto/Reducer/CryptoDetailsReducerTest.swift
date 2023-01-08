@testable import CyptoAppiOS
import XCTest

final class CryptoDetailsReducerTest: XCTestCase {
    func testUpdateStatus() throws {
        let initialState = CryptoDetailsState()

        let newState = cryptoDetailsReducer(state: initialState, event: .updateStatus(.loading))

        XCTAssertEqual(LoadingStatus.loading, newState.status)
    }

    func testUpdateDetails() throws {
        let initialState = CryptoDetailsState()
        let details = CryptoDataMock.mockedCryptoDetails()

        let newState = cryptoDetailsReducer(state: initialState, event: .updateDetails(details))

        XCTAssertEqual(details, newState.cryptoDetails)
    }
}

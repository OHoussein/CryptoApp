@testable import CryptoAppiOS
import XCTest

final class CryptoListReducerTest: XCTestCase {
    func testUpdateStatus() throws {
        let initialState = CryptoListState()

        let newState = cryptoListReducer(state: initialState, event: .updateStatus(.loading))

        XCTAssertEqual(LoadingStatus.loading, newState.status)
    }

    func testUpdateCryptoList() throws {
        let initialState = CryptoListState()
        let cryptoList = CryptoDataMock.mockedCryptoList()

        let newState = cryptoListReducer(state: initialState, event: .updateCryptoList(cryptoList))

        XCTAssertEqual(cryptoList, newState.cryptoList)
    }
}

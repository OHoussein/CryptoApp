import Combine
import sharedModules
import XCTest

final class CryptoListViewModelTest: XCTestCase {
    private var subscriptions: [AnyCancellable] = []
    private var states: [CryptoListState] = []
    private var viewModel: CryptoListViewModel!
    private var useCase: MockedUseCase!

    override func tearDown() {
        subscriptions.forEach { cancellable in cancellable.cancel() }
        states = []
    }

    override func setUp() {
        useCase = MockedUseCase()
        viewModel = CryptoListViewModel(
            getTopCryptoListUseCase: useCase
        )

        viewModel.$state
            .sink { newState in
                self.states.append(newState)
            }
            .store(in: &subscriptions)
    }

    func testSuccessGetData() throws {
        viewModel.send(intent: .refresh)
        waitFor(description: "waiting for idle", publisher: viewModel.$state) { state in
            state.status == .idle && state.cryptoList != nil
        }

        XCTAssertEqual(LoadingStatus.idle, states[0].status)
        XCTAssertEqual(LoadingStatus.loading, states[1].status)
        XCTAssertEqual("bitcoin", viewModel.state.cryptoList?.first?.base.id)
        XCTAssertEqual(LoadingStatus.idle, viewModel.state.status)
    }

    func testErrorWithPreLoadedData() throws {
        // Given
        useCase.errorOnRefresh = true

        // When
        viewModel.send(intent: .refresh)
        waitFor(description: "waiting for error", publisher: viewModel.$state) { state in
            if case LoadingStatus.error = state.status {
                return state.cryptoList != nil
            }
            return false
        }

        // Then
        XCTAssertEqual(LoadingStatus.idle, states[0].status)
        XCTAssertEqual(LoadingStatus.loading, states[1].status)
        XCTAssertEqual(LoadingStatus.error("Network error"), viewModel.state.status)

        XCTAssertNotNil(viewModel.state.cryptoList)
        XCTAssertEqual(1, viewModel.state.cryptoList?.count)
    }

    func testErrorWithPreLoadedDataThenRefresh() throws {
        useCase.errorOnRefresh = true
        viewModel.send(intent: .refresh)
        waitFor(description: "waiting for error", publisher: viewModel.$state) { state in
            if case LoadingStatus.error = state.status {
                return true
            }
            return false
        }

        useCase.errorOnRefresh = false
        viewModel.send(intent: .refresh)
        waitFor(description: "waiting for idle", publisher: viewModel.$state) { state in
            state.status == .idle && state.cryptoList != nil
        }

        // Then
        XCTAssertEqual(LoadingStatus.idle, states[0].status)
        XCTAssertEqual(LoadingStatus.loading, states[1].status)
        XCTAssertEqual(LoadingStatus.error("Network error"), states[2].status)
        XCTAssertEqual(LoadingStatus.loading, states[3].status)
        XCTAssertEqual(LoadingStatus.idle, states[4].status)

        XCTAssertNotNil(viewModel.state.cryptoList)
        XCTAssertEqual(1, viewModel.state.cryptoList?.count)
    }
}

private class MockedUseCase: GetTopCryptoListUseCase {
    var errorOnRefresh = false
    let cryptoList = CryptoListModel(list: [
        CryptoModel(
            id: "bitcoin",
            name: "Bitcoin",
            imageUrl: "https://img.com/bitcoin",
            price: 16000.52,
            symbol: "BTC",
            priceChangePercentIn24h: 2.17,
            order: 1
        )
    ])

    func get() -> Kotlinx_coroutines_coreFlow {
        return SharedModulesKt.getTopCryptoListUseCase.get()
    }

    func getAsWrapper() -> CoroutinesToolsFlowWrapper<CryptoListModel> {
        return asFlowWrapper(cryptoList)
    }

    func refresh() async throws {
        if errorOnRefresh {
            throw DomainError(message: "")
        }
    }
}

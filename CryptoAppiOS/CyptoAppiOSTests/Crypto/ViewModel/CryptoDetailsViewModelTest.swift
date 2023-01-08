import Combine
import sharedModules
import XCTest

final class CryptoDetailsViewModelTest: XCTestCase {
    private var subscriptions: [AnyCancellable] = []
    private var states: [CryptoDetailsState] = []
    private var viewModel: CryptoDetailsViewModel!
    private var useCase: MockedUseCase!

    override func tearDown() {
        subscriptions.forEach { cancellable in cancellable.cancel() }
        states = []
    }

    override func setUp() {
        useCase = MockedUseCase()
        viewModel = CryptoDetailsViewModel(
            cryptoId: "bitcoin",
            getCryptoDetailsUseCase: useCase
        )

        viewModel.$state
            .sink { newState in
                self.states.append(newState)
            }
            .store(in: &subscriptions)
    }

    func testSuccessGetData() throws {
        // When
        viewModel.send(intent: .refresh)
        waitFor(description: "waiting for idle", publisher: viewModel.$state) { state in
            state.status == .idle && state.cryptoDetails != nil
        }
        // Then
        XCTAssertEqual(LoadingStatus.idle, states[0].status)
        XCTAssertEqual(LoadingStatus.loading, states[1].status)
        XCTAssertEqual("bitcoin", viewModel.state.cryptoDetails?.base.id)
        XCTAssertEqual(LoadingStatus.idle, viewModel.state.status)
    }

    func testErrorWithPreLoadedData() throws {
        // Given
        useCase.errorOnRefresh = true

        // When
        viewModel.send(intent: .refresh)
        waitFor(description: "waiting for error", publisher: viewModel.$state) { state in
            if case LoadingStatus.error = state.status {
                return state.cryptoDetails != nil
            }
            return false
        }

        // Then
        XCTAssertEqual(LoadingStatus.idle, states[0].status)
        XCTAssertEqual(LoadingStatus.loading, states[1].status)
        XCTAssertEqual(LoadingStatus.error("Network error"), viewModel.state.status)
        XCTAssertNotNil(viewModel.state.cryptoDetails)
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
            state.status == .idle && state.cryptoDetails != nil
        }

        // Then
        XCTAssertEqual(LoadingStatus.idle, states[0].status)
        XCTAssertEqual(LoadingStatus.loading, states[1].status)
        XCTAssertEqual(LoadingStatus.error("Network error"), states[2].status)
        XCTAssertEqual(LoadingStatus.loading, states[3].status)
        XCTAssertEqual(LoadingStatus.idle, states[4].status)

        XCTAssertNotNil(viewModel.state.cryptoDetails)
    }
}

private class MockedUseCase: CryptoDomainGetCryptoDetailsUseCase {
    var errorOnRefresh = false
    let cryptoDetails = CryptoDomainDomainCryptoDetails(
        id: "bitcoin",
        name: "Bitcoin",
        symbol: "BTC",
        imageUrl: "https://img.com/bitcoin",
        hashingAlgorithm: "SHA-256",
        homePageUrl: "https://bitcoin.com",
        blockchainSite: "https://btc.com",
        mainRepoUrl: "https://github.com/bitcoin",
        sentimentUpVotesPercentage: 64,
        sentimentDownVotesPercentage: 36,
        description: "bitcoin description"
    )

    func get(cryptoId _: String) -> Kotlinx_coroutines_coreFlow {
        return SharedModulesKt.getTopCryptoListUseCase.get()
    }

    func getAsWrapper(cryptoId _: String) -> CoroutinesToolsFlowWrapper<CryptoDomainDomainCryptoDetails> {
        return asFlowWrapper(cryptoDetails)
    }

    func refresh(cryptoId _: String) async throws {
        if errorOnRefresh {
            throw DomainError(message: "")
        }
    }
}

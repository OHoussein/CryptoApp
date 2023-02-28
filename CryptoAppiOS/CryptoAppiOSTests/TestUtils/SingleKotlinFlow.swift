import Foundation
import sharedModules

class SingleKotlinFlow<T>: Kotlinx_coroutines_coreFlow {
    private let value: T

    init(value: T) {
        self.value = value
    }

    @MainActor
    func collect(collector: Kotlinx_coroutines_coreFlowCollector) async throws {
        try await collector.emit(value: value)
    }
}

func asFlowWrapper<T>(_ data: T) -> CoroutinesToolsFlowWrapper<T> {
    return CoroutinesToolsFlowWrapper(origin: SingleKotlinFlow<T>(value: data))
}

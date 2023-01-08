import Combine
import Foundation
import sharedModules

func createPublisher<T>(
    _ flowWrapper: CoroutinesToolsFlowWrapper<T>
) -> AnyPublisher<T, Error> {
    return Deferred<Publishers.HandleEvents<PassthroughSubject<T, Error>>> {
        let subject = PassthroughSubject<T, Error>()
        let closeable = flowWrapper.subscribe { item in
            if let safeItem = item {
                subject.send(safeItem)
            }
        } onError: { error in
            subject.send(completion: .failure(DomainError.from(throwable: error)))
        } onCompletion: {
            subject.send(completion: .finished)
        }
        return subject.handleEvents(receiveCancel: {
            closeable.close()
        })
    }.eraseToAnyPublisher()
}

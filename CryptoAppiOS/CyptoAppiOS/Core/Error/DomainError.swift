import Foundation
import sharedModules

struct DomainError: Error {
    let message: String

    public var errorDescription: String? {
        return message
    }

    static func from(throwable: KotlinThrowable) -> DomainError {
        return DomainError(message: throwable.message ?? "Unknown error")
    }
}

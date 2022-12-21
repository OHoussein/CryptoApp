import Foundation

extension String {
    func deleteHTMLTag() -> String {
        let cleaned = replacingOccurrences(of: #"<[^>]*>"#, with: "", options: .regularExpression)
        return cleaned
    }
}

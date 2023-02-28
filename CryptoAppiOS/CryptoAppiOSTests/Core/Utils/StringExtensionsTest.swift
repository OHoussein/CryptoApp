@testable import CryptoAppiOS
import XCTest

final class StringExtensionsTest: XCTestCase {
    func testDeleteHTMLTag() throws {
        let htmlText = "<b>Hello</b>,<br /> this is a <strong><b>h</b>tml</strng> text."
        let expectedPlaintText = "Hello, this is a html text."

        let plaintText = htmlText.deleteHTMLTag()

        XCTAssertEqual(expectedPlaintText, plaintText)
    }
}

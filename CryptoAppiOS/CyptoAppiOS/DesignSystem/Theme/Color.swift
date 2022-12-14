import Foundation
import SwiftUI

let blueGrey50 = Color(hex: 0xFFEC_EFF1)

let green500 = Color(hex: 0xFF4C_AF50)
let red500 = Color(hex: 0xFFF4_4336)

let positiveColor = green500
let negativeColor = red500

extension Color {
    init(hex: UInt, alpha: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255,
            green: Double((hex >> 08) & 0xFF) / 255,
            blue: Double((hex >> 00) & 0xFF) / 255,
            opacity: alpha
        )
    }
}

import Foundation
import SwiftUI

let blueGrey50 = Color(hex: 0xFFEC_EFF1)
let blueGrey900 = Color(hex: 0xFF26_3238)

let green500 = Color(hex: 0xFF4C_AF50)
let red500 = Color(hex: 0xFFF4_4336)

let positiveColor = green500
let negativeColor = red500

class AppColors: ObservableObject {
    let backgroundCardColor: Color
    let forgroundColor: Color
    let cardShadowColor: Color

    init(backgroundCardColor: Color, forgroundColor: Color, cardShadowColor: Color) {
        self.backgroundCardColor = backgroundCardColor
        self.forgroundColor = forgroundColor
        self.cardShadowColor = cardShadowColor
    }
}

extension ColorScheme {
    func getAppColors() -> AppColors {
        switch self {
        case .dark:
            return AppColors(backgroundCardColor: blueGrey900,
                             forgroundColor: Color.white,
                             cardShadowColor: blueGrey900)
        default:
            return AppColors(backgroundCardColor: blueGrey50,
                             forgroundColor: Color.black,
                             cardShadowColor: Color.gray)
        }
    }
}

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

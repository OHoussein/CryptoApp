xcodebuild -scheme CyptoAppiOS -workspace ../CryptoAppiOS/CyptoAppiOS.xcworkspace \
 CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED=NO \
 -destination 'platform=iOS Simulator,name=iPhone 14,OS=16.1' \
 -derivedDataPath \
 build

xcrun simctl install Booted ../CryptoAppiOS/build/Build/Products/Debug-iphonesimulator/CyptoAppiOS.app
maestro test ios/ios.yml
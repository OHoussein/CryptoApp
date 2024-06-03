xcodebuild \
          -workspace ../app-iOS/appiOS.xcodeproj/project.xcworkspace \
          -configuration Release \
          -scheme appiOS \
          -sdk iphonesimulator \
          -derivedDataPath app-iOS/build

xcrun simctl install Booted ../app-iOS/build/Build/Products/Debug-iphonesimulator/CryptoAppiOS.app
maestro test ios/ios.yml
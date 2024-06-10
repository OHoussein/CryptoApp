[![Main CI](https://github.com/OHoussein/android-ios-kmm-crypto-app/workflows/Main%20CI/badge.svg)](https://github.com/OHoussein/android-ios-kmm-crypto-app/actions/workflows/main_ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OHoussein_android-crypto-app&metric=alert_status)](https://sonarcloud.io/dashboard?id=OHoussein_android-crypto-app)


A cross-platform app to display cryptocurrency prices, built using Kotlin Multiplatform and Compose Multiplatform. The app targets Android, iOS, and Desktop (JVM) platforms.

## Features

- View real-time cryptocurrency prices.
- Offline first.
- Cross-platform support: Android, iOS, and Desktop.
- Coroutine and Flow for asynchronous operations.
- Comprehensive testing with Maestro and JUnit.
- Test coverage reports with Kover.

## Tech Stack

- **Kotlin Multiplatform**: Share code between Android, iOS, and Desktop.
- **Compose Multiplatform**: Build UI across platforms with Jetpack Compose.
- **Multi-module clean architecture**: Maintainable and scalable project structure.
- **Koin**: Lightweight dependency injection framework.
- **Coroutines/Flow**: Asynchronous programming.
- **Ktor**: HTTP client.
- **SQLDelight**: Type-safe SQL, and multiplatform persistence library.
- **JUnit**: Unit testing framework.
- **Kover**: Code coverage tool for Kotlin.
- **Maestro**: End-to-end test automation framework.

## Architecture

The app follows the clean architecture principle, which includes:
- **Domain Layer**: Contains business logic.
- **Data Layer**: Handles data operations, including API calls and local database.
- **Presentation Layer**: Contains UI components built with Compose Multiplatform.

## Setup and Installation
- **JDK 20** to build the app
- [JVM](https://www.java.com/en/download/help/download_options.html) to run the desktop app
- [Xcode](https://developer.apple.com/xcode/) to build the iOS app
- Recommended IDE: [Intellij](https://www.jetbrains.com/idea/) or [Fleet](https://www.jetbrains.com/fleet/)
- [Maestro CLI](https://maestro.mobile.dev/) if you want to run the end to end tests.

## Build the app
### Android
```shell
./gradlew app-android:assembleRelease
```
### iOS
```shell
xcodebuild \
          -workspace app-iOS/appiOS.xcodeproj/project.xcworkspace \
          -configuration Debug \
          -scheme appiOS \
          -sdk iphonesimulator \
          -derivedDataPath app-iOS/build
```
### Desktop
```shell
./gradlew app-desktop:run
```

## Screenshots 📸
## Android
#### Dark mode 🌚

<div  align="center">
<img width="250px" src="https://github.com/OHoussein/android-ios-kmm-crypto-app/blob/main/design/android_crypto_list_dark.png" align="center" />
&nbsp;
<img width="250px" src="https://github.com/OHoussein/android-ios-kmm-crypto-app/blob/main/design/android_crypto_details_dark.png" align="center" />
</div>

#### Light mode 🌕

<div  align="center">
<img width="250px" src="https://github.com/OHoussein/android-ios-kmm-crypto-app/blob/main/design/android_crypto_list_light.png" align="center" />
&nbsp;
<img width="250px" src="https://github.com/OHoussein/android-ios-kmm-crypto-app/blob/main/design/android_crypto_details_light.png"  align="center" />
</div>

## iOS
#### Dark mode 🌚

<div  align="center">
<img width="250px" src="https://github.com/OHoussein/android-ios-kmm-crypto-app/blob/main/design/ios_crypto_list_dark.png" align="center" />
&nbsp;
<img width="250px" src="https://github.com/OHoussein/android-ios-kmm-crypto-app/blob/main/design/ios_crypto_details_dark.png" align="center" />
</div>

#### Light mode 🌕

<div  align="center">
<img width="250px" src="https://github.com/OHoussein/android-ios-kmm-crypto-app/blob/main/design/ios_crypto_list_light.png" align="center" />
&nbsp;
<img width="250px" src="https://github.com/OHoussein/android-ios-kmm-crypto-app/blob/main/design/ios_crypto_details_light.png"  align="center" />
</div>

## End to end test
### Android
https://user-images.githubusercontent.com/10960959/212092726-0e661257-5d7f-4efa-ac1d-908b0c4e19a4.mp4


## Credit

Data are provided by the awesome [CoinGecko API](https://www.coingecko.com/en/api)

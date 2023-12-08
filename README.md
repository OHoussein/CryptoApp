[![Android Main CI](https://github.com/OHoussein/android-ios-kmm-crypto-app/workflows/Android%20Main%20CI/badge.svg)](https://github.com/OHoussein/android-ios-kmm-crypto-app/actions/workflows/main_ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OHoussein_android-crypto-app&metric=alert_status)](https://sonarcloud.io/dashboard?id=OHoussein_android-crypto-app)


Implementation of the clean architecture using:

## Stack
#### Core
* Kotlin multiplatform
* Multi-module clean architecture
* **Koin** for dependency injection
* **Coroutine/Flow**
* Ktor for API calls
* SQLDelight for local database
* end to end test with maestro
* JUnit tests
* **Kover** for tests coverage 
#### Android
* **Jetpack compose**
* **Unit test** with kotest
* **Github Action**
#### iOS
* **Swift UI**
* **Publisher**

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

## Porject setup
### For Android & iOS
Install JDK
### iOS
Install cocoapods
```
brew install cocoapods
```
Install pod dependencies
```
pod install
```


## Layers

### Domain Layer

This module should contain only Java/Kotlin classes and doesn't have any Android framework class. It
defines the business aspect of the application. With the use case it combines data from the data
layer It doesn't depend on any module. It communicates with the data module through a repository
interface declared on the domain layer and implemented on the data layer.

### Presentation Layer

This is the platform specific layer, it contains UI coordinated by ViewModels which execute use
cases. This layer depends on the Domain Layer. With the DomainModelMappers, domains models are
converted to ui models.

### Data Layer

Contains remote, stored or in-memory data sources. With the DomainModelMappers, data models like api
models or database model are converted to domain models.

# Architecture

## Credit

Data are provided by the awesome [CoinGecko API](https://www.coingecko.com/en/api)

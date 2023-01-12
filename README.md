[![Android Main CI](https://github.com/OHoussein/android-crypto-app/workflows/Android%20Main%20CI/badge.svg)](https://github.com/OHoussein/android-crypto-app/actions/workflows/main_ci.yml)
[![Android Develop CI](https://github.com/OHoussein/android-crypto-app/workflows/Android%20Develop%20CI/badge.svg)](https://github.com/OHoussein/android-crypto-app/actions/workflows/develop_ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=OHoussein_android-crypto-app&metric=alert_status)](https://sonarcloud.io/dashboard?id=OHoussein_android-crypto-app)

⚠️ This project works only with Android Studio Arctic fox and above

Implementation of the clean architecture using:

* **Jetpack compose**
* **Koin** for dependency injection
* **Coroutine/Flow**
* **Unit test** with kotest
* **Github Action**

## Screenshots 📸

### Dark mode 🌚

<div  align="center">
<img width="250px" src="https://github.com/OHoussein/android-crypto-app/blob/develop/design/screen_list_dark.png" alt="architecture" align=center />
&nbsp;
<img width="250px" src="https://github.com/OHoussein/android-crypto-app/blob/develop/design/screen_details_dark.png" alt="architecture" align=center />
</div>

### Light mode 🌕

<div  align="center">
<img width="250px" src="https://github.com/OHoussein/android-crypto-app/blob/develop/design/screen_list_light.png" alt="architecture" align=center />
&nbsp;
<img width="250px" src="https://github.com/OHoussein/android-crypto-app/blob/develop/design/screen_details_light.png" alt="architecture" align=center />
</div>

## End to end test

https://user-images.githubusercontent.com/10960959/212092726-0e661257-5d7f-4efa-ac1d-908b0c4e19a4.mp4



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

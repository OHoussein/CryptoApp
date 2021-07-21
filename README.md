![Android Master CI](https://github.com/OHoussein/android-crypto-app/workflows/Android%20Main%20CI/badge.svg)
![Android Develop CI](https://github.com/OHoussein/android-crypto-app/workflows/Android%20Develop%20CI/badge.svg)

⚠️ This project works only with Android Studio 4 Bumblebee and above

Implementation of the clean architecture using:

* **Jetpack compose**
* **Dagger Hilt** for dependency injection
* **Coroutine/Flow**
* **Unit test** with junit
* **Instrumentation test** with Espresso
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

<div  align="center">
<img src="https://github.com/OHoussein/android-crypto-app/blob/develop/design/architecture.svg" alt="architecture" align=center />
</div>

## Flow

1. UI (Composable, Fragment, Activity, ...) calls loading method from the ViewModel.
1. ViewModels execute the use case.
1. Use case calls the repository.
1. The repository returns data from local or remote data source.
1. Information flows back to the UI.

## Credit

Data are provided by the awesome [CoinGecko API](https://www.coingecko.com/en/api)

# TODO

- [ ] Add more UI tests (based on the mocked web services)
- [ ] Use StateFlow instead of livedata
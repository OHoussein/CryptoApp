![Android Master CI](https://github.com/OHoussein/android-crypto-app/workflows/Android%20Master%20CI/badge.svg)
![Android Develop CI](https://github.com/OHoussein/android-crypto-app/workflows/Android%20Develop%20CI/badge.svg)

⚠️ This project works only with Android Studio 4 Bumblebee and above


## Layers
### Domain Layer 
This module should contain only Java/Kotlin classes and doesn't have any Android framework class.
It defines the business aspect of the application. With the use case it combines data from the data layer
It doesn't depend on any module. It communicates with the data module through a repository interface  declared on the domain layer and implemented on the data layer.
### Presentation Layer
This is the plateform specific layer, it contains UI coordinated by ViewModels which execute use cases.
This layer depends on the Domain Layer. With the DomainModelMappers, domains models are converted to ui models.
### Data Layer
Contains remote, stored or in-memory data sources.
With the DomainModelMappers, data models like api models or database model are converted to domain models.

# Libraries
* **Jetpack Compose**
* **Dagger Hilt :** for the dependency injection, this library is so simple to use and to make testable code. 
* **Coroutine :** doing async tasks is so simple with this new style of concurency
* **Flow :** for handling multi-shots data with coroutine with async collections transformation (a lot of its API still in experimental)
* **Github Action :** For quick CI integration  and full integration with Github
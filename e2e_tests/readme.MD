## Installation

[Documentation](https://maestro.mobile.dev/getting-started/installing-maestro)

```
curl -Ls "https://get.maestro.mobile.dev" | bash
```

## run end to end test
```
cd e2e_tests/
./test_android.sh
```
## Build Android and run end to end test
```
./gradlew e2eTest
```
## Generate screenshots
```
cd e2e_tests/
./generate_screenshots.sh
```
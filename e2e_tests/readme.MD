## Installation

brew install python3
pip3 install --user pipenv
cd e2e_tests
pipenv install

## run end to end test
cd e2e_tests
pipenv run python3 e2e_tests/crypto.py

## Build run end to end test
./gradlew e2eTest

## Generate screenshots on the running device

pipenv run python3 e2e_tests/crypto_screenshot_generate.py
#!/bin/bash

echo "Running git pre-commit hook"

./gradlew detekt -PdetektAutoFix=true

./gradlew detekt
status=$?
if [ "$status" = 0 ] ; then
    echo "Static analysis found no issues. Proceeding with push."
    exit 0
else
    echo 1>&2 "Static analysis found issues you need to fix before pushing."
    exit 1
fi
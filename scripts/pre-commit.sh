#!/bin/bash

echo "Running git pre-commit hook"

./gradlew detekt -PdetektAutoFix=true --no-daemon
autoCorrected=$?

if [ "$autoCorrected" = 0 ] ; then
    echo "Static analysis is corrected. Proceeding with push."
    sleep 0.5 #wait for file sync
    exit 0
else
    echo 1>&2 "Static analysis found issues you need to fix before pushing."
    exit 1
fi
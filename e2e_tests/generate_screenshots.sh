## Android
android_device_id=$(adb devices | grep -v "List of devices" | awk '{print $1}' | head -n 1| xargs)
if [[ -z "$android_device_id" ]]; then
    echo -e "Error: No connected android device found !"
else
    echo "Run android screenshots on $android_device_id"
    adb -s "$android_device_id" shell "cmd uimode night yes"
    maestro --device="$android_device_id" test android/android-screenshots.yml -e test_name="dark"
    adb -s "$android_device_id" shell "cmd uimode night no"
    maestro --device="$android_device_id" test android/android-screenshots.yml -e test_name="light"
fi

## iOS
ios_device_id=$(xcrun simctl list | grep -E '(Booted)' | sed 's/.*(\([a-zA-Z0-9\-]\{36\}\)).*/\1/' | xargs)
if [[ -z "$ios_device_id" ]]; then
    echo -e "Error: No connected ios device found !"
else
    echo "Run ios screenshots on: $ios_device_id"
    xcrun simctl ui booted appearance dark
    maestro --device="$ios_device_id" test ios/ios-screenshots.yml -e test_name="dark"
    xcrun simctl ui booted appearance light
    maestro --device="$ios_device_id" test ios/ios-screenshots.yml -e test_name="light"
fi
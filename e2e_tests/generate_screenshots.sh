# Dark Mode
adb shell "cmd uimode night yes"
maestro test android.yml -e test_name="dark"
adb shell "cmd uimode night no"
maestro test android.yml -e test_name="light"
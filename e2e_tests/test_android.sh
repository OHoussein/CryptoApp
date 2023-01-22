bash ../gradlew :android:app:installRelease
adb shell screenrecord /sdcard/tmp/crypto_test.mp4 &
maestro test android/android.yml
kill $!
sleep 2
adb pull /sdcard/tmp/crypto_test.mp4 screenshots/record_android.mp4
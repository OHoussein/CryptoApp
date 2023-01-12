adb shell screenrecord /sdcard/tmp/crypto_test.mp4 &
maestro test android.yml -e test_name="e2e"
kill $!
sleep 2
adb pull /sdcard/tmp/crypto_test.mp4 screenshots/crypto_test.mp4
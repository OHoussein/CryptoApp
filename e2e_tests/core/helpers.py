from uiautomator2 import Device
from time import sleep

def nightMode(device: Device, on: bool):
    yesOrno = "yes" if on else "no"
    device.shell(f"cmd uimode night {yesOrno}", timeout=10)
    sleep(1)

def screenShotDarkAndLightMode(device: Device, name: str):
    nightMode(device, True)
    device(resourceId="android:id/content").screenshot().save("screens/" + name+"_dark.jpg")
    nightMode(device, False)
    device(resourceId="android:id/content").screenshot().save("screens/" + name+"_light.jpg")


def screenshot(device: Device, name: str, isDarkMode: bool):
    suffix = "_dark.jpg" if (isDarkMode) else "_light.jpg"
    device(resourceId="android:id/content").screenshot().save("screens/" + name + suffix)

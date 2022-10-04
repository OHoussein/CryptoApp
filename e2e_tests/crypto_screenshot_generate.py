import os
from time import sleep
from xmlrpc.client import Boolean
from core import helpers
from core.app_helpers import AppHelper
from uiautomator2 import Device

def record(device: Device, isDarkMode: Boolean):
    helpers.nightMode(device, isDarkMode)
    sleep(10)
    device(text="Bitcoin").exists(timeout=10)
    helpers.screenshot(device, "crypto_list", isDarkMode=isDarkMode)
    device(text="Bitcoin").click()
    device(text="Bitcoin (BTC)").exists(timeout=10)
    helpers.screenshot(device, "crypto_details", isDarkMode=isDarkMode)

def main():
    if not os.path.exists("screens"):
        os.makedirs("screens")
    appHelper = AppHelper()
    device = appHelper.open_app()

    record(device, isDarkMode = False)
    device.press("back")
    record(device, isDarkMode = True)

main()

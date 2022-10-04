from uiautomator2 import Device
import uiautomator2 as u2

APP = "dev.ohoussein.cryptoapp"

class AppHelper:

    def open_app(self) -> Device:
        self.device = u2.connect()
        self.device.app_start(APP, stop = True)
        print(self.device.info)
        self.device.app_info(APP)  

        if not self.device(scrollable=True, instance=0).exists():
            raise Exception("Crypto list is not displayed")
        return self.device

    def stopApp(self):
        self.device.app_stop("dev.ohoussein.cryptoapp")
        self.device.uiautomator.stop()
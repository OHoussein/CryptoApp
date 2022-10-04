from nis import match
import unittest
import uiautomator2 as u2


class TestApp(unittest.TestCase):
    def setUp(self) -> None:
        self.device = u2.connect()
        self.device.app_start("dev.ohoussein.cryptoapp")
        print(self.device.info)
        self.device.app_info("dev.ohoussein.cryptoapp")

    def tearDown(self) -> None:
        if self.device:
            self.device.app_stop("dev.ohoussein.cryptoapp")
            self.device.uiautomator.stop()

    def test_sanity_check(self) -> None:

        cryptoList = self.device(scrollable=True, instance=0)
        self.assertTrue(cryptoList.exists(timeout=10))
        self.assertTrue(cryptoList.child(text="Bitcoin").exists)
        self.assertTrue(cryptoList.child(text="BTC").exists)
        self.assertTrue(cryptoList.child(text="Bitcoin").sibling(textMatches=r'[0-9.]+%').exists())
        
        btcPrice = cryptoList.child(text="Bitcoin").sibling(textMatches=r'\$[0-9.,]+').get_text()
        self.assertIn('$', btcPrice)
        btcPriceValue = float(btcPrice.strip("$").replace(',',''))
        self.assertGreater(btcPriceValue, 5000)

        self.assertTrue(cryptoList.child(text="Ethereum").exists)

        #BTC details
        cryptoList.child(text="Bitcoin").click()
        self.assertTrue(self.device(text="Bitcoin (BTC)").exists(timeout=10))
        self.assertTrue(self.device(textMatches=r'Bitcoin is the first successful internet money .*').exists)

        # url click
        self.device.press("back")
        self.assertTrue(self.device(scrollable=True, instance=0).exists)


if __name__ == '__main__':
    unittest.main()
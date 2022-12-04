package dev.ohoussein.cryptoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.ohoussein.crypto.presentation.activity.CryptoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(
            Intent(
                this, CryptoActivity::class.java
            )
        )
        finish()
    }
}

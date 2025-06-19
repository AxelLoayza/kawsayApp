package com.android.kawsayapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthSession()
        }, 2500) // 2.5 segundos
    }

    private fun checkAuthSession() {
        Amplify.Auth.fetchAuthSession(
            { result ->
                Log.i("SplashActivity", "Auth session state: ${result.isSignedIn}")
                // Si está logueado, va a MainActivity
                // Si NO está logueado, va a la NUEVA AuthActivity
                val nextActivity = if (result.isSignedIn) {
                    MainActivity::class.java
                } else {
                    AuthActivity::class.java // <- ¡CAMBIO IMPORTANTE AQUÍ!
                }
                startActivity(Intent(this, nextActivity))
                finish()
            },
            { error ->
                Log.e("SplashActivity", "Failed to fetch auth session", error)
                // En caso de error, ir a la pantalla de Login por seguridad
                startActivity(Intent(this, AuthActivity::class.java)) // <- ¡CAMBIO IMPORTANTE AQUÍ!
                finish()
            }
        )
    }
}
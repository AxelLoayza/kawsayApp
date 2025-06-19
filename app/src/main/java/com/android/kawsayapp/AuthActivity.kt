package com.android.kawsayapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.rememberAuthenticatorState
import com.amplifyframework.ui.authenticator.ui.Authenticator

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Amplify.Auth.fetchAuthSession(
            { result ->
                if (result.isSignedIn) {
                    runOnUiThread {
                        goToMain()
                    }
                } else {
                    runOnUiThread {
                        showAuthenticator()
                    }
                }
            },
            { error ->
                Log.e("AuthActivity", "Error al obtener sesión", error)
                runOnUiThread {
                    showAuthenticator()
                }
            }
        )
    }


    private fun showAuthenticator() {
        setContent {
            val state = rememberAuthenticatorState()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Authenticator(state = state) {
                    goToMain()
                }
            }
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

package com.android.kawsayapp

import android.os.Bundle
import android.view.Surface
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.ui.Authenticator


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Authenticator { state ->
                    Column {
                        Text(text = "Hola ${state.user.username}!")
                        Button(onClick = { Amplify.Auth.signOut {} }) {
                            Text(text = "Cerrar sesión")
                        }
                    }
                }
            }
        }
    }
}
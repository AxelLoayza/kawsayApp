package com.android.kawsayapp
import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify

class MyAmplifyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            // Verificar que los archivos de configuración existan
            Log.i("MyAmplifyApp", "Iniciando configuración de Amplify...")

            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Amplify configurado exitosamente")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Error al configurar Amplify: ${error.message}", error)
            Log.e("MyAmplifyApp", "Causa: ${error.cause}")
            Log.e("MyAmplifyApp", "Sugerencia: ${error.recoverySuggestion}")
        }
    }
}
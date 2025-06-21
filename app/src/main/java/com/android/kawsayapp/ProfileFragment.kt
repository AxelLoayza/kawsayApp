package com.android.kawsayapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val logoutButton: Button = view.findViewById(R.id.btnCerrarSesion)
        val userNameTextView: TextView = view.findViewById(R.id.text_user_name)
        val userEmailTextView: TextView = view.findViewById(R.id.text_user_email)

        // --- LÓGICA AÑADIDA PARA MOSTRAR DATOS DEL USUARIO ---
        fetchUserAttributes(userNameTextView, userEmailTextView)

        // --- TU LÓGICA DE CERRAR SESIÓN (NO SE CAMBIA) ---
        logoutButton.setOnClickListener {
            signOutUser()
        }

        // --- Lógica para los nuevos botones del menú (opcional) ---
        view.findViewById<TextView>(R.id.menu_edit_profile).setOnClickListener {
            Toast.makeText(context, "Editar Perfil (próximamente)", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.menu_settings).setOnClickListener {
            Toast.makeText(context, "Configuración (próximamente)", Toast.LENGTH_SHORT).show()
        }
        // ... puedes añadir más listeners aquí

        return view
    }

    private fun fetchUserAttributes(userNameView: TextView, userEmailView: TextView) {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                Log.i("ProfileFragment", "Atributos de usuario obtenidos: $attributes")
                val email = attributes.find { it.key == AuthUserAttributeKey.email() }?.value
                // Cognito no tiene un "nombre" por defecto, a menudo se usa "name" o "nickname" como atributo personalizado.
                // Usaremos el email como nombre si no hay otro.
                val name = attributes.find { it.key == AuthUserAttributeKey.name() }?.value ?: email?.substringBefore('@')

                activity?.runOnUiThread {
                    userNameView.text = name ?: "Usuario"
                    userEmailView.text = email ?: "No se encontró el email"
                }
            },
            { error ->
                Log.e("ProfileFragment", "Error al obtener atributos de usuario", error)
            }
        )
    }

    private fun signOutUser() {
        Amplify.Auth.signOut {
            Log.i("ProfileFragment", "Sesión cerrada correctamente")
            activity?.runOnUiThread {
                val intent = Intent(requireContext(), AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()
            }
        }
    }
}
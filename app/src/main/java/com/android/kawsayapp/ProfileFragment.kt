package com.android.kawsayapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.amplifyframework.auth.options.AuthSignOutOptions
import com.amplifyframework.core.Amplify

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout del fragmento (asegúrate de tener uno)
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Referencia al botón de cerrar sesión (asegúrate de tener el botón con ese ID en el layout)
        val logoutButton = view.findViewById<Button>(R.id.btnCerrarSesion)

        logoutButton.setOnClickListener {
            Amplify.Auth.signOut { result ->
                Log.i("ProfileFragment", "Sesión cerrada correctamente: $result")

                requireActivity().runOnUiThread {
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
            }



        }

        return view
    }
}

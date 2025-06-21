package com.android.kawsayapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.core.Amplify
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class HomeFragment : Fragment() {

    private lateinit var welcomeTitle: TextView
    private lateinit var emojiHappy: ImageView
    private lateinit var emojiNeutral: ImageView
    private lateinit var emojiSad: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // --- Inicializar vistas ---
        welcomeTitle = view.findViewById(R.id.welcome_title)

        // --- Cargar datos y configurar listeners ---
        fetchUserName()
        setupMoodSelectors(view)
        setupActionNavigation(view)

        return view
    }

    private fun fetchUserName() {
        Amplify.Auth.fetchUserAttributes(
            { attributes ->
                val name = attributes.find { it.key == AuthUserAttributeKey.name() }?.value
                activity?.runOnUiThread {
                    if (!name.isNullOrBlank()) {
                        // El `split(' ')[0]` es para tomar solo el primer nombre si hay más.
                        welcomeTitle.text = "¡Hola, ${name.split(' ')[0]}!"
                    } else {
                        welcomeTitle.text = "¡Hola!"
                    }
                }
            },
            { error ->
                Log.e("HomeFragment", "Error al obtener nombre de usuario", error)
                activity?.runOnUiThread { welcomeTitle.text = "¡Hola!" }
            }
        )
    }

    private fun setupActionNavigation(view: View) {
        val cardChatbot: MaterialCardView = view.findViewById(R.id.card_chatbot)
        val cardGroups: MaterialCardView = view.findViewById(R.id.card_groups)
        val cardSchedule: MaterialCardView = view.findViewById(R.id.card_schedule)

        cardChatbot.setOnClickListener {
            navigateToTab(R.id.nav_chatbot)
        }
        cardGroups.setOnClickListener {
            navigateToTab(R.id.nav_groups)
        }
        cardSchedule.setOnClickListener {
            navigateToTab(R.id.nav_schedule)
        }
    }

    private fun navigateToTab(tabId: Int) {
        // Encontrar la BottomNavigationView en la actividad principal y cambiar la selección
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.selectedItemId = tabId
    }

    private fun setupMoodSelectors(view: View) {
        emojiHappy = view.findViewById(R.id.emoji_happy)
        emojiNeutral = view.findViewById(R.id.emoji_neutral)
        emojiSad = view.findViewById(R.id.emoji_sad)

        val emojis = listOf(emojiHappy, emojiNeutral, emojiSad)

        val moodClickListener = View.OnClickListener { clickedView ->
            emojis.forEach { it.isSelected = false }
            clickedView.isSelected = true

            when (clickedView.id) {
                R.id.emoji_happy -> Toast.makeText(context, "¡Qué bueno que te sientas bien!", Toast.LENGTH_SHORT).show()
                R.id.emoji_neutral -> Toast.makeText(context, "Recuerda, está bien no estar bien.", Toast.LENGTH_SHORT).show()
                R.id.emoji_sad -> Toast.makeText(context, "Estamos aquí para ti. Prueba hablar con Kawzay.", Toast.LENGTH_SHORT).show()
            }
        }
        emojis.forEach { it.setOnClickListener(moodClickListener) }
    }
}
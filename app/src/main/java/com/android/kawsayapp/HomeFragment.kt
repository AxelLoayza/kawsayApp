package com.android.kawsayapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Encontrar las tarjetas por su ID
        val cardChatbot: MaterialCardView = view.findViewById(R.id.card_chatbot)
        val cardGroups: MaterialCardView = view.findViewById(R.id.card_groups)
        val cardSchedule: MaterialCardView = view.findViewById(R.id.card_schedule)

        // Añadir acciones (por ahora, un simple mensaje)
        cardChatbot.setOnClickListener {
            // Aquí navegarías al fragmento del chatbot
            Toast.makeText(context, "Abriendo Chatbot...", Toast.LENGTH_SHORT).show()
        }

        cardGroups.setOnClickListener {
            // Aquí navegarías al fragmento de grupos
            Toast.makeText(context, "Abriendo Grupos...", Toast.LENGTH_SHORT).show()
        }

        cardSchedule.setOnClickListener {
            // Aquí navegarías al fragmento de la agenda
            Toast.makeText(context, "Abriendo Agenda...", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
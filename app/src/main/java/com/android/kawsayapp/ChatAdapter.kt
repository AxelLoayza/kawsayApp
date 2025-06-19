package com.android.kawsayapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    // Esta clase interna representa la vista de un solo item
    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.text_message_body)
        val layout: ConstraintLayout = view as ConstraintLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return MessageViewHolder(view)
    }

    // Aquí es donde la magia ocurre: se bindea el dato a la vista
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.text

        // Cambiamos el estilo y la alineación según quién envía el mensaje
        val constraintSet = ConstraintSet()
        constraintSet.clone(holder.layout)

        if (message.sender == MessageSender.USER) {
            // Mensaje del usuario: fondo azul, alineado a la derecha
            holder.messageText.setBackgroundResource(R.drawable.bg_user_message)
            constraintSet.clear(R.id.text_message_body, ConstraintSet.START)
            constraintSet.connect(
                R.id.text_message_body,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
                16 // Margen de 16dp
            )
        } else {
            // Mensaje del bot: fondo blanco, alineado a la izquierda
            holder.messageText.setBackgroundResource(R.drawable.bg_bot_message)
            constraintSet.clear(R.id.text_message_body, ConstraintSet.END)
            constraintSet.connect(
                R.id.text_message_body,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                16 // Margen de 16dp
            )
        }
        constraintSet.applyTo(holder.layout)
    }

    override fun getItemCount() = messages.size
}
package com.android.kawsayapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChatbotFragment : Fragment() {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: FloatingActionButton
    private val messageList = mutableListOf<ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chatbot, container, false)

        // Inicializar vistas
        recyclerView = view.findViewById(R.id.recycler_view_chat)
        messageEditText = view.findViewById(R.id.edit_text_message)
        sendButton = view.findViewById(R.id.button_send)

        // Configurar el RecyclerView y el Adapter
        setupRecyclerView()

        // Añadir mensaje de bienvenida del bot
        addInitialBotMessage()

        // Configurar el botón de envío
        sendButton.setOnClickListener {
            handleSendMessage()
        }

        return view
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(messageList)
        recyclerView.adapter = chatAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun addInitialBotMessage() {
        val welcomeMessage = ChatMessage("¡Hola! Soy Kawzay, tu asistente virtual. ¿En qué puedo ayudarte hoy?", MessageSender.BOT)
        messageList.add(welcomeMessage)
        chatAdapter.notifyItemInserted(messageList.size - 1)
    }

    private fun handleSendMessage() {
        val messageText = messageEditText.text.toString().trim()
        if (messageText.isNotEmpty()) {
            // 1. Crear y añadir el mensaje del usuario a la lista
            val userMessage = ChatMessage(messageText, MessageSender.USER)
            messageList.add(userMessage)
            chatAdapter.notifyItemInserted(messageList.size - 1)
            recyclerView.scrollToPosition(messageList.size - 1) // Desplazarse al nuevo mensaje

            // 2. Limpiar el campo de texto
            messageEditText.text.clear()

            // 3. Simular una respuesta del bot (aquí iría tu lógica de Amplify Lex)
            simulateBotResponse(messageText)
        }
    }

    private fun simulateBotResponse(userText: String) {
        // Simulación: el bot responde después de 1 segundo
        recyclerView.postDelayed({
            val botResponse = ChatMessage("He recibido tu mensaje: '$userText'. Estoy procesando tu solicitud...", MessageSender.BOT)
            messageList.add(botResponse)
            chatAdapter.notifyItemInserted(messageList.size - 1)
            recyclerView.scrollToPosition(messageList.size - 1)
        }, 1000)
    }
}
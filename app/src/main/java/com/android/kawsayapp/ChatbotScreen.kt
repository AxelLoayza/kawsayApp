package com.android.kawsayapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatbotScreen(onBack: () -> Unit) {
    var userMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Chatbot IA", style = MaterialTheme.typography.headlineMedium)

        // Área del chat (simulada por ahora)
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Text("Aquí irán los mensajes del chat...")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                modifier = Modifier.weight(1f).padding(8.dp)
            )
            Button(onClick = { /* lógica de envío */ }) {
                Text("Enviar")
            }
        }

        Button(onClick = onBack, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Volver")
        }
    }
}

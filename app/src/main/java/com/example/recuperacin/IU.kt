package com.example.recuperacin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Composable que representa la interfaz de usuario del juego
@Composable
fun IU(viewModel: MyViewModel) {
    var respuestaUsuario by remember { mutableStateOf("") }
    val pistaActual by viewModel.pistaActual.observeAsState("")
    val intentosRestantes by viewModel.intentosRestantes.observeAsState(3)
    val mensaje by viewModel.mensaje.observeAsState("")
    val estadoJuego by viewModel.estadoJuego.observeAsState(EstadoJuego.EN_JUEGO)
    // Observa el estado del juego

    // Estructura de la interfaz usando Column para organizar los elementos
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Muestra la pista actual
        Text(text = "Pista: $pistaActual", fontSize = 20.sp, modifier = Modifier.padding(8.dp))

        // Campo de texto para que el usuario ingrese su respuesta
        OutlinedTextField(
            value = respuestaUsuario,
            onValueChange = { respuestaUsuario = it },
            label = { Text("Tu respuesta") },
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { viewModel.verificarRespuesta(respuestaUsuario) }),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        // Botón para enviar la respuesta
        Button(
            onClick = {
                viewModel.verificarRespuesta(respuestaUsuario)
                respuestaUsuario = "" // Se limpia el campo de texto después de enviar la respuesta
            },
            enabled = estadoJuego == EstadoJuego.EN_JUEGO, // Solo habilitado si el juego está en curso
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Adivinar")
        }

        // Muestra los intentos restantes
        Text(text = "Intentos restantes: $intentosRestantes", fontSize = 16.sp, modifier = Modifier.padding(8.dp))

        // Muestra un mensaje si hay un resultado (ganar/perder)
        if (mensaje.isNotEmpty()) {
            Text(text = mensaje, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        }
    }
}
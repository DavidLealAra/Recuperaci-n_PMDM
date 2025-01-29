package com.example.recuperacin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// ViewModel que maneja la lógica del juego
class MyViewModel : ViewModel() {
    // Selecciona aleatoriamente una palabra del diccionario
    private val seleccion = Diccionario.palabras.random()
    private val palabra = seleccion.palabra
    private val pistas = seleccion.sinonimos.shuffled()

    // LiveData para la pista actual
    private val _pistaActual = MutableLiveData(pistas[0])
    val pistaActual: LiveData<String> get() = _pistaActual

    // LiveData para controlar los intentos restantes
    private val _intentosRestantes = MutableLiveData(3)
    val intentosRestantes: LiveData<Int> get() = _intentosRestantes

    // LiveData para mostrar mensajes al usuario
    private val _mensaje = MutableLiveData("")
    val mensaje: LiveData<String> get() = _mensaje

    // LiveData para el estado actual del juego
    private val _estadoJuego = MutableLiveData(EstadoJuego.EN_JUEGO)
    val estadoJuego: LiveData<EstadoJuego> get() = _estadoJuego

    // Método para verificar la respuesta del usuario
    fun verificarRespuesta(respuesta: String): EstadoJuego {
        // Si el juego ya terminó, no hacer nada
        if (_estadoJuego.value != EstadoJuego.EN_JUEGO) return _estadoJuego.value!!

        // Si la respuesta es correcta, el usuario gana
        if (respuesta.equals(palabra, ignoreCase = true)) {
            _mensaje.value = "¡Has ganado! La palabra era $palabra"
            _estadoJuego.value = EstadoJuego.GANADO
        } else {
            // Si la respuesta es incorrecta, reducir el número de intentos restantes
            _intentosRestantes.value = (_intentosRestantes.value ?: 3) - 1
            // Si se quedan sin intentos, el usuario pierde
            if (_intentosRestantes.value == 0) {
                _mensaje.value = "¡Has perdido! La palabra era $palabra"
                _estadoJuego.value = EstadoJuego.PERDIDO
            } else {
                // Se actualiza la pista con la siguiente disponible
                _pistaActual.value = pistas[3 - (_intentosRestantes.value ?: 3)]
            }
        }
        return _estadoJuego.value!!
    }
}
package com.example.recuperacin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MyViewModel : ViewModel() {
    private val seleccion = Diccionario.palabras.random()
    private val palabra = seleccion.palabra
    private val pistas = seleccion.sinonimos.shuffled()

    private val _pistaActual = MutableLiveData(pistas[0])
    val pistaActual: LiveData<String> get() = _pistaActual

    private val _intentosRestantes = MutableLiveData(3)
    val intentosRestantes: LiveData<Int> get() = _intentosRestantes

    private val _mensaje = MutableLiveData("")
    val mensaje: LiveData<String> get() = _mensaje

    private val _estadoJuego = MutableLiveData(EstadoJuego.EN_JUEGO)
    val estadoJuego: LiveData<EstadoJuego> get() = _estadoJuego

    fun verificarRespuesta(respuesta: String): EstadoJuego {
        if (_estadoJuego.value != EstadoJuego.EN_JUEGO) return _estadoJuego.value!!

        if (respuesta.equals(palabra, ignoreCase = true)) {
            _mensaje.value = "¡Has ganado! La palabra era $palabra"
            _estadoJuego.value = EstadoJuego.GANADO
        } else {
            _intentosRestantes.value = (_intentosRestantes.value ?: 3) - 1
            if (_intentosRestantes.value == 0) {
                _mensaje.value = "¡Has perdido! La palabra era $palabra"
                _estadoJuego.value = EstadoJuego.PERDIDO
            } else {
                _pistaActual.value = pistas[3 - (_intentosRestantes.value ?: 3)]
            }
        }
        return _estadoJuego.value!!
    }
}
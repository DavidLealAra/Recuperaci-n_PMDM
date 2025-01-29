package com.example.recuperacin

// Singleton para manejar el diccionario
object Diccionario {
    val palabras = listOf(
        Datos("amor", listOf("cariño", "afecto", "pasión")),
        Datos("felicidad", listOf("alegría", "dicha", "gozo")),
        Datos("miedo", listOf("temor", "pavor", "pánico"))
    )
}

// Enum para representar los estados del juego
enum class EstadoJuego { EN_JUEGO, GANADO, PERDIDO }

// Clase Datos para almacenar palabra y sinónimos
data class Datos(val palabra: String, val sinonimos: List<String>)
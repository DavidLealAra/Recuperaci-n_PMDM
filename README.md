# Juego de Adivinar la Palabra

## Descripción
Esta aplicación es un juego de adivinanza en el que el usuario debe descubrir una palabra oculta basándose en las pistas proporcionadas. Cada pista es un sinónimo de la palabra secreta, lo que facilita el proceso de adivinanza. El usuario cuenta con un total de tres intentos para acertar la palabra. Si no logra adivinarla después de tres intentos, el juego termina y se le revela la respuesta correcta.


## Funcionamiento
1. Se selecciona aleatoriamente una palabra del diccionario predefinido en la aplicación.
2. En cada ronda, el juego muestra un sinónimo de la palabra oculta.
3. El jugador introduce una respuesta en un campo de texto.
4. Si la respuesta es correcta, el usuario gana y el juego finaliza mostrando un mensaje de felicitación.
5. Si la respuesta es incorrecta, el jugador pierde un intento y se muestra una nueva pista.
6. Si el jugador agota los tres intentos sin acertar la palabra, el juego finaliza y se muestra la respuesta correcta.

## Tecnologías y Patrones Utilizados
El desarrollo de la aplicación sigue buenas prácticas y patrones de diseño para garantizar una estructura clara y mantenible.

### **1. Singleton**
Se implementa el patrón Singleton en la clase `Diccionario`, la cual contiene una lista de palabras con sus respectivos sinónimos. Este patrón garantiza que solo exista una instancia del diccionario en toda la ejecución de la aplicación.

```kotlin
object Diccionario {
   val palabras = listOf(
       Datos("amor", listOf("cariño", "afecto", "pasión")),
       Datos("felicidad", listOf("alegría", "dicha", "gozo")),
       Datos("miedo", listOf("temor", "pavor", "pánico"))
   )
}
```

### **2. Enum (Enumeraciones)**
Se utiliza la enumeración `EstadoJuego` para representar los diferentes estados posibles del juego: **EN_JUEGO**, **GANADO** y **PERDIDO**. Esto facilita el control del flujo del juego de manera clara y estructurada.

```kotlin
enum class EstadoJuego { EN_JUEGO, GANADO, PERDIDO }
```

### **3. MVVM (Model-View-ViewModel)**
El diseño de la aplicación sigue la arquitectura MVVM, que permite una separación clara entre la lógica de negocio, la UI y la gestión de datos.

- **Modelo (Model)**: `Datos` (contiene la palabra y sus sinónimos).

```kotlin
data class Datos(val palabra: String, val sinonimos: List<String>)
```

- **Vista (View)**: `IU` (interfaz gráfica con Jetpack Compose para mostrar información y recibir entradas del usuario).

```kotlin
@Composable
fun IU(viewModel: MyViewModel) {
    var respuestaUsuario by remember { mutableStateOf("") }
    val pistaActual by viewModel.pistaActual.observeAsState("")
    val intentosRestantes by viewModel.intentosRestantes.observeAsState(3)
    val mensaje by viewModel.mensaje.observeAsState("")
    val estadoJuego by viewModel.estadoJuego.observeAsState(EstadoJuego.EN_JUEGO)
}
```

- **ViewModel**: `MyViewModel` (maneja la lógica del juego y el estado de la UI usando `LiveData`).

```kotlin
class MyViewModel : ViewModel() {
   private val seleccion = Diccionario.palabras.random()
   private val palabra = seleccion.palabra
   private val pistas = seleccion.sinonimos.shuffled()
}
```

### **4. Observer Pattern**
Se utiliza `LiveData` en `MyViewModel` para permitir la actualización automática de la interfaz de usuario en respuesta a cambios en el estado del juego.

```kotlin
private val _pistaActual = MutableLiveData(pistas[0])
val pistaActual: LiveData<String> get() = _pistaActual

private val _intentosRestantes = MutableLiveData(3)
val intentosRestantes: LiveData<Int> get() = _intentosRestantes

private val _mensaje = MutableLiveData("")
val mensaje: LiveData<String> get() = _mensaje

private val _estadoJuego = MutableLiveData(EstadoJuego.EN_JUEGO)
val estadoJuego: LiveData<EstadoJuego> get() = _estadoJuego
```

### **5. Manejo de Estados**
Se emplea `MutableLiveData` para gestionar diferentes aspectos del juego como la pista actual, los intentos restantes, el mensaje de estado y el estado general del juego.

### **6. Métodos con Parámetros y Retorno de Valores**
Se utiliza la función `verificarRespuesta(respuesta: String): EstadoJuego`, la cual evalúa la respuesta del usuario, actualiza el estado del juego y devuelve un `EstadoJuego` correspondiente.

```kotlin
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
```

## Estructura del Código
```
com.example.recuperacin
│── Diccionario.kt (Singleton con palabras y sinónimos)
│── EstadoJuego.kt (Enum con los estados del juego)
│── Datos.kt (Modelo de datos)
│── MainActivity.kt (Inicializa la UI con el ViewModel)
│── MyViewModel.kt (Lógica del juego y gestión de estados)
│── IU.kt (Interfaz gráfica con Jetpack Compose)
```


## Posibles Mejoras
A futuro, se pueden implementar las siguientes mejoras:
- **Agregar más palabras y sinónimos** para ampliar la variedad del juego.
- **Integrar una API de sinónimos** para obtener pistas dinámicamente.
- **Soporte para múltiples jugadores** y modo en línea.
- **Implementar efectos visuales y sonoros** para mejorar la experiencia del usuario.

---
¡ Espero que disfrutes del juego ! 🎮🎉

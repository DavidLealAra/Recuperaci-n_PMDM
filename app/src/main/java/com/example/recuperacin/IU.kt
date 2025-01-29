package com.example.recuperacin

import androidx.compose.runtime.*



@Composable
fun IU(viewModel: MyViewModel = remember {
    MyViewModel() }) {
    var respuestaUsuario by remember { mutableStateOf("") }
}
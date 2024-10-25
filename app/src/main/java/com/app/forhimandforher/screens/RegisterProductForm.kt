package com.app.forhimandforher.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.forhimandforher.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterProductForm(navController: NavController, nameImage: String?){
    Scaffold (topBar = {
        TopAppBar(
            title = {
            Text("Registrar Producto")
        }, navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Arrow Back"
                )
            }
            })
    }) {
        RegisterProductFormBodyContent(navController, nameImage)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterProductFormBodyContent(navController: NavController, nameImage: String?){
    var modelo by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("S") }
    var color by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var costo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen superior
        val imageResource = when (nameImage) {
            "for-him-and-for-her" -> R.drawable.for_him_and_for_her_logo
            "rh-novedades" -> R.drawable.rh_novedades
            else -> R.drawable.ic_launcher_foreground // Imagen por defecto
        }
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Logo de For Him and For Her",
            modifier = Modifier.size(width = 300.dp, height = 150.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registrar Producto",
            fontSize = 20.sp,
            fontWeight = FontWeight.W400
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (nameImage.equals("for-him-and-for-her")) {
            FormField("Modelo:", modelo) { modelo = it }
            FormField("Código:", codigo) { codigo = it }

            // Dropdown para Talla
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Talla:", modifier = Modifier.width(100.dp))
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = { }
                ) {
                    TextField(
                        value = talla,
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = false,
                        onDismissRequest = { }
                    ) {
                        listOf("S", "M", "L", "XL").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = { talla = option }
                            )
                        }
                    }
                }
            }

            FormField("Color:", color) { color = it }
            NumberFormField("Cantidad:", cantidad) { cantidad = it }
            DecimalFormField("Costo:", costo) { costo = it }
            DecimalFormField("Precio:", precio) { precio = it }
        } else {
            FormField("Marca:", marca) { marca = it }
            FormField("Código:", codigo) { codigo = it }
            NumberFormField("Cantidad:", cantidad) { cantidad = it }
            DecimalFormField("Costo:", costo) { costo = it }
            DecimalFormField("Precio:", precio) { precio = it }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF889A7)
                ),
                modifier = Modifier
                    .width(120.dp)
                    .height(48.dp)
            ) {
                Text("Regresar", color = Color.Black)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2770B9)
                ),
                modifier = Modifier
                    .width(120.dp)
                    .height(48.dp)
            ) {
                Text("Aceptar", color = Color.Black)
            }
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.width(100.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NumberFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.width(100.dp))
        TextField(
            value = value,
            onValueChange = { if (it.all { char -> char.isDigit() }) onValueChange(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DecimalFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.width(100.dp))
        TextField(
            value = value,
            onValueChange = { if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) onValueChange(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
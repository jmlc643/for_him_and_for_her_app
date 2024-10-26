package com.app.forhimandforher.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.app.forhimandforher.models.product.CreateProductFHAH
import com.app.forhimandforher.models.product.CreateProductRH
import com.app.forhimandforher.services.RetrofitClient
import com.app.forhimandforher.viewmodels.Message
import com.app.forhimandforher.viewmodels.ProductViewModel
import kotlinx.coroutines.launch

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
    var nombre by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var proveedor by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("S") }
    var color by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("1") }
    var costo by remember { mutableStateOf("0") }
    var precio by remember { mutableStateOf("0") }
    var showDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<Message?>(null) }

    val scope = rememberCoroutineScope()
    val viewModel = remember { ProductViewModel() }

    val isForHimAndForHer = nameImage == "for-him-and-for-her"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen superior
            val imageResource = if (isForHimAndForHer) {
                R.drawable.for_him_and_for_her_logo
            } else {
                R.drawable.rh_novedades
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

            if (isForHimAndForHer) {
                FormField(
                    value = modelo,
                    onValueChange = { modelo = it },
                    label = "Modelo"
                )
                FormField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = "Nombre"
                )
                FormField(
                    value = proveedor,
                    onValueChange = { proveedor = it },
                    label = "Proveedor"
                )
                FormField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = "Código"
                )

                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = talla,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Talla") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("S", "M", "L", "XL").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    talla = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                FormField(
                    value = color,
                    onValueChange = { color = it },
                    label = "Color"
                )
                FormField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = "Cantidad",
                    keyboardType = KeyboardType.Number
                )
                FormField(
                    value = costo,
                    onValueChange = { costo = it },
                    label = "Costo",
                    keyboardType = KeyboardType.Decimal
                )
                FormField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = "Precio",
                    keyboardType = KeyboardType.Decimal
                )
            } else {
                FormField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = "Nombre"
                )
                FormField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = "Código"
                )
                FormField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = "Cantidad",
                    keyboardType = KeyboardType.Number
                )
                FormField(
                    value = costo,
                    onValueChange = { costo = it },
                    label = "Costo",
                    keyboardType = KeyboardType.Decimal
                )
                FormField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = "Precio",
                    keyboardType = KeyboardType.Decimal
                )
            }

            // Mensajes y estado
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }

            message?.let {
                Text(
                    text = when (it) {
                        is Message.Success -> it.message
                        is Message.Error -> it.message
                    },
                    color = when (it) {
                        is Message.Success -> Color(0xFF22C55E)
                        is Message.Error -> Color(0xFFEF4444)
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
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
                    Text("Cancelar", color = Color.Black)
                }

                Button(
                    onClick = {
                        scope.launch {
                            isLoading = true
                            message = null
                            try {
                                if (nameImage == "for-him-and-for-her") {
                                    val product = CreateProductFHAH(
                                        model = modelo,
                                        code = codigo,
                                        name = nombre,
                                        supplierName = proveedor,
                                        size = talla,
                                        color = color,
                                        quantity = cantidad.toIntOrNull() ?: 1,
                                        purchasePrice = costo.toDoubleOrNull() ?: 0.0,
                                        salesPrice = precio.toDoubleOrNull() ?: 0.0
                                    )
                                    val response = RetrofitClient.productApiService.createProductFHAH(product)
                                    if (response.success) {
                                        message = Message.Success("Producto registrado exitosamente")
                                        showDialog = true
                                    } else {
                                        message = Message.Error(response.message)
                                    }
                                } else {
                                    val product = CreateProductRH(
                                        brandName = nombre,
                                        code = codigo,
                                        name = nombre,
                                        quantity = cantidad.toIntOrNull() ?: 1,
                                        purchasePrice = costo.toDoubleOrNull() ?: 0.0,
                                        salesPrice = precio.toDoubleOrNull() ?: 0.0
                                    )
                                    val response = RetrofitClient.productApiService.createProductRH(product)
                                    if (response.success) {
                                        message = Message.Success("Producto registrado exitosamente")
                                        showDialog = true
                                    } else {
                                        message = Message.Error(response.message)
                                    }
                                }
                            } catch (e: Exception) {
                                message = Message.Error("Error: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2770B9)
                    ),
                    modifier = Modifier
                        .width(120.dp)
                        .height(48.dp)
                ) {
                    Text("Guardar", color = Color.Black)
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Datos del Producto") },
            text = {
                Column {
                    if (nameImage == "for-him-and-for-her") {
                        Text("Modelo: $modelo")
                        Text("Nombre: $nombre")
                        Text("Proveedor: $proveedor")
                        Text("Código: $codigo")
                        Text("Talla: $talla")
                        Text("Color: $color")
                        Text("Cantidad: $cantidad")
                        Text("Costo: $costo")
                        Text("Precio: $precio")
                    } else {
                        Text("Nombre: $nombre")
                        Text("Código: $codigo")
                        Text("Cantidad: $cantidad")
                        Text("Costo: $costo")
                        Text("Precio: $precio")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
)  {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF8FAFC),
            focusedContainerColor = Color(0xFFF8FAFC)
        ),
        singleLine = true
    )
}
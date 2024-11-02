package com.app.forhimandforher.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.forhimandforher.R
import com.app.forhimandforher.models.product.create.CreateProductFHAH
import com.app.forhimandforher.models.product.create.CreateProductRH
import com.app.forhimandforher.navigation.AppScreens
import com.app.forhimandforher.viewmodels.FileViewModel
import com.app.forhimandforher.viewmodels.ProductViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterProductForm(navController: NavController, nameImage: String?, viewModel: ProductViewModel, context: Context, scannedCode: String?){
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
        RegisterProductFormBodyContent(navController, nameImage, viewModel, FileViewModel(), context, scannedCode)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterProductFormBodyContent(navController: NavController, nameImage: String?, viewModel: ProductViewModel, fileModel: FileViewModel, context: Context, scannedCode: String?){

    var modelo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var codigoBarras by remember { mutableStateOf(scannedCode ?: "") }
    var foto by remember { mutableStateOf("") }
    var tempMessage by remember { mutableStateOf<String?>(null) }
    var proveedor by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("S") }
    var color by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("1") }
    var costo by remember { mutableStateOf("0") }
    var precio by remember { mutableStateOf("0") }
    var marca by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val isForHimAndForHer = nameImage == "for-him-and-for-her"

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            fileModel.prepareFilePartAndUpload(context, it)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Imagen superior
            val imageResource = if (isForHimAndForHer) {
                R.drawable.for_him_and_for_her_logo
            } else {
                R.drawable.rh_novedades
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
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
            }

            // Mostrar el resultado de la subida o cualquier mensaje
            fileModel.uploadResponse?.let { response ->
                foto = response.data.path
                tempMessage = "Archivo subido con éxito"
                LaunchedEffect(tempMessage) {
                    kotlinx.coroutines.delay(3000)
                    tempMessage = null
                }
            } ?: fileModel.errorMessage?.let { error ->
                Text("Error: $error")
            }

            // Mostrar el mensaje temporal si existe
            tempMessage?.let { message ->
                Text(message)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxSize()
            ){
                FormField(
                    value = codigoBarras,
                    onValueChange = { codigoBarras = it },
                    label = "Código de Barras"
                )
                Button(
                    onClick = {
                        navController.navigate(route = AppScreens.BarcodeScannerScreen.route)
                    },
                    modifier = Modifier
                        .width(120.dp)
                        .height(60.dp)
                ){
                    Text("Escanear Código")
                }
            }

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
                    label = "Código del Modelo"
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
                NumberFormField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = "Cantidad"
                )
                DecimalFormField(
                    value = costo,
                    onValueChange = { costo = it },
                    label = "Precio de Compra"
                )
                DecimalFormField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = "Precio de Venta"
                )
            } else {
                FormField(
                    value = marca,
                    onValueChange = { marca = it },
                    label = "Marca",
                )
                FormField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = "Nombre",
                )
                NumberFormField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = "Cantidad"
                )
                DecimalFormField(
                    value = costo,
                    onValueChange = { costo = it },
                    label = "Precio de Compra"
                )
                DecimalFormField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = "Precio de Venta"
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { launcher.launch("image/*") },
                    shape = RectangleShape,
                    modifier = Modifier
                        .width(300.dp)
                        .height(150.dp)
                        .border(1.dp, Color.Black)
                        .background(Color.Transparent)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Image(
                            painterResource(id = R.drawable.subir_archivo),
                            contentDescription = "Imagen de Subir Archivo",
                            contentScale = ContentScale.Fit
                        )
                    }
                }
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
                        if(isForHimAndForHer){
                            viewModel.createProductFHAH(
                                CreateProductFHAH(
                                    code = codigo,
                                    purchasePrice = costo.toDouble(),
                                    name = nombre,
                                    model = modelo,
                                    salesPrice = precio.toDouble(),
                                    quantity = cantidad.toInt(),
                                    supplierName = proveedor,
                                    size = talla,
                                    color = color,
                                    photo = foto,
                                    barcode = codigoBarras
                                )
                            )
                        } else{
                            viewModel.createProductRH(
                                CreateProductRH(
                                    code = codigo,
                                    purchasePrice = costo.toDouble(),
                                    name = nombre,
                                    brandName = marca,
                                    salesPrice = precio.toDouble(),
                                    quantity = cantidad.toInt(),
                                    photo = foto,
                                    barcode = codigoBarras
                                )
                            )
                        }
                        showDialog = true
                    },
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
                        Text("Código de Barras: $codigoBarras")
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
                        Text("Código de Barras: $codigoBarras")
                        Text("Nombre: $nombre")
                        Text("Código: $codigo")
                        Text("Cantidad: $cantidad")
                        Text("Costo: $costo")
                        Text("Precio: $precio")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    codigo = ""
                    costo = "0"
                    nombre = ""
                    modelo = ""
                    precio = "0"
                    cantidad = "0"
                    proveedor = ""
                    talla = ""
                    color = ""
                    marca = ""
                    codigoBarras = ""
                    foto = ""
                }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
private fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .width(200.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF8FAFC),
            focusedContainerColor = Color(0xFFF8FAFC),
            errorContainerColor = Color(0xFFFEE2E2)
        ),
        singleLine = true
    )
}

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
            onValueChange = {
                if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) onValueChange(
                    it
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
package com.app.forhimandforher.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.forhimandforher.R
import com.app.forhimandforher.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(navController: NavController, nameImage: String?){
    Scaffold (topBar = {
        TopAppBar(
            title = {
                Text("Dashboard")
            }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }
            }
        )
    }) {
        DashboardBodyContent(navController, nameImage)
    }
}

@Composable
fun DashboardBodyContent(navController: NavController, nameImage: String?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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

        Spacer(modifier = Modifier.height(32.dp))

        // Primera fila de botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate(route = AppScreens.RegisterProductForm.route + "/"+nameImage) },
                shape = RectangleShape,
                modifier = Modifier
                    .width(140.dp)
                    .height(130.dp)
                    .border(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Registrar Productos", color = Color.Black, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { },
                shape = RectangleShape,
                modifier = Modifier
                    .width(140.dp)
                    .height(130.dp)
                    .border(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                    Text("Ver Productos", color = Color.Black, textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Segunda fila de botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { },
                shape = RectangleShape,
                modifier = Modifier
                    .width(140.dp)
                    .height(130.dp)
                    .border(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Registrar Venta", color = Color.Black, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { },
                shape = RectangleShape,
                modifier = Modifier
                    .width(140.dp)
                    .height(130.dp)
                    .border(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Reporte de Ventas", color = Color.Black, textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Regresar
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF889A7))
        ) {
            Text("Regresar", color = Color.Black)
        }
    }
}
package com.app.forhimandforher.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.forhimandforher.R
import com.app.forhimandforher.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseBusiness(navController: NavController){
    Scaffold (topBar = {
        TopAppBar(
            title = {
                Text("Escoge el rubro")
            }
        )
    }){
        ChooseBusinessBodyContent(navController)
    }
}

@Composable
fun ChooseBusinessBodyContent(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "¿Con cúal negocio",
            fontSize = 36.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center
        )
        Text(
            "deseas trabajar?",
            fontSize = 36.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            navController.navigate(route = AppScreens.DashBoard.route + "/for-him-and-for-her")
        },
            shape = RectangleShape,
            modifier= Modifier.size(width = 400.dp, height = 200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ){
            Image(
                painter = painterResource(id = R.drawable.for_him_and_for_her_logo),
                contentDescription = "Logo For Him and For Her",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            navController.navigate(route = AppScreens.DashBoard.route + "/rh-novedades")
        },
            shape = RectangleShape,
            modifier= Modifier.size(width = 400.dp, height = 200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ){
            Image(
                painter = painterResource(id = R.drawable.rh_novedades),
                contentDescription = "Logo RH Novedades",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
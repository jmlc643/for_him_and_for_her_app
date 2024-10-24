package com.app.forhimandforher.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
        Button(onClick = {
            navController.navigate(route = AppScreens.DashBoard.route)
        },
            modifier= Modifier.size(width = 400.dp, height = 150.dp),
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
            navController.navigate(route = AppScreens.DashBoard.route)
        },
            modifier= Modifier.size(width = 400.dp, height = 150.dp),
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
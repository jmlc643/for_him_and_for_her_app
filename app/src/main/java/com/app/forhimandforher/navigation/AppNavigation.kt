package com.app.forhimandforher.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.forhimandforher.screens.ChooseBusiness
import com.app.forhimandforher.screens.Dashboard
import com.app.forhimandforher.screens.RegisterProductForm
import com.app.forhimandforher.viewmodels.ProductViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val viewModel = ProductViewModel()
    NavHost(navController = navController, startDestination = AppScreens.ChooseBusiness.route){
        composable(route = AppScreens.ChooseBusiness.route){
            ChooseBusiness(navController)
        }
        composable(route = AppScreens.DashBoard.route+"/{nameImage}",
            arguments = listOf(navArgument(name = "nameImage"){
                type = NavType.StringType
            })
        ){
            Dashboard(navController, it.arguments?.getString("nameImage"))
        }
        composable(route = AppScreens.RegisterProductForm.route+"/{nameImage}",
            arguments = listOf(navArgument(name = "nameImage"){
                type = NavType.StringType
            })
        ){
            RegisterProductForm(navController, it.arguments?.getString("nameImage"), viewModel)
        }
    }
}
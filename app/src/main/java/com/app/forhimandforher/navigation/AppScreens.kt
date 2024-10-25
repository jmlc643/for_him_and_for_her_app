package com.app.forhimandforher.navigation

sealed class AppScreens(val route: String) {
    object ChooseBusiness: AppScreens("choose_business")
    object DashBoard: AppScreens("dashboard")
    object RegisterProductForm: AppScreens("register_product_form")
}
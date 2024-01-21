package com.room.shoppe.ui.navigation

sealed class NavRoutes(val route: String) {
    object MainProduct : NavRoutes("main")
    object CreateUpdateProduct : NavRoutes("cp")
}

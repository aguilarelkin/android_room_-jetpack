package com.room.shoppe.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.room.shoppe.ui.createupdate.Operation
import com.room.shoppe.ui.createupdate.OperationViewModel
import com.room.shoppe.ui.navigation.NavRoutes
import com.room.shoppe.ui.product.Product
import com.room.shoppe.ui.product.ProductViewModel
import com.room.shoppe.ui.theme.ShoppeRoomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppeRoomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNav()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNav() {
    val navController = rememberNavController()
    Scaffold(topBar = { TopAppBar(title = { Text(text = "ROOM") }) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                NavigationHost(navController = navController)
            }
        }
    )
}

@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavRoutes.MainProduct.route) {
        composable(NavRoutes.MainProduct.route) {
            val productViewModel = hiltViewModel<ProductViewModel>()
            Product(navController, productViewModel)
        }
        composable(NavRoutes.CreateUpdateProduct.route + "/{id}") {
            val operationViewModel = hiltViewModel<OperationViewModel>()
            Operation(navController, id = it.arguments?.getString("id"), operationViewModel)
        }
    }
}

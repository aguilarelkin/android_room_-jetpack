package com.room.shoppe.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.room.shoppe.domain.models.Product
import com.room.shoppe.ui.dialog.Delete
import com.room.shoppe.ui.navigation.NavRoutes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Product(navController: NavHostController, productViewModel: ProductViewModel) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 1.dp)
            .fillMaxSize()
    ) {
        Main(
            productViewModel = productViewModel,
            navController,
            Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun Main(productViewModel: ProductViewModel, navController: NavHostController, modifier: Modifier) {
    ListProduct(productViewModel, navController)
    ButtonCreate(navController, modifier)
}

@Composable
fun ListProduct(productViewModel: ProductViewModel, navController: NavHostController) {
    val stateDelete by productViewModel.stateDelete.collectAsState()
    if (stateDelete) {
        productViewModel.getProducts()
        productViewModel.initState()
    }
    productViewModel.getProducts()
    val stateList: List<Product> by productViewModel.stateList.collectAsState()

    if (stateList.isNotEmpty()) {
        LazyColumn {
            items(stateList) { model ->
                ListItemProduct(item = model, productViewModel = productViewModel, navController)
            }
        }
    }
}

@Composable
fun ListItemProduct(
    item: Product, productViewModel: ProductViewModel, navController: NavHostController
) {
    val showDialog: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    if (showDialog.value) {
        Delete(deleteProduct = { deleteProdut(productViewModel, item.id) },
            name = item.name,
            setShowDialog = { showDialog.value = it })
    }
    Card(
        modifier = Modifier
            .padding(start = 20.dp, 10.dp)
            .fillMaxSize(), shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = item.name,
                fontFamily = FontFamily.Monospace
            )
            Box {
                Row {
                    Button(
                        modifier = Modifier.padding(end = 5.dp),
                        onClick = { startOperation(navController, item.id.toString()) }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
                    }
                    Button(onClick = { showDialog.value = true }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonCreate(navController: NavHostController, modifier: Modifier) {
    Box(modifier = modifier.padding(bottom = 10.dp)) {
        FloatingActionButton(
            onClick = { navController.navigate(NavRoutes.CreateUpdateProduct.route + "/${null}") }
        ) {
            Text(text = "+")
        }
    }
}

private fun startOperation(navController: NavHostController, id: String) {
    navController.navigate(NavRoutes.CreateUpdateProduct.route + "/${id}")
}

private fun deleteProdut(productViewModel: ProductViewModel, id: Int) {
    productViewModel.deleteProductId(id)
}


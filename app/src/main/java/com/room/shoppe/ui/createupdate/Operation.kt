package com.room.shoppe.ui.createupdate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.room.shoppe.domain.models.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Operation(
    navController: NavHostController,
    id: String?,
    operationViewModel: OperationViewModel
) {
    Scaffold(content = { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            MainOperation(operationViewModel = operationViewModel, id, navController)
        }
    })
}

@Composable
fun MainOperation(
    operationViewModel: OperationViewModel,
    id: String?,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()

    val stateProduct: Product by operationViewModel.stateProduct.collectAsState()
    val stateUc: Boolean by operationViewModel.stateUC.collectAsState()

    val stateId: Int by operationViewModel.stateId.collectAsState()
    val stateCode: String by operationViewModel.stateCode.collectAsState()
    val stateName: String by operationViewModel.stateName.collectAsState()
    val stateCost: Double by operationViewModel.stateCost.collectAsState()
    val stateAmount: Int by operationViewModel.stateAmount.collectAsState()

    val okOperationDdbb: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }

    if (stateUc) {
        operationViewModel.initState()
        navController.popBackStack()
    }
    if (id != "null" && !okOperationDdbb.value) {
        operationViewModel.searchProduct(id = id!!.toInt())
        if (stateProduct.name.isNotEmpty()) {
            okOperationDdbb.value = true
            operationViewModel.onChangedField(
                stateProduct.id,
                stateProduct.code,
                stateProduct.name,
                stateProduct.cost,
                stateProduct.amount
            )
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        EditText(name = "ID", data = stateId.toString(), key = KeyboardType.Number, changedField = {
            operationViewModel.onChangedField(
                it.toInt(), stateCode, stateName, stateCost, stateAmount
            )
        })
        Spacer(modifier = Modifier.padding(8.dp))
        EditText(name = "CODE", data = stateCode, key = KeyboardType.Text, changedField = {
            operationViewModel.onChangedField(
                stateId, it, stateName, stateCost, stateAmount
            )
        })
        Spacer(modifier = Modifier.padding(8.dp))
        EditText(name = "NAME", data = stateName, key = KeyboardType.Text, changedField = {
            operationViewModel.onChangedField(
                stateId, stateCode, it, stateCost, stateAmount
            )
        })
        Spacer(modifier = Modifier.padding(8.dp))
        EditText(name = "COST",
            data = stateCost.toString(),
            key = KeyboardType.Decimal,
            changedField = {
                operationViewModel.onChangedField(
                    stateId, stateCode, stateName, validCost(it), stateAmount
                )
            })
        Spacer(modifier = Modifier.padding(8.dp))
        EditText(name = "AMOUNT",
            data = stateAmount.toString(),
            key = KeyboardType.Number,
            changedField = {
                operationViewModel.onChangedField(
                    stateId, stateCode, stateName, stateCost, validId(it)
                )
            })
        Spacer(modifier = Modifier.padding(16.dp))
        ButtonOperation(id = id) {
            operation(
                id = id,
                product = Product(stateId, stateCode, stateName, stateCost, stateAmount),
                operationViewModel = operationViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditText(name: String, data: String, key: KeyboardType, changedField: (String) -> Unit) {
    Text(text = name)
    TextField(
        value = data,
        onValueChange = { changedField(it) },
        modifier = Modifier.fillMaxWidth(0.9f),
        placeholder = { Text(text = name) },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = key)
    )
}

@Composable
fun ButtonOperation(id: String?, changedField: () -> Unit) {
    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
        Button(
            onClick = { changedField() },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00C853),
                disabledContainerColor = Color(0xFF00B8D4),
                contentColor = Color(0xFFFFFFFF),
                disabledContentColor = Color(0xFFFFFFFF)
            )//, enabled = loginEnable
        ) {
            if (id == "null") {
                Text(text = "Create")
            } else {
                Text(text = "Update")
            }
        }
    }
}

private fun operation(
    id: String?,
    product: Product,
    operationViewModel: OperationViewModel,
) {
    if (id == "null") {
        operationViewModel.insertProduct(product)
    } else {
        operationViewModel.updateProductDb(product)
    }
}

private fun validCost(cost: String): Double {
    return try {
        cost.toDouble()
    } catch (e: Exception) {
        0.0
    }
}

private fun validId(edId: String): Int {
    return try {
        Integer.parseInt(edId)
    } catch (e: Exception) {
        0
    }
}

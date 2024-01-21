package com.room.shoppe.ui.createupdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.room.shoppe.domain.models.Product
import com.room.shoppe.domain.usecase.product.CreateProduct
import com.room.shoppe.domain.usecase.product.GetProductId
import com.room.shoppe.domain.usecase.product.UpdateProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OperationViewModel @Inject constructor(
    private val createProduct: CreateProduct,
    private val updateProduct: UpdateProduct,
    private val getProductId: GetProductId
) : ViewModel() {
    private var _stateProduct = MutableStateFlow<Product>(Product())
    val stateProduct: StateFlow<Product> = _stateProduct

    private var _stateUC = MutableStateFlow<Boolean>(false)
    val stateUC: StateFlow<Boolean> = _stateUC

    private var _stateId = MutableStateFlow<Int>(0)
    val stateId: StateFlow<Int> = _stateId
    private var _stateCode = MutableStateFlow<String>("")
    val stateCode: StateFlow<String> = _stateCode
    private var _stateName = MutableStateFlow<String>("")
    val stateName: StateFlow<String> = _stateName
    private var _stateCost = MutableStateFlow<Double>(0.0)
    val stateCost: StateFlow<Double> = _stateCost
    private var _stateAmount = MutableStateFlow<Int>(0)
    val stateAmount: StateFlow<Int> = _stateAmount

    fun initState() {
        _stateUC.value = false
    }

    fun onChangedField(
        id: Int,
        code: String,
        name: String,
        cost: Double,
        amount: Int,
    ) {
        _stateId.value = id
        _stateCode.value = code
        _stateName.value = name
        _stateCost.value = cost
        _stateAmount.value = amount
    }

    fun searchProduct(id: Int) {
        viewModelScope.launch {
            val result: Product? = withContext(Dispatchers.IO) {
                getProductId(id)
            }
            if (result != null) {
                _stateProduct.value = result
            }
        }
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            val result: String? = withContext(Dispatchers.IO) {
                createProduct(product)
            }
            if (result != null) {
                _stateUC.value = true
            }
        }
    }

    fun updateProductDb(product: Product) {
        viewModelScope.launch {
            val result: String? = withContext(Dispatchers.IO) {
                updateProduct(product)
            }
            if (result != null) {
                _stateUC.value = true
            }
        }
    }
}
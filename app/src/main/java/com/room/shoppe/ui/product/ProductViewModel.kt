package com.room.shoppe.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.room.shoppe.domain.models.Product
import com.room.shoppe.domain.usecase.product.DeleteProduct
import com.room.shoppe.domain.usecase.product.GetProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAProduct: GetProduct, private val deleteProduct: DeleteProduct
) : ViewModel() {
    private var _stateList = MutableStateFlow<List<Product>>(emptyList())
    val stateList: StateFlow<List<Product>> = _stateList

    private var _stateDelete = MutableStateFlow<Boolean>(false)
    val stateDelete: StateFlow<Boolean> = _stateDelete

    fun initState() {
        _stateDelete.value = false
    }

    fun getProducts() {
        viewModelScope.launch {
            val result: List<Product>? = withContext(Dispatchers.IO) {
                getAProduct()
            }
            if (result != null) {
                _stateList.value = result
            }
        }
    }

    fun deleteProductId(id: Int) {
        viewModelScope.launch {
            val result: String? = withContext(Dispatchers.IO) {
                deleteProduct(id)
            }
            if (!result.isNullOrEmpty()) {
                _stateDelete.value = true
            }
        }
    }

}
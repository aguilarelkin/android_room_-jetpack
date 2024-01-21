package com.room.shoppe.data.database

import android.util.Log
import com.room.shoppe.data.database.dao.DaoProduct
import com.room.shoppe.domain.models.Product
import com.room.shoppe.domain.repository.RepositoryProduct
import javax.inject.Inject

class RepositoryProductImpl @Inject constructor(private val daoProduct: DaoProduct) :
    RepositoryProduct {
    override suspend fun getAllProducts(): List<Product>? {
        runCatching {
            daoProduct.getAllProducts()
        }.onSuccess { return it.map { data -> data.toDomain() } }
            .onFailure { Log.i("DDBB", "Error: ${it.message}") }
        return null
    }

    override suspend fun getProductId(id: Int): Product? {
        runCatching {
            daoProduct.getProductId(id)
        }.onSuccess { return it.toDomain() }.onFailure { Log.i("DDBB", "Error: ${it.message}") }
        return null
    }

    override suspend fun createProduct(product: Product): String? {
        runCatching {
            daoProduct.createProduct(product.toConvert())
        }.onSuccess { return "200" }.onFailure { Log.i("DDBB", "Error: ${it.message}") }
        return null
    }

    override suspend fun updateProduct(product: Product): String? {
        runCatching {
            daoProduct.updateProduct(product.toConvert())
        }.onSuccess { return "200" }.onFailure { Log.i("DDBB", "Error: ${it.message}") }
        return null
    }

    override suspend fun deleteProduct(id: Int): String? {
        runCatching {
            daoProduct.deleteProduct(id)
        }.onSuccess { return "200" }.onFailure { Log.i("DDBB", "Error: ${it.message}") }
        return null
    }
}
package com.room.shoppe.domain.repository

import com.room.shoppe.domain.models.Product

interface RepositoryProduct {
    suspend fun getAllProducts(): List<Product>?
    suspend fun getProductId(id: Int): Product?
    suspend fun createProduct(product: Product): String?
    suspend fun updateProduct(product: Product): String?
    suspend fun deleteProduct(id: Int): String?
}
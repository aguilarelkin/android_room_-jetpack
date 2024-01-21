package com.room.shoppe.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.room.shoppe.data.database.entities.ProductData
import com.room.shoppe.domain.models.Product

@Dao
interface DaoProduct {

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductData>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductId(id: Int): ProductData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createProduct(productData: ProductData)

    @Update
    suspend fun updateProduct(productData: ProductData)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProduct(id: Int)
}
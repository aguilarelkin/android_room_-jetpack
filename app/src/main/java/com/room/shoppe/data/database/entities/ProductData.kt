package com.room.shoppe.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.room.shoppe.domain.models.Product

@Entity(tableName = "products")
data class ProductData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cost") val cost: Double,
    @ColumnInfo(name = "amount") val amount: Int
) {
    fun toDomain() = Product(
        id = id,
        code = code,
        name = name,
        cost = cost,
        amount = amount
    )
}


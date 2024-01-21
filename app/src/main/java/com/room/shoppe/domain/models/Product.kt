package com.room.shoppe.domain.models

import com.room.shoppe.data.database.entities.ProductData

data class Product(
    val id: Int = 0,
    val code: String = "",
    val name: String = "",
    val cost: Double = 0.0,
    val amount: Int = 0
) {
    fun toConvert() = ProductData(id, code, name, cost, amount)
}

package com.room.shoppe.domain.usecase.product

import com.room.shoppe.domain.models.Product
import com.room.shoppe.domain.repository.RepositoryProduct
import javax.inject.Inject

class UpdateProduct @Inject constructor(private val repositoryProduct: RepositoryProduct) {

    suspend operator fun invoke(product: Product): String? {
        return repositoryProduct.updateProduct(product)
    }
}
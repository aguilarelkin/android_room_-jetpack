package com.room.shoppe.domain.usecase.product

import com.room.shoppe.domain.repository.RepositoryProduct
import javax.inject.Inject

class DeleteProduct @Inject constructor(private val repositoryProduct: RepositoryProduct) {

    suspend operator fun invoke(id: Int): String? {
        return repositoryProduct.deleteProduct(id)
    }
}